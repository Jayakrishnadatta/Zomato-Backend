package com.zentro.dto.cart;

import java.util.List;

import com.zentro.dto.cartItem.CartItemResponseDto;

public class CartResponse {

	private Long cartId;

    private Long userId;

    private List<CartItemResponseDto> items;

    private int totalItems;

    private double totalAmount;
    
  

	public CartResponse(Long cartId, Long userId, List<CartItemResponseDto> items, int totalItems, double totalAmount) {
		super();
		this.cartId = cartId;
		this.userId = userId;
		this.items = items;
		this.totalItems = totalItems;
		this.totalAmount = totalAmount;
	}

	public CartResponse() {
		// TODO Auto-generated constructor stub
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CartItemResponseDto> getItems() {
		return items;
	}

	public void setItems(List<CartItemResponseDto> items) {
		this.items = items;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "CartResponse [cartId=" + cartId + ", userId=" + userId + ", totalItems=" + totalItems + ", totalAmount="
				+ totalAmount + "]";
	}
    
    
}
