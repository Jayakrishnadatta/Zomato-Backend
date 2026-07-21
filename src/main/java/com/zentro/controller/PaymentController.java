package com.zentro.controller;

import com.zentro.dto.payment.PaymentRequestDto;
import com.zentro.dto.payment.PaymentResponse;
import com.zentro.service.PaymentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/")
    public PaymentResponse processPayment(@RequestBody PaymentRequestDto dto) {
        return paymentService.processPayment(dto);
    }

    @PostMapping("/razorpay/verify")
    public PaymentResponse verifyRazorpay(@RequestBody com.zentro.dto.payment.RazorpayVerifyDto dto) {
        return paymentService.verifyRazorpayPayment(dto);
    }

    @GetMapping("/{paymentId}")
    public PaymentResponse getPaymentById(@PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @GetMapping("/user/{userId}")
    public List<PaymentResponse> getPaymentsByUserId(@PathVariable Long userId) {
        return paymentService.getPaymentsByUserId(userId);
    }

    @PatchMapping("/{paymentId}/refund")
    public void refundPayment(@PathVariable Long paymentId) {
        paymentService.refundPayment(paymentId);
    }
}
