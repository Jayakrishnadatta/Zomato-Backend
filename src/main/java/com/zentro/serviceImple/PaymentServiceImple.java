package com.zentro.serviceImple;

import com.zentro.dto.payment.PaymentRequestDto;
import com.zentro.dto.payment.PaymentResponse;
import com.zentro.mapper.PaymentMapper;
import com.zentro.model.Order;
import com.zentro.model.OrderStatus;
import com.zentro.model.Payment;
import com.zentro.model.PaymentStatus;
import com.zentro.repository.OrderRepository;
import com.zentro.repository.PaymentRepository;
import com.zentro.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PaymentServiceImple implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @org.springframework.beans.factory.annotation.Value("${razorpay.key}")
    private String razorpayKey;

    @org.springframework.beans.factory.annotation.Value("${razorpay.secret}")
    private String razorpaySecret;

    public PaymentServiceImple(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequestDto dto) {
        validateDto(dto);

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (order.getPaymentStatus().equals(PaymentStatus.PAID)) {
            throw new IllegalArgumentException("Order is already paid");
        }
        if (order.getOrderStatus().equals(OrderStatus.CANCELLED)) {
            throw new IllegalArgumentException("Cancelled order cannot be paid");
        }

        String method = dto.getPaymentMethod();

        // Razorpay flow: create an order on Razorpay and persist payment with PENDING status.
        if (method != null && method.equalsIgnoreCase("RAZORPAY")) {
            try {
                // Build request to Razorpay orders API
                java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
                String apiUrl = "https://api.razorpay.com/v1/orders";
                int amountPaise = (int) Math.round(order.getFinalAmount() * 100);
                String jsonBody = String.format("{\"amount\":%d,\"currency\":\"INR\",\"receipt\":\"order_rcptid_%d\"}", amountPaise, order.getOrderId());

                String auth = java.util.Base64.getEncoder().encodeToString((razorpayKey + ":" + razorpaySecret).getBytes(java.nio.charset.StandardCharsets.UTF_8));

                java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                        .uri(java.net.URI.create(apiUrl))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Basic " + auth)
                        .POST(java.net.http.HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

                java.net.http.HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200 && response.statusCode() != 201) {
                    throw new RuntimeException("Razorpay order creation failed: " + response.body());
                }

                String body = response.body();
                // Extract "id" value from JSON response (simple extraction, not full JSON parsing)
                String idKey = "\"id\":\"";
                int idIndex = body.indexOf(idKey);
                String gatewayOrderId = null;
                if (idIndex >= 0) {
                    int start = idIndex + idKey.length();
                    int end = body.indexOf('"', start);
                    if (end > start) {
                        gatewayOrderId = body.substring(start, end);
                    }
                }

                Payment payment = PaymentMapper.toEntity(dto);
                payment.setOrder(order);
                payment.setPaidAmount(order.getFinalAmount());
                payment.setPaymentStatus(PaymentStatus.PENDING);
                payment.setTransactionId(gatewayOrderId);
                payment.setPaymentGateway("RAZORPAY");
                paymentRepository.save(payment);

                order.setPayment(payment);
                orderRepository.save(order);

                // Return response with gateway order id and PENDING status
                PaymentResponse resp = PaymentMapper.toResponse(payment);
                return resp;

            } catch (Exception e) {
                throw new RuntimeException("Razorpay integration failed", e);
            }
        }

        // Default (sync) flow: mark payment as PAID
        Payment payment = PaymentMapper.toEntity(dto);
        payment.setOrder(order);
        payment.setPaidAmount(order.getFinalAmount());
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaidAt(LocalDateTime.now());
        payment.setPaymentGateway(method);

        order.setPaymentStatus(PaymentStatus.PAID);
        order.setPayment(payment);

        return PaymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
        validatePaymentId(paymentId);
        PaymentResponse response = paymentRepository.findPaymentResponseByPaymentId(paymentId);
        if (response == null) {
            throw new IllegalArgumentException("Payment not found");
        }
        return response;
    }

    @Override
    public List<PaymentResponse> getPaymentsByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User is required");
        }
        return paymentRepository.findPaymentResponsesByUserId(userId);
    }

    @Override
    public void refundPayment(Long paymentId) {
        validatePaymentId(paymentId);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        if (payment.getOrder() != null) {
            payment.getOrder().setPaymentStatus(PaymentStatus.REFUNDED);
        }
        paymentRepository.save(payment);
    }

    @Override
    public PaymentResponse verifyRazorpayPayment(com.zentro.dto.payment.RazorpayVerifyDto dto) {
        if (dto == null || dto.getRazorpayOrderId() == null || dto.getRazorpayOrderId().isBlank()) {
            throw new IllegalArgumentException("Razorpay order id is required");
        }
        if (dto.getRazorpayPaymentId() == null || dto.getRazorpayPaymentId().isBlank()) {
            throw new IllegalArgumentException("Razorpay payment id is required");
        }
        if (dto.getRazorpaySignature() == null || dto.getRazorpaySignature().isBlank()) {
            throw new IllegalArgumentException("Razorpay signature is required");
        }

        Payment payment = paymentRepository.findByTransactionId(dto.getRazorpayOrderId());
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found for given Razorpay order id");
        }

        try {
            String payload = dto.getRazorpayOrderId() + "|" + dto.getRazorpayPaymentId();

            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
            javax.crypto.spec.SecretKeySpec secretKey = new javax.crypto.spec.SecretKeySpec(razorpaySecret.getBytes(java.nio.charset.StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] digest = mac.doFinal(payload.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            String computedSignature = sb.toString();

            if (!computedSignature.equals(dto.getRazorpaySignature())) {
                throw new IllegalArgumentException("Invalid Razorpay signature");
            }

            // mark payment as paid
            payment.setTransactionId(dto.getRazorpayPaymentId());
            payment.setPaymentStatus(PaymentStatus.PAID);
            payment.setPaidAt(LocalDateTime.now());
            paymentRepository.save(payment);

            if (payment.getOrder() != null) {
                payment.getOrder().setPaymentStatus(PaymentStatus.PAID);
                orderRepository.save(payment.getOrder());
            }

            return PaymentMapper.toResponse(payment);

        } catch (Exception e) {
            throw new RuntimeException("Failed to verify Razorpay signature", e);
        }
    }

    private void validateDto(PaymentRequestDto dto) {
        if (dto == null || dto.getOrderId() == null || dto.getOrderId() <= 0) {
            throw new IllegalArgumentException("Payment data is required");
        }
        if (dto.getPaymentMethod() == null || dto.getPaymentMethod().isBlank()) {
            throw new IllegalArgumentException("Payment method is required");
        }
    }

    private void validatePaymentId(Long paymentId) {
        if (paymentId == null || paymentId <= 0) {
            throw new IllegalArgumentException("Payment is required");
        }
    }
}
