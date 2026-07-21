package com.zentro.serviceImple;

import com.zentro.dto.product.ProductRequestDto;
import com.zentro.dto.product.ProductResponseDto;
import com.zentro.mapper.ProductMapper;
import com.zentro.model.Category;
import com.zentro.model.Product;
import com.zentro.repository.CategoryRepository;
import com.zentro.repository.ProductRepository;
import com.zentro.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ProductServiceImple implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public ProductServiceImple(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductResponseDto addProduct(ProductRequestDto dto) {

        validateDto(dto);

        Product product = ProductMapper.toEntity(dto);
        setCategory(product, dto.getCategoryId());

        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto dto) {
        validateProductId(productId);
        validateDto(dto);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setProductName(dto.getProductName());
        product.setProductDescription(dto.getProductDescription());
        product.setBrandName(dto.getBrandName());
        product.setSkuCode(dto.getSkuCode());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setDiscountPrice(dto.getDiscountPrice());
        product.setAvailableQuantity(dto.getAvailableQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setAvailable(dto.isAvailable());
        setCategory(product, dto.getCategoryId());

        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long productId) {
        validateProductId(productId);
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product not found");
        }

        productRepository.deleteById(productId);

    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        validateProductId(productId);


        ProductResponseDto response = productRepository.findByProductId(productId);
        if (response == null) {
            throw new IllegalArgumentException("Product not found");
        }
        return response;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAllProduct();
    }

    @Override
    public List<ProductResponseDto> searchProducts(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return productRepository.findAllProduct();
        }

        return productRepository.searchProducts(keyword);
    }

    @Override
    public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("Category is required");
        }
        return productRepository.findByCategoryId(categoryId);
    }

    private void setCategory(Product product, Long categoryId) {
        if (categoryId == null) {
            return;
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        product.setCategory(category);
    }

    private void validateProductId(Long productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product is required");
        }
    }

    private void validateDto(ProductRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Product data is required");
        }
        if (dto.getProductName() == null || dto.getProductName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (dto.getProductDescription() == null || dto.getProductDescription().isBlank()) {
            throw new IllegalArgumentException("Product description is required");
        }
        if (dto.getBrandName() == null || dto.getBrandName().isBlank()) {
            throw new IllegalArgumentException("Brand name is required");
        }
        if (dto.getSkuCode() == null || dto.getSkuCode().isBlank()) {
            throw new IllegalArgumentException("SKU code is required");
        }
        if (dto.getOriginalPrice() <= 0) {
            throw new IllegalArgumentException("Original price must be greater than zero");
        }
        if (dto.getDiscountPrice() < 0) {
            throw new IllegalArgumentException("Discount price cannot be negative");
        }
        if (dto.getAvailableQuantity() < 0) {
            throw new IllegalArgumentException("Available quantity cannot be negative");
        }
        if (dto.getCategoryId() == null || dto.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Category ID is required");
        }
        if (dto.getImageUrl() == null || dto.getImageUrl().isBlank()) {
            throw new IllegalArgumentException("Image URL is required");
        }
    }
}
