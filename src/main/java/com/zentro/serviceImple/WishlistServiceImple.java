package com.zentro.serviceImple;

import com.zentro.dto.wishlist.WishlistRequestDto;
import com.zentro.dto.wishlist.WishlistResponseDto;
import com.zentro.model.Product;
import com.zentro.model.User;
import com.zentro.model.Whishlist;
import com.zentro.repository.ProductRepository;
import com.zentro.repository.UserRepository;
import com.zentro.repository.WishlistRepository;
import com.zentro.service.WishlistService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WishlistServiceImple implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishlistServiceImple(
            WishlistRepository wishlistRepository,
            UserRepository userRepository,
            ProductRepository productRepository
    ) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addToWishlist(WishlistRequestDto dto) {
        validateDto(dto);

        if (wishlistRepository.findByUser_IdAndProduct_ProductId(dto.getUserId(), dto.getProductId()).isPresent()) {
            return;
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Whishlist wishlist = new Whishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlist.setAddedAt(LocalDateTime.now());
        wishlistRepository.save(wishlist);
    }

    @Override
    public void removeFromWishlist(WishlistRequestDto dto) {
        validateDto(dto);
        Whishlist wishlist = wishlistRepository.findByUser_IdAndProduct_ProductId(dto.getUserId(), dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Wishlist item not found"));
        wishlistRepository.delete(wishlist);
    }

    @Override
    public List<WishlistResponseDto> getWishlistByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User is required");
        }
        return wishlistRepository.findWishlistByUserId(userId);
    }

    private void validateDto(WishlistRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Wishlist data is required");
        }
        if (dto.getUserId() == null || dto.getUserId() <= 0) {
            throw new IllegalArgumentException("User is required");
        }
        if (dto.getProductId() == null || dto.getProductId() <= 0) {
            throw new IllegalArgumentException("Product is required");
        }
    }
}
