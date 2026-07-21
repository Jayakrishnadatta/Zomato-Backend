package com.zentro.dto.payment;

import java.time.LocalDateTime;

import com.zentro.model.PaymentStatus;

public class PaymentResponse {
    
	private Long paymentId;

    private String transactionId;

    private double amount;

    private String paymentStatus;

    private LocalDateTime paidAt;

	private String paymentGateway;

	private String gatewayOrderId;
    
  
	public PaymentResponse(Long paymentId, String transactionId, double amount, String paymentStatus,
			LocalDateTime paidAt) {
		super();
		this.paymentId = paymentId;
		this.transactionId = transactionId;
		this.amount = amount;
		this.paymentStatus = paymentStatus;
		this.paidAt = paidAt;
	}

	public PaymentResponse(Long paymentId, String transactionId, double amount, PaymentStatus paymentStatus,
			LocalDateTime paidAt) {
		this(paymentId, transactionId, amount, paymentStatus != null ? paymentStatus.name() : null, paidAt);
	}

	public PaymentResponse() {
		// TODO Auto-generated constructor stub
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}

	public String getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public String getGatewayOrderId() {
		return gatewayOrderId;
	}

	public void setGatewayOrderId(String gatewayOrderId) {
		this.gatewayOrderId = gatewayOrderId;
	}

	@Override
	public String toString() {
		return "PaymentResponse [paymentId=" + paymentId + ", transactionId=" + transactionId + ", amount=" + amount
				+ ", paymentStatus=" + paymentStatus + ", paymentGateway=" + paymentGateway + "]";
	}
    
    

}
