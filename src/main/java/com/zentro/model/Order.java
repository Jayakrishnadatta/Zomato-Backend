package com.zentro.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long orderId;


	    @Enumerated(EnumType.STRING)
	    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
	    private OrderStatus orderStatus;

	    @Enumerated(EnumType.STRING)
	    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
	    private PaymentStatus paymentStatus;

	    private double totalAmount;

	    private double taxAmount;

	    private double shippingCharge;

	    private double discountAmount;

	    private double finalAmount;

	    private String orderTrackingNumber;

	    private LocalDateTime orderedAt;

	    private LocalDateTime deliveredAt;
	    
	    @JoinColumn(name = "userId")
	    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH })
	    private User user;
	
	    
	    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
	    private List<OrderItems> orderItems;
	    
	    @OneToOne(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	    private Payment payment;
	    
	    @OneToOne(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	    private Shipment shipment;
	    
	    
	    
	    public Order() {
			// TODO Auto-generated constructor stub
		}

		public Order( OrderStatus orderStatus, PaymentStatus paymentStatus, double totalAmount, double taxAmount,
				double shippingCharge, double discountAmount, double finalAmount, String orderTrackingNumber,
				LocalDateTime orderedAt, LocalDateTime deliveredAt) {
			super();
			
			this.orderStatus = orderStatus;
			this.paymentStatus = paymentStatus;
			this.totalAmount = totalAmount;
			this.taxAmount = taxAmount;
			this.shippingCharge = shippingCharge;
			this.discountAmount = discountAmount;
			this.finalAmount = finalAmount;
			this.orderTrackingNumber = orderTrackingNumber;
			this.orderedAt = orderedAt;
			this.deliveredAt = deliveredAt;
		}
		
		
		
		public Shipment getShipment() {
			return shipment;
		}

		public void setShipment(Shipment shipment) {
			this.shipment = shipment;
		}

		public Payment getPayment() {
			return payment;
		}

		public void setPayment(Payment payment) {
			this.payment = payment;
		}

		public List<OrderItems> getOrderItems() {
			return orderItems;
		}

		public void setOrderItems(List<OrderItems> orderItems) {
			this.orderItems = orderItems;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}

		public Long getUserId() {
			return user.getId();
		}

		public void setUserId(Long userId) {
			this.user.setId(userId);
		}

		public OrderStatus getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(OrderStatus orderStatus) {
			this.orderStatus = orderStatus;
		}

		public PaymentStatus getPaymentStatus() {
			return paymentStatus;
		}

		public void setPaymentStatus(PaymentStatus paymentStatus) {
			this.paymentStatus = paymentStatus;
		}

		public double getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
		}

		public double getTaxAmount() {
			return taxAmount;
		}

		public void setTaxAmount(double taxAmount) {
			this.taxAmount = taxAmount;
		}

		public double getShippingCharge() {
			return shippingCharge;
		}

		public void setShippingCharge(double shippingCharge) {
			this.shippingCharge = shippingCharge;
		}

		public double getDiscountAmount() {
			return discountAmount;
		}

		public void setDiscountAmount(double discountAmount) {
			this.discountAmount = discountAmount;
		}

		public double getFinalAmount() {
			return finalAmount;
		}

		public void setFinalAmount(double finalAmount) {
			this.finalAmount = finalAmount;
		}

		public String getOrderTrackingNumber() {
			return orderTrackingNumber;
		}

		public void setOrderTrackingNumber(String orderTrackingNumber) {
			this.orderTrackingNumber = orderTrackingNumber;
		}

		public LocalDateTime getOrderedAt() {
			return orderedAt;
		}

		public void setOrderedAt(LocalDateTime orderedAt) {
			this.orderedAt = orderedAt;
		}

		public LocalDateTime getDeliveredAt() {
			return deliveredAt;
		}

		public void setDeliveredAt(LocalDateTime deliveredAt) {
			this.deliveredAt = deliveredAt;
		}

		@Override
		public String toString() {
			Long userId = user != null ? user.getId() : null;
			return "Order [orderId=" + orderId + ", userId=" + userId + ", orderStatus=" + orderStatus
					+ ", paymentStatus=" + paymentStatus + ", totalAmount=" + totalAmount + ", taxAmount=" + taxAmount
					+ ", shippingCharge=" + shippingCharge + ", discountAmount=" + discountAmount + ", finalAmount="
					+ finalAmount + ", orderTrackingNumber=" + orderTrackingNumber + ", orderedAt=" + orderedAt
					+ ", deliveredAt=" + deliveredAt + ", user=" + user + "]";
		}

	
	    
}
