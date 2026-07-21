package com.zentro.service;

import java.util.List;

import com.zentro.dto.wishlist.WishlistRequestDto;
import com.zentro.dto.wishlist.WishlistResponseDto;

public interface WishlistService {

    void addToWishlist(
            WishlistRequestDto dto
    );

    void removeFromWishlist(
            WishlistRequestDto dto
    );

    List<WishlistResponseDto>
    getWishlistByUserId(
            Long userId
    );
}
