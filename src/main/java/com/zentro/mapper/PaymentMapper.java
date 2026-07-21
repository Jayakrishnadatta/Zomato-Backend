package com.zentro.mapper;

import com.zentro.dto.payment.PaymentRequestDto;
import com.zentro.dto.payment.PaymentResponse;
import com.zentro.model.Payment;

public class PaymentMapper {

    private PaymentMapper() {
    }

    // Request DTO → Entity

    public static Payment toEntity(
            PaymentRequestDto dto
    ) {

        Payment payment =
                new Payment();

        payment.setPaymentMethod(
                dto.getPaymentMethod()
        );

        return payment;
    }

    // Entity → Response DTO

    public static PaymentResponse
    toResponse(Payment payment) {

        PaymentResponse response =
                new PaymentResponse();

        response.setPaymentId(
                payment.getPaymentId()
        );

        response.setTransactionId(
                payment.getTransactionId()
        );

        response.setAmount(
                payment.getPaidAmount()
        );

        response.setPaymentStatus(
                payment.getPaymentStatus() != null ? payment.getPaymentStatus().name() : null
        );

        response.setPaidAt(
                payment.getPaidAt()
        );

        response.setPaymentGateway(
                payment.getPaymentGateway()
        );

        // For gateways like Razorpay the transactionId is the gateway's order/payment id
        response.setGatewayOrderId(
                payment.getTransactionId()
        );

        return response;
    }
}
