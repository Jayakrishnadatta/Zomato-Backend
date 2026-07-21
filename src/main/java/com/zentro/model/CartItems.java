package com.zentro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;



    private int quantity;

    private double itemPrice;

    private double totalPrice;
    
    @JoinColumn(name = "cartId")
    @ManyToOne
    private Cart cart;
    
    @JoinColumn(name = "productId")
    @ManyToOne
    private Product product;
    
    
    public CartItems() {
		// TODO Auto-generated constructor stub
	}
	public CartItems(Long cartId, Long productId, int quantity, double itemPrice, double totalPrice) {
		super();
		this.cart.setCartId(cartId);
		this.product.setProductId(productId);
		this.quantity = quantity;
		this.itemPrice = itemPrice;
		this.totalPrice = totalPrice;
	}
	
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Long getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}
	public Long getCartId() {
		return cart.getCartId();
	}
	public void setCartId(Long cartId) {
		this.cart.setCartId(cartId);
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
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Override
	public String toString() {
		return "CartItems [cartItemId=" + cartItemId + ", cartId=" + cart.getCartId() + ", productId=" + product.getProductId() + ", quantity="
				+ quantity + ", itemPrice=" + itemPrice + ", totalPrice=" + totalPrice + "]";
	}
    
	
}
