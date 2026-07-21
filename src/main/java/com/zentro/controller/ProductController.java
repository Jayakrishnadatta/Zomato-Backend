package com.zentro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zentro.dto.product.ProductRequestDto;
import com.zentro.dto.product.ProductResponseDto;
import com.zentro.service.ProductService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/")
	public ProductResponseDto addProduct(@RequestBody ProductRequestDto dto) {
		return productService.addProduct(dto);
	}

	@PutMapping("/{productId}")
	public ProductResponseDto updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto dto) {
		return productService.updateProduct(productId, dto);
	}

	@DeleteMapping("/{productId}")
	public void deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
	}

	@GetMapping("/{productId}")
	public ProductResponseDto getProductById(@PathVariable Long productId) {
		return productService.getProductById(productId);
	}

	@GetMapping("/")
	public List<ProductResponseDto> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/search/{keyword}")
	public List<ProductResponseDto> searchProducts(@PathVariable String keyword) {
		return productService.searchProducts(keyword);
	}

	@GetMapping("/category/{categoryId}")
	public List<ProductResponseDto> getProductsByCategory(@PathVariable Long categoryId) {
		return productService.getProductsByCategory(categoryId);
	}
}
