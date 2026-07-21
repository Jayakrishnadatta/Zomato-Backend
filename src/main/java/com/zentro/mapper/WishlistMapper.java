package com.zentro.mapper;

import com.zentro.dto.wishlist.WishlistResponseDto;
import com.zentro.model.Whishlist;

public class WishlistMapper {

    private WishlistMapper() {
    }

    public static WishlistResponseDto
    toResponse(Whishlist wishlist) {

        WishlistResponseDto response =
                new WishlistResponseDto();

        response.setWishlistId(
                wishlist.getWishlistId()
        );

        response.setProductId(
                wishlist.getProduct()
                    .getProductId()
        );

        response.setProductName(
                wishlist.getProduct()
                    .getProductName()
        );

        response.setProductPrice(
                wishlist.getProduct()
                    .getDiscountPrice()
        );

        response.setImageUrl(
                wishlist.getProduct()
                    .getImageUrl()
        );

        return response;
    }
}