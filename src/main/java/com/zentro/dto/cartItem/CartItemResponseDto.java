package com.zentro.dto.cartItem;

public class CartItemResponseDto {
	
	private Long cartItemId;

    private Long productId;

    private String productName;

    private int quantity;

    private double price;

    private double totalPrice;

    private String imageUrl;

	public CartItemResponseDto(Long cartItemId, Long productId, String productName, int quantity, double price,
			double totalPrice, String imageUrl) {
		super();
		this.cartItemId = cartItemId;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
		this.imageUrl = imageUrl;
	}

	public CartItemResponseDto() {
	}

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "CartItemResponseDto [cartItemId=" + cartItemId + ", productId=" + productId + ", productName="
				+ productName + ", quantity=" + quantity + ", price=" + price + ", totalPrice=" + totalPrice
				+ ", imageUrl=" + imageUrl + "]";
	}
}
