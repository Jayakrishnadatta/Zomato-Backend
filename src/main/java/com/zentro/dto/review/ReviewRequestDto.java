package com.zentro.dto.review;

public class ReviewRequestDto {

	private Long productId;

	private int rating;

	private String reviewComment;

	public ReviewRequestDto() {
	}

	public ReviewRequestDto(Long productId, int rating, String reviewComment) {
		this.productId = productId;
		this.rating = rating;
		this.reviewComment = reviewComment;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	@Override
	public String toString() {
		return "ReviewRequestDto [productId=" + productId + ", rating=" + rating + ", reviewComment=" + reviewComment
				+ "]";
	}
}
