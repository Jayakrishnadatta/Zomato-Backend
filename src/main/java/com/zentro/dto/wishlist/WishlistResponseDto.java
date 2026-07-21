package com.zentro.dto.wishlist;

public class WishlistResponseDto {
	
    private Long wishlistId;

    private Long productId;

    private String productName;

    private double productPrice;

    private String imageUrl;

	public WishlistResponseDto(Long wishlistId, Long productId, String productName, double productPrice, String imageUrl) {
		super();
		this.wishlistId = wishlistId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.imageUrl = imageUrl;
	}

	public WishlistResponseDto() {
		// TODO Auto-generated constructor stub
	}

	public Long getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(Long wishlistId) {
		this.wishlistId = wishlistId;
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

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "WishlistResponseDto [wishlistId=" + wishlistId + ", productId=" + productId + ", productName=" + productName
				+ ", productPrice=" + productPrice + ", imageUrl=" + imageUrl + "]";
	}
    
    

}
