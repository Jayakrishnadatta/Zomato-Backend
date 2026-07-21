package com.zentro.dto.review;

import java.time.LocalDateTime;

public class ReviewResponse {

    private Long reviewId;

    private String userName;

    private int rating;

    private String reviewComment;

    private LocalDateTime reviewedAt;
    
  
	public ReviewResponse(Long reviewId, String userName, int rating, String reviewComment, LocalDateTime reviewedAt) {
		super();
		this.reviewId = reviewId;
		this.userName = userName;
		this.rating = rating;
		this.reviewComment = reviewComment;
		this.reviewedAt = reviewedAt;
	}

	public ReviewResponse() {
		// TODO Auto-generated constructor stub
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public LocalDateTime getReviewedAt() {
		return reviewedAt;
	}

	public void setReviewedAt(LocalDateTime reviewedAt) {
		this.reviewedAt = reviewedAt;
	}

	@Override
	public String toString() {
		return "ReviewResponse [reviewId=" + reviewId + ", userName=" + userName + ", rating=" + rating
				+ ", reviewComment=" + reviewComment + "]";
	}
    
    
}
