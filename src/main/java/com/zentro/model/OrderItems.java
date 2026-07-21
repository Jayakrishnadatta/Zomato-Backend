package com.zentro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long orderItemId;



	    private int quantity;

	    private double unitPrice;

	    private double totalPrice;
	    
	    
	    @ManyToOne
	    @JoinColumn(name = "productId")
	    private Product product;
	    
	    @ManyToOne
	    @JoinColumn(name = "orderId")
	    private Order order;
	    
	    
	    
	    public OrderItems() {
			// TODO Auto-generated constructor stub
		}
		public OrderItems(Long orderId, Long productId, int quantity, double unitPrice, double totalPrice) {
			super();
			this.order.setOrderId(orderId);
			this.product.setProductId(productId);
			this.quantity = quantity;
			this.unitPrice = unitPrice;
			this.totalPrice = totalPrice;
		}
		
		
		public Order getOrder() {
			return order;
		}
		public void setOrder(Order order) {
			this.order = order;
		}
		public Product getProduct() {
			return product;
		}
		public void setProduct(Product product) {
			this.product = product;
		}
		public Long getOrderItemId() {
			return orderItemId;
		}
		public void setOrderItemId(Long orderItemId) {
			this.orderItemId = orderItemId;
		}
		public Long getOrderId() {
			return order.getOrderId();
		}
		public void setOrderId(Long orderId) {
			this.order.setOrderId(orderId);
		}
		public Long getProductId() {
			return product.getProductId();
		}
		public void setProductId(Long productId) {
			this.product.setProductId(productId);
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public double getUnitPrice() {
			return unitPrice;
		}
		public void setUnitPrice(double unitPrice) {
			this.unitPrice = unitPrice;
		}
		public double getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}
		@Override
		public String toString() {
			return "OrderItems [orderItemId=" + orderItemId + ", orderId=" + order.getOrderId() + ", productId=" + product.getProductId()
					+ ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", totalPrice=" + totalPrice
					+ ", product=" + product + "]";
		}
		
	    
		
}
