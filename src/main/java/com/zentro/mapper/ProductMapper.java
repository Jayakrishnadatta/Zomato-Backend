package com.zentro.mapper;

import com.zentro.dto.product.ProductRequestDto;
import com.zentro.dto.product.ProductResponseDto;
import com.zentro.model.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    // Request DTO → Entity
    public static Product toEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setProductDescription(dto.getProductDescription());
        product.setBrandName(dto.getBrandName());
        product.setSkuCode(dto.getSkuCode());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setDiscountPrice(dto.getDiscountPrice());
        product.setAvailableQuantity(dto.getAvailableQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setAvailable(dto.isAvailable());
        return product;
    }

    // Entity → Response DTO
    public static ProductResponseDto toResponse(Product product) {
        ProductResponseDto response = new ProductResponseDto();
        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setProductDescription(product.getProductDescription());
        response.setBrandName(product.getBrandName());
        response.setSkuCode(product.getSkuCode());
        response.setOriginalPrice(product.getOriginalPrice());
        response.setDiscountPrice(product.getDiscountPrice());
        response.setAvailableQuantity(product.getAvailableQuantity());
        response.setSoldQuantity(product.getSoldQuantity());
        response.setAverageRating(product.getAverageRating());
        response.setImageUrl(product.getImageUrl());
        response.setAvailable(product.isAvailable());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }
}
