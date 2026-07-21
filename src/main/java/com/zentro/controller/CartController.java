package com.zentro.controller;

import com.zentro.dto.cart.CartResponse;
import com.zentro.dto.cartItem.AddToCartDto;
import com.zentro.service.CartServlet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartServlet cartServlet;

    public CartController(CartServlet cartServlet) {
        this.cartServlet = cartServlet;
    }

    @PostMapping("/{userId}/items")
    public CartResponse addToCart(@PathVariable Long userId, @RequestBody AddToCartDto dto) {
        return cartServlet.addToCart(userId, dto);
    }

    @PatchMapping("/{userId}/items/{productId}/quantity/{quantity}")
    public CartResponse updateCartItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @PathVariable int quantity
    ) {
        return cartServlet.updateCartItemQuantity(userId, productId, quantity);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public CartResponse removeFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        return cartServlet.removeFromCart(userId, productId);
    }

    @GetMapping("/{userId}")
    public CartResponse getCartByUserId(@PathVariable Long userId) {
        return cartServlet.getCartByUserId(userId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartServlet.clearCart(userId);
    }
}
