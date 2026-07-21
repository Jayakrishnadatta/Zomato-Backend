package com.zentro.dto.cartItem;

public class AddToCartDto {
	
	private Long productId;

    private int quantity;
    
 
	public AddToCartDto() {
	}

	public AddToCartDto(Long productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "AddToCartDto [productId=" + productId + ", quantity=" + quantity + "]";
	}
    
    

}
