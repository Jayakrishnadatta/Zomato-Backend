package com.zentro.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
 

	    private String categoryName;

	    private String categoryDescription;

	    private Long parentCategoryId;

	    private boolean isActive;
	    
	    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	    private List<Product> product;
	    
	    public Category() {
			// TODO Auto-generated constructor stub
		}

		public Category(String categoryName, String categoryDescription, Long parentCategoryId, boolean isActive) {
			super();
			this.categoryName = categoryName;
			this.categoryDescription = categoryDescription;
			this.parentCategoryId = parentCategoryId;
			this.isActive = isActive;
		}
		
	

		public List<Product> getProduct() {
			return product;
		}

		public void setProduct(List<Product> product) {
			this.product = product;
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

		public Long getParentCategoryId() {
			return parentCategoryId;
		}

		public void setParentCategoryId(Long parentCategoryId) {
			this.parentCategoryId = parentCategoryId;
		}

		public boolean isActive() {
			return isActive;
		}

		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}

		@Override
		public String toString() {
			return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + ", categoryDescription="
					+ categoryDescription + ", parentCategoryId=" + parentCategoryId + ", isActive=" + isActive + "]";
		}
	    
	    
	
}
