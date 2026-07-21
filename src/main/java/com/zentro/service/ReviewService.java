package com.zentro.service;

import java.util.List;

import com.zentro.dto.review.ReviewResponse;
import com.zentro.dto.review.ReviewRequestDto;

public interface ReviewService {

    ReviewResponse addReview(
            Long userId,
            ReviewRequestDto dto
    );

    ReviewResponse updateReview(
            Long reviewId,
            ReviewRequestDto dto
    );

    void deleteReview(Long reviewId);

    List<ReviewResponse>
    getReviewsByProductId(
            Long productId
    );
}
