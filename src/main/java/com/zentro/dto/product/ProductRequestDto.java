package com.zentro.dto.product;

public class ProductRequestDto {

	private String productName;

    private String productDescription;

    private String brandName;

    private String skuCode;

    private double originalPrice;

    private double discountPrice;

    private int availableQuantity;

    private Long categoryId;

    private String imageUrl;

    private boolean isAvailable;
    
    public ProductRequestDto() {
		// TODO Auto-generated constructor stub
	}

	public ProductRequestDto(String productName, String productDescription, String brandName, String skuCode,
			double originalPrice, double discountPrice, int availableQuantity, Long categoryId, String imageUrl,
			boolean isAvailable) {
		super();
		this.productName = productName;
		this.productDescription = productDescription;
		this.brandName = brandName;
		this.skuCode = skuCode;
		this.originalPrice = originalPrice;
		this.discountPrice = discountPrice;
		this.availableQuantity = availableQuantity;
		this.categoryId = categoryId;
		this.imageUrl = imageUrl;
		this.isAvailable = isAvailable;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	@Override
	public String toString() {
		return "ProductRequestDto [productName=" + productName + ", productDescription=" + productDescription
				+ ", brandName=" + brandName + ", skuCode=" + skuCode + ", originalPrice=" + originalPrice
				+ ", discountPrice=" + discountPrice + ", availableQuantity=" + availableQuantity + ", categoryId="
				+ categoryId + ", imageUrl=" + imageUrl + ", isAvailable=" + isAvailable + "]";
	}
    
    
}
