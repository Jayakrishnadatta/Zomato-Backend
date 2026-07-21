package com.zentro.service;

import com.zentro.dto.cart.CartResponse;
import com.zentro.dto.cartItem.AddToCartDto;

public interface CartServlet {

    CartResponse addToCart(
            Long userId,
            AddToCartDto dto
    );

    CartResponse updateCartItemQuantity(
            Long userId,
            Long productId,
            int quantity
    );

    CartResponse removeFromCart(
            Long userId,
            Long productId
    );

    CartResponse getCartByUserId(
            Long userId
    );

    void clearCart(Long userId);
}
