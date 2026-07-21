package com.zentro.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;


	    private double totalPrice;

	    private int totalItems;

	    @CreationTimestamp
	    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

	    @UpdateTimestamp
	    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	    private LocalDateTime updatedAt;
	    
	    
	    @JoinColumn(name = "userId")
	    @OneToOne(cascade = CascadeType.ALL)
	    private User user;
	    
	    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
	    private List<CartItems> cartItems;
	    
	    

	    public Cart() {
			// TODO Auto-generated constructor stub
		}

		public Cart(double totalPrice, int totalItems, LocalDateTime createdAt) {
			super();
			this.totalPrice = totalPrice;
			this.totalItems = totalItems;
			this.createdAt = createdAt;
		}
		
		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public List<CartItems> getCartItems() {
			return cartItems;
		}

		public void setCartItems(List<CartItems> cartItems) {
			this.cartItems = cartItems;
		}

		public Long getCartId() {
			return cartId;
		}

		public void setCartId(Long cartId) {
			this.cartId = cartId;
		}

	

		public double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}

		public int getTotalItems() {
			return totalItems;
		}

		public void setTotalItems(int totalItems) {
			this.totalItems = totalItems;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		@Override
		public String toString() {
			return "Cart [cartId=" + cartId + ", userId=" + user.getId() + ", totalPrice=" + totalPrice + ", totalItems="
					+ totalItems + "]";
		}
	    
	    
	    
}
