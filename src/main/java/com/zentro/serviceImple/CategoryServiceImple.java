package com.zentro.serviceImple;

import com.zentro.dto.category.CategoryRequestDto;
import com.zentro.dto.category.CategoryResponseDto;
import com.zentro.mapper.CategoryMapper;
import com.zentro.model.Category;
import com.zentro.repository.CategoryRepository;
import com.zentro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImple implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImple(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto dto) {

        validateDto(dto);

        Category category = categoryRepository.save(CategoryMapper.toEntity(dto));

        return CategoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto dto) {
        validateCategoryId(categoryId);
        validateDto(dto);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        category.setCategoryName(dto.getCategoryName());
        category.setCategoryDescription(dto.getCategoryDescription());

        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        validateCategoryId(categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Category not found");
        }

        categoryRepository.deleteById(categoryId);

    }

    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {
        validateCategoryId(categoryId);

        CategoryResponseDto response = categoryRepository.getCategoryByCategoryId(categoryId);
        if (response == null) {
            throw new IllegalArgumentException("Category not found");
        }
        return response;
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.getAllCategory();
    }

    private void validateCategoryId(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("Category is required");
        }
    }

    private void validateDto(CategoryRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Category data is required");
        }
    }
}
