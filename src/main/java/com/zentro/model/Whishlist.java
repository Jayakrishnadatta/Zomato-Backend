package com.zentro.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Whishlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wishlistId;



    private LocalDateTime addedAt;
    
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;
    
    @JoinColumn(name = "productId")
    @ManyToOne
    private Product product;
    
    public Whishlist() {
		// TODO Auto-generated constructor stub
	}


	
	public Whishlist(LocalDateTime addedAt) {
		super();

		this.addedAt = addedAt;
	}



	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(Long wishlistId) {
		this.wishlistId = wishlistId;
	}

	public Long getUserId() {
		return user.getId();
	}

	public void setUserId(Long userId) {
		if (this.user == null) {
			this.user = new User();
		}
		this.user.setId(userId);
	}

	public Long getProductId() {
		return product.getProductId();
	}

	public void setProductId(Long productId) {
		this.product.setProductId(productId);
	}

	public LocalDateTime getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(LocalDateTime addedAt) {
		this.addedAt = addedAt;
	}

	@Override
	public String toString() {
		return "Whishlist [wishlistId=" + wishlistId + ", userId=" + getUserId()+ ", productId=" + product.getProductId() + "]";
	}
    
    
}
