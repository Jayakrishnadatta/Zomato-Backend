package com.zentro.serviceImple;

import com.zentro.dto.review.ReviewResponse;
import com.zentro.dto.review.ReviewRequestDto;
import com.zentro.mapper.ReviewMapper;
import com.zentro.model.Product;
import com.zentro.model.Review;
import com.zentro.model.User;
import com.zentro.repository.ProductRepository;
import com.zentro.repository.ReviewRepository;
import com.zentro.repository.UserRepository;
import com.zentro.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImple implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewServiceImple(
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            ProductRepository productRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ReviewResponse addReview(Long userId, ReviewRequestDto dto) {
        validateUserId(userId);
        validateDto(dto, true);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Review review = ReviewMapper.toEntity(dto);
        review.setUser(user);
        review.setProduct(product);
        review.setReviewedAt(LocalDateTime.now());

        return ReviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    public ReviewResponse updateReview(Long reviewId, ReviewRequestDto dto) {
        validateReviewId(reviewId);
        validateDto(dto, false);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        review.setRating(dto.getRating());
        review.setReviewComment(dto.getReviewComment());
        review.setReviewedAt(LocalDateTime.now());

        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            review.setProduct(product);
        }

        return ReviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Long reviewId) {
        validateReviewId(reviewId);
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product is required");
        }
        return reviewRepository.findReviewResponsesByProductId(productId);
    }

    private void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User is required");
        }
    }

    private void validateReviewId(Long reviewId) {
        if (reviewId == null || reviewId <= 0) {
            throw new IllegalArgumentException("Review is required");
        }
    }

    private void validateDto(ReviewRequestDto dto, boolean productRequired) {
        if (dto == null) {
            throw new IllegalArgumentException("Review data is required");
        }
        if (productRequired && dto.getProductId() == null) {
            throw new IllegalArgumentException("Product is required");
        }
        if (dto.getProductId() != null && dto.getProductId() <= 0) {
            throw new IllegalArgumentException("Product is required");
        }
        if (dto.getRating() < 1 || dto.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }
}
