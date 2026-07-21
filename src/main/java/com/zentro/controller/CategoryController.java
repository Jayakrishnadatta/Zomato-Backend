package com.zentro.controller;

import com.zentro.dto.category.CategoryRequestDto;
import com.zentro.dto.category.CategoryResponseDto;
import com.zentro.service.CategoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    public CategoryResponseDto addCategory(@RequestBody CategoryRequestDto dto)
    {
        return categoryService.addCategory(dto);
    }

    @PutMapping("/{categoryId}")
    public  CategoryResponseDto updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequestDto dto)
    {
        return categoryService.updateCategory(categoryId,dto);
    }

    @GetMapping("/{categoryId}")
    public CategoryResponseDto getByCategoryId(@PathVariable Long categoryId)
    {
        return  categoryService.getCategoryById(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId)
    {
        categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/")

    public List<CategoryResponseDto> getAllCategory (){
        return categoryService.getAllCategories();
    }

}
