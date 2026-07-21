package com.zentro.service;

import java.util.List;

import com.zentro.dto.product.ProductRequestDto;
import com.zentro.dto.product.ProductResponseDto;

public interface ProductService {

    ProductResponseDto addProduct(
            ProductRequestDto dto
    );

    ProductResponseDto updateProduct(
            Long productId,
            ProductRequestDto dto
    );

    void deleteProduct(Long productId);

    ProductResponseDto getProductById(
            Long productId
    );

    List<ProductResponseDto>
    getAllProducts();

    List<ProductResponseDto>
    searchProducts(String keyword);

    List<ProductResponseDto>
    getProductsByCategory(
            Long categoryId
    );

}
