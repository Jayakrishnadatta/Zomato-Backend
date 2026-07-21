package com.zentro.mapper;

import com.zentro.dto.cartItem.AddToCartDto;
import com.zentro.dto.cartItem.CartItemResponseDto;
import com.zentro.model.CartItems;

public class CartItemMapper {

    private CartItemMapper() {
    }
    
    public class AddToCartMapper {

        private AddToCartMapper() {
        }

        public static CartItems toEntity(
                AddToCartDto dto
        ) {

            CartItems cartItems =
                    new CartItems();

            cartItems.setQuantity(
                    dto.getQuantity()
            );

            return cartItems;
        }
    };
    
    

    public static CartItemResponseDto
    toResponse(CartItems cartItems) {

        CartItemResponseDto response =
                new CartItemResponseDto();

        response.setCartItemId(
                cartItems.getCartItemId()
        );

        response.setProductId(
                cartItems.getProduct()
                    .getProductId()
        );

        response.setProductName(
                cartItems.getProduct()
                    .getProductName()
        );

        response.setQuantity(
                cartItems.getQuantity()
        );

        response.setPrice(
                cartItems.getItemPrice()
        );

        response.setTotalPrice(
                cartItems.getTotalPrice()
        );

        response.setImageUrl(
                cartItems.getProduct()
                    .getImageUrl()
        );

        return response;
    }
}