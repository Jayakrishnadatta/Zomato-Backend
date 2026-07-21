package com.zentro.service;

import java.util.List;

import com.zentro.dto.category.CategoryRequestDto;
import com.zentro.dto.category.CategoryResponseDto;

public interface CategoryService {

    CategoryResponseDto addCategory(
            CategoryRequestDto dto
    );

    CategoryResponseDto updateCategory(
            Long categoryId,
            CategoryRequestDto dto
    );

    void deleteCategory(Long categoryId);

    CategoryResponseDto getCategoryById(
            Long categoryId
    );

    List<CategoryResponseDto>
    getAllCategories();
}