package com.zentro.repository;

import com.zentro.dto.review.ReviewResponse;
import com.zentro.model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query("select new com.zentro.dto.review.ReviewResponse(r.reviewId, r.user.firstName, r.rating, r.reviewComment, r.reviewedAt) from Review r where r.product.productId = :productId")
    List<ReviewResponse> findReviewResponsesByProductId(@Param("productId") Long productId);
}
