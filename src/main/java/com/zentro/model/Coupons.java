package com.zentro.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coupons {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long couponId;

	    private String couponCode;

	    private double discountPercentage;

	    private double maximumDiscountAmount;

	    private double minimumOrderAmount;

	    private LocalDate expiryDate;

	    private boolean isActive;
	    
	    public Coupons() {
			// TODO Auto-generated constructor stub
		}

		public Coupons(String couponCode, double discountPercentage, double maximumDiscountAmount,
				double minimumOrderAmount, LocalDate expiryDate, boolean isActive) {
			super();
			this.couponCode = couponCode;
			this.discountPercentage = discountPercentage;
			this.maximumDiscountAmount = maximumDiscountAmount;
			this.minimumOrderAmount = minimumOrderAmount;
			this.expiryDate = expiryDate;
			this.isActive = isActive;
		}

		public Long getCouponId() {
			return couponId;
		}

		public void setCouponId(Long couponId) {
			this.couponId = couponId;
		}

		public String getCouponCode() {
			return couponCode;
		}

		public void setCouponCode(String couponCode) {
			this.couponCode = couponCode;
		}

		public double getDiscountPercentage() {
			return discountPercentage;
		}

		public void setDiscountPercentage(double discountPercentage) {
			this.discountPercentage = discountPercentage;
		}

		public double getMaximumDiscountAmount() {
			return maximumDiscountAmount;
		}

		public void setMaximumDiscountAmount(double maximumDiscountAmount) {
			this.maximumDiscountAmount = maximumDiscountAmount;
		}

		public double getMinimumOrderAmount() {
			return minimumOrderAmount;
		}

		public void setMinimumOrderAmount(double minimumOrderAmount) {
			this.minimumOrderAmount = minimumOrderAmount;
		}

		public LocalDate getExpiryDate() {
			return expiryDate;
		}

		public void setExpiryDate(LocalDate expiryDate) {
			this.expiryDate = expiryDate;
		}

		public boolean isActive() {
			return isActive;
		}

		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}

		@Override
		public String toString() {
			return "Coupons [couponId=" + couponId + ", couponCode=" + couponCode + ", discountPercentage="
					+ discountPercentage + ", maximumDiscountAmount=" + maximumDiscountAmount + ", minimumOrderAmount="
					+ minimumOrderAmount + ", isActive=" + isActive + "]";
		}
	    
	    

}
