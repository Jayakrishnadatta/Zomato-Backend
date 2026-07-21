package com.zentro.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;


    private String paymentMethod;

    private String transactionId;

    private String paymentGateway;

    private double paidAmount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
    private PaymentStatus paymentStatus;

    private LocalDateTime paidAt;
    
    @JoinColumn(name = "orderId")
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Order order;
    
    public Payment() {
		// TODO Auto-generated constructor stub
	}

	public Payment(String paymentMethod, String transactionId, String paymentGateway, double paidAmount,
			PaymentStatus paymentStatus, LocalDateTime paidAt) {
		super();
		this.paymentMethod = paymentMethod;
		this.transactionId = transactionId;
		this.paymentGateway = paymentGateway;
		this.paidAmount = paidAmount;
		this.paymentStatus = paymentStatus;
		this.paidAt = paidAt;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Long getOrderId() {
		return order != null ? order.getOrderId() : null;
	}

	public void setOrderId(Long orderId) {
		if (this.order == null) {
			this.order = new Order();
		}
		this.order.setOrderId(orderId);
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", orderId=" + getOrderId() + ", paymentMethod=" + paymentMethod
				+ ", transactionId=" + transactionId + ", paymentGateway=" + paymentGateway + ", paidAmount="
				+ paidAmount + ", paymentStatus=" + paymentStatus + "]";
	}
    
    
}
