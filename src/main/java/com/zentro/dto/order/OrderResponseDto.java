package com.zentro.dto.order;

import java.time.LocalDateTime;

import com.zentro.model.OrderStatus;
import com.zentro.model.PaymentStatus;

public class OrderResponseDto {


	private Long orderId;
	private Long productId;

	private String productName;

	private int quantity;

	private double price;

	private double totalPrice;
	

    private String orderStatus;


	private String paymentStatus;
    
    private LocalDateTime orderedAt;
    
    public OrderResponseDto() {
		// TODO Auto-generated constructor stub
	}
    
    public OrderResponseDto(Long orderId, Long productId, String productName, int quantity, double price, double totalPrice,
			String orderStatus, String paymentStatus, LocalDateTime orderedAt) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.orderedAt = orderedAt;
	}

	public OrderResponseDto(Long orderId, Long productId, String productName, int quantity, double price, double totalPrice,
			OrderStatus orderStatus, PaymentStatus paymentStatus, LocalDateTime orderedAt) {
		this(orderId, productId, productName, quantity, price, totalPrice,
				orderStatus != null ? orderStatus.name() : null,
				paymentStatus != null ? paymentStatus.name() : null,
				orderedAt);
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getOrderedAt() {
		return orderedAt;
	}

	public void setOrderedAt(LocalDateTime orderedAt) {
		this.orderedAt = orderedAt;
	}



	
	
	

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "OrderResponseDto [orderId=" + orderId + ", productId=" + productId + ", productName=" + productName + ", quantity=" + quantity
				+ ", price=" + price + ", totalPrice=" + totalPrice + "]";
	}
	
	
	
}
