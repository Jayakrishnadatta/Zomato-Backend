package com.zentro.dto.category;

public class CategoryRequestDto {

	private String categoryName;

	private String categoryDescription;

	public CategoryRequestDto() {
	}

	public CategoryRequestDto(String categoryName, String categoryDescription) {
		super();
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
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
		return "CategoryRequestDto [categoryName=" + categoryName + ", categoryDescription=" + categoryDescription
				+ "]";
	}
	
	
}
