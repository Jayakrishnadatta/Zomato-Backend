package com.zentro.repository;

import com.zentro.dto.payment.PaymentResponse;
import com.zentro.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    @Query("select new com.zentro.dto.payment.PaymentResponse(p.paymentId, p.transactionId, p.paidAmount, p.paymentStatus, p.paidAt) from Payment p where p.paymentId = :paymentId")
    PaymentResponse findPaymentResponseByPaymentId(@Param("paymentId") Long paymentId);

    @Query("select new com.zentro.dto.payment.PaymentResponse(p.paymentId, p.transactionId, p.paidAmount, p.paymentStatus, p.paidAt) from Payment p where p.order.user.Id = :userId")
    List<PaymentResponse> findPaymentResponsesByUserId(@Param("userId") Long userId);

    // Find payment by gateway transaction/order id (used for verifying Razorpay payments)
    Payment findByTransactionId(String transactionId);
}
