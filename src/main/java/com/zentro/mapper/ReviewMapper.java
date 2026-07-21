package com.zentro.mapper;


import com.zentro.dto.review.ReviewResponse;
import com.zentro.dto.review.ReviewRequestDto;
import com.zentro.model.Review;

public class ReviewMapper {

    private ReviewMapper() {
    }

    // Request DTO → Entity

    public static Review toEntity(
            ReviewRequestDto dto
    ) {

        Review review =
                new Review();

        review.setRating(
                dto.getRating()
        );

        review.setReviewComment(
                dto.getReviewComment()
        );

        return review;
    }

    // Entity → Response DTO

    public static ReviewResponse
    toResponse(Review review) {

        ReviewResponse response =
                new ReviewResponse();

        response.setReviewId(
                review.getReviewId()
        );

        response.setUserName(
                review.getUser()
                    .getFirstName()
        );

        response.setRating(
                review.getRating()
        );

        response.setReviewComment(
                review.getReviewComment()
        );

        response.setReviewedAt(
                review.getReviewedAt()
        );

        return response;
    }
}
