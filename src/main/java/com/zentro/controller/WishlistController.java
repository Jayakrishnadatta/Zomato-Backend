package com.zentro.controller;

import com.zentro.dto.wishlist.WishlistRequestDto;
import com.zentro.dto.wishlist.WishlistResponseDto;
import com.zentro.service.WishlistService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/")
    public void addToWishlist(@RequestBody WishlistRequestDto dto) {
        wishlistService.addToWishlist(dto);
    }

    @DeleteMapping("/")
    public void removeFromWishlist(@RequestBody WishlistRequestDto dto) {
        wishlistService.removeFromWishlist(dto);
    }

    @GetMapping("/user/{userId}")
    public List<WishlistResponseDto> getWishlistByUserId(@PathVariable Long userId) {
        return wishlistService.getWishlistByUserId(userId);
    }
}
