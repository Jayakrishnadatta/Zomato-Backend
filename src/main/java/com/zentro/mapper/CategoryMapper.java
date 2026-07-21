package com.zentro.mapper;

import com.zentro.dto.category.CategoryRequestDto;
import com.zentro.dto.category.CategoryResponseDto;
import com.zentro.model.Category;

public class CategoryMapper {

    private CategoryMapper() {
    }

    // Request DTO → Entity

    public static Category toEntity(
            CategoryRequestDto dto
    ) {

        Category category =
                new Category();

        category.setCategoryName(
                dto.getCategoryName()
        );

        category.setCategoryDescription(
                dto.getCategoryDescription()
        );

        return category;
    }

    // Entity → Response DTO

    public static CategoryResponseDto
    toResponse(Category category) {

        CategoryResponseDto response =
                new CategoryResponseDto();

        response.setCategoryId(
                category.getCategoryId()
        );

        response.setCategoryName(
                category.getCategoryName()
        );

        response.setCategoryDescription(
                category.getCategoryDescription()
        );

        return response;
    }
}