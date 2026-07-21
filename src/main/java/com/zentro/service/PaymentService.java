package com.zentro.service;

import java.util.List;

import com.zentro.dto.payment.PaymentRequestDto;
import com.zentro.dto.payment.PaymentResponse;

public interface PaymentService {

    PaymentResponse processPayment(
            PaymentRequestDto dto
    );

    PaymentResponse getPaymentById(
            Long paymentId
    );

    List<PaymentResponse>
    getPaymentsByUserId(
            Long userId
    );

    void refundPayment(Long paymentId);

    // Verify Razorpay payment after checkout on client side
    PaymentResponse verifyRazorpayPayment(com.zentro.dto.payment.RazorpayVerifyDto dto);
}
