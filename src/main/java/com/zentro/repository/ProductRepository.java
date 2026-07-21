package com.zentro.repository;

import com.zentro.dto.product.ProductResponseDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zentro.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {


    @Query("select new com.zentro.dto.product.ProductResponseDto(p.productId, p.productName, p.productDescription, p.brandName, p.skuCode, p.originalPrice, p.discountPrice, p.availableQuantity, p.soldQuantity, p.averageRating, p.imageUrl, p.isAvailable, p.createdAt, p.updatedAt) from Product p where p.productId = :productId")
    ProductResponseDto findByProductId(@Param("productId") long productId);


    @Query("select new com.zentro.dto.product.ProductResponseDto(p.productId, p.productName, p.productDescription, p.brandName, p.skuCode, p.originalPrice, p.discountPrice, p.availableQuantity, p.soldQuantity, p.averageRating, p.imageUrl, p.isAvailable, p.createdAt, p.updatedAt) from Product p")
    List<ProductResponseDto> findAllProduct();


    @Query("select new com.zentro.dto.product.ProductResponseDto(p.productId, p.productName, p.productDescription, p.brandName, p.skuCode, p.originalPrice, p.discountPrice, p.availableQuantity, p.soldQuantity, p.averageRating, p.imageUrl, p.isAvailable, p.createdAt, p.updatedAt) from Product p where lower(p.productName) like lower(concat('%', :keyword, '%')) or lower(p.productDescription) like lower(concat('%', :keyword, '%')) or lower(p.brandName) like lower(concat('%', :keyword, '%'))")
    List<ProductResponseDto> searchProducts(@Param("keyword") String keyword);

    @Query("select new com.zentro.dto.product.ProductResponseDto(p.productId, p.productName, p.productDescription, p.brandName, p.skuCode, p.originalPrice, p.discountPrice, p.availableQuantity, p.soldQuantity, p.averageRating, p.imageUrl, p.isAvailable, p.createdAt, p.updatedAt) from Product p where p.category.categoryId = :categoryId")
    List<ProductResponseDto> findByCategoryId(@Param("categoryId") Long categoryId);

    void deleteByProductId(long productId);
}
