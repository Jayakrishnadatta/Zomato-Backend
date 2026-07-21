package com.zentro.dto.product;

import java.time.LocalDateTime;

public class ProductResponseDto {
	
	
	private Long productId;

    private String productName;

    private String productDescription;

    private String brandName;

    private String skuCode;

    private double originalPrice;

    private double discountPrice;

    private int availableQuantity;

    private int soldQuantity;

    private double averageRating;

    private String imageUrl;

    private boolean isAvailable;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
   public ProductResponseDto() {
	// TODO Auto-generated constructor stub
}

	public ProductResponseDto(Long productId, String productName, String productDescription, String brandName,
			String skuCode, double originalPrice, double discountPrice, int availableQuantity, int soldQuantity,
			double averageRating, String imageUrl, boolean isAvailable, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.brandName = brandName;
		this.skuCode = skuCode;
		this.originalPrice = originalPrice;
		this.discountPrice = discountPrice;
		this.availableQuantity = availableQuantity;
		this.soldQuantity = soldQuantity;
		this.averageRating = averageRating;
		this.imageUrl = imageUrl;
		this.isAvailable = isAvailable;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public int getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(int soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "ProductResponseDto [productId=" + productId + ", productName=" + productName + ", productDescription="
				+ productDescription + ", brandName=" + brandName + ", skuCode=" + skuCode + ", originalPrice="
				+ originalPrice + ", discountPrice=" + discountPrice + ", availableQuantity=" + availableQuantity
				+ ", soldQuantity=" + soldQuantity + ", averageRating=" + averageRating + ", imageUrl=" + imageUrl
				+ ", isAvailable=" + isAvailable + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
    
    
	

}
