package com.zentro.repository;

import com.zentro.dto.category.CategoryResponseDto;
import com.zentro.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query("select new com.zentro.dto.category.CategoryResponseDto(c.categoryId, c.categoryName, c.categoryDescription) from Category c where c.categoryId = :categoryId")
    CategoryResponseDto getCategoryByCategoryId(@Param("categoryId") long categoryId);

    @Query("select new com.zentro.dto.category.CategoryResponseDto(c.categoryId, c.categoryName, c.categoryDescription) from Category c")
    List<CategoryResponseDto> getAllCategory();

}
