package com.zentro.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;



    private int rating;

    private String reviewComment;

    private boolean isVerifiedPurchase;

    private LocalDateTime reviewedAt;
    
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;
    
    @JoinColumn(name = "productId")
    @ManyToOne
    private Product product;

    
    public Review() {
		// TODO Auto-generated constructor stub
	}

	public Review(Long productId, int rating, String reviewComment, boolean isVerifiedPurchase,
			LocalDateTime reviewedAt) {
		
		this.setProductId(productId);
		this.rating = rating;
		this.reviewComment = reviewComment;
		this.isVerifiedPurchase = isVerifiedPurchase;
		this.reviewedAt = reviewedAt;
	}

	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public Long getUserId() {
		return user.getId();
	}

	public void setUserId(Long userId) {
		this.user.setId(userId);
	}

	public Long getProductId() {
		return product.getProductId();
	}

	public void setProductId(Long productId) {
		this.product.setProductId(productId);
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public boolean isVerifiedPurchase() {
		return isVerifiedPurchase;
	}

	public void setVerifiedPurchase(boolean isVerifiedPurchase) {
		this.isVerifiedPurchase = isVerifiedPurchase;
	}

	public LocalDateTime getReviewedAt() {
		return reviewedAt;
	}

	public void setReviewedAt(LocalDateTime reviewedAt) {
		this.reviewedAt = reviewedAt;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", userId=" + user.getId() + ", productId=" + product.getProductId() + ", rating=" + rating
				+ ", reviewComment=" + reviewComment + ", isVerifiedPurchase=" + isVerifiedPurchase + "]";
	}
    
    
}
