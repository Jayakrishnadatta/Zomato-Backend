package com.zentro.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Shipment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long shipmentId;


	    private String courierPartner;

	    private String trackingNumber;

	    private String shipmentStatus;

	    private LocalDateTime shippedAt;

	    private LocalDateTime deliveredAt;
	    
	    @JoinColumn(name = "orderId")
	    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	    private Order order;
	    
	    public Shipment() {
			// TODO Auto-generated constructor stub
		}

		public Shipment(Long orderId, String courierPartner, String trackingNumber, String shipmentStatus,
				LocalDateTime shippedAt, LocalDateTime deliveredAt) {
			super();
			Order order = new Order();
			order.setOrderId(orderId);
			this.order = order;
			this.courierPartner = courierPartner;
			this.trackingNumber = trackingNumber;
			this.shipmentStatus = shipmentStatus;
			this.shippedAt = shippedAt;
			this.deliveredAt = deliveredAt;
		}
		
		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}

		public Long getShipmentId() {
			return shipmentId;
		}

		public void setShipmentId(Long shipmentId) {
			this.shipmentId = shipmentId;
		}

		public Long getOrderId() {
			return order.getOrderId();
		}

		public void setOrderId(Long orderId) {
			this.order.setOrderId(orderId);
		}

		public String getCourierPartner() {
			return courierPartner;
		}

		public void setCourierPartner(String courierPartner) {
			this.courierPartner = courierPartner;
		}

		public String getTrackingNumber() {
			return trackingNumber;
		}

		public void setTrackingNumber(String trackingNumber) {
			this.trackingNumber = trackingNumber;
		}

		public String getShipmentStatus() {
			return shipmentStatus;
		}

		public void setShipmentStatus(String shipmentStatus) {
			this.shipmentStatus = shipmentStatus;
		}

		public LocalDateTime getShippedAt() {
			return shippedAt;
		}

		public void setShippedAt(LocalDateTime shippedAt) {
			this.shippedAt = shippedAt;
		}

		public LocalDateTime getDeliveredAt() {
			return deliveredAt;
		}

		public void setDeliveredAt(LocalDateTime deliveredAt) {
			this.deliveredAt = deliveredAt;
		}

		@Override
		public String toString() {
			return "Shipment [shipmentId=" + shipmentId + ", orderId=" + order.getOrderId() + ", courierPartner=" + courierPartner
					+ ", trackingNumber=" + trackingNumber + ", shipmentStatus=" + shipmentStatus + "]";
		}
	    

}
