package com.zentro.mapper;

import java.util.stream.Collectors;

import com.zentro.dto.cart.CartResponse;
import com.zentro.model.Cart;

public class CartMapper {

    private CartMapper() {
    }

    public static CartResponse
    toResponse(Cart cart) {

        CartResponse response =
                new CartResponse();

        response.setCartId(
                cart.getCartId()
        );

        response.setUserId(
                cart.getUser()
                    .getId()
        );

        response.setTotalItems(
                cart.getTotalItems()
        );

        response.setTotalAmount(
                cart.getTotalPrice()
        );

        response.setItems(
                cart.getCartItems()
                    .stream()
                    .map(
                        CartItemMapper
                        ::toResponse
                    )
                    .collect(
                        Collectors.toList()
                    )
        );

        return response;
    }
}