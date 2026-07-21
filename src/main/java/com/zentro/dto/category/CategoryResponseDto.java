package com.zentro.dto.category;

public class CategoryResponseDto {
	
	
	private Long categoryId;

	private String categoryName;

	private String categoryDescription;

	 public CategoryResponseDto() {
		// TODO Auto-generated constructor stub
	}
	
	public CategoryResponseDto(Long categoryId, String categoryName, String categoryDescription) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	@Override
	public String toString() {
		return "CategoryResponseDto [categoryId=" + categoryId + ", categoryName=" + categoryName
				+ ", categoryDescription=" + categoryDescription + "]";
	}
	
	
}
