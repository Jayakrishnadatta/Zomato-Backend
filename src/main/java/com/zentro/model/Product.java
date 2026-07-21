package com.zentro.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long productId;

	    private String productName;

	    private String productDescription;

	    private String brandName;

	    private String skuCode;

	    private double originalPrice;

	    private double discountPrice;

	    private int availableQuantity;

	    private int soldQuantity;

	    private double averageRating;

	    private String imageUrl;
	    

	    private boolean isAvailable;


	  @CreationTimestamp
	  @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	  private LocalDateTime createdAt;

 
	 @UpdateTimestamp
	 @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	  private LocalDateTime updatedAt;
	    
	    @OneToMany(mappedBy = "product" ,cascade = CascadeType.ALL)
	    
	    private List<OrderItems> orderItems;	
	    
	    
	    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	    private List<CartItems> cartItems;
	    
	    @JoinColumn(name = "categoryId")
	    @ManyToOne(cascade = CascadeType.ALL)
	     private Category category;
	    

	    
	    
	    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	    private List<Whishlist> whishlist;
	    
	    
	    
	    public Product() {
			// TODO Auto-generated constructor stub
		}

		public Product(String productName, String productDescription, String brandName, String skuCode,
				double originalPrice, double discountPrice, int availableQuantity, int soldQuantity,
				double averageRating,  boolean isAvailable, LocalDateTime updatedAt,String imageUrl) {
			super();
			this.productName = productName;
			this.productDescription = productDescription;
			this.brandName = brandName;
			this.skuCode = skuCode;
			this.originalPrice = originalPrice;
			this.discountPrice = discountPrice;
			this.availableQuantity = availableQuantity;
			this.soldQuantity = soldQuantity;
			this.averageRating = averageRating;
			this.isAvailable = isAvailable;
			this.updatedAt = updatedAt;
			this.imageUrl=imageUrl;
		}
		
		
		
		public List<Whishlist> getWhishlist() {
			return whishlist;
		}

		

		public void setWhishlist(List<Whishlist> whishlist) {
			this.whishlist = whishlist;
		}

		public Category getCategory() {
			return category;
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		public List<CartItems> getCartItems() {
			return cartItems;
		}

		public void setCartItems(List<CartItems> cartItems) {
			this.cartItems = cartItems;
		}

		public List<OrderItems> getOrderItems() {
			return orderItems;
		}

		public void setOrderItems(List<OrderItems> orderItems) {
			this.orderItems = orderItems;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getProductDescription() {
			return productDescription;
		}

		public void setProductDescription(String productDescription) {
			this.productDescription = productDescription;
		}

		public String getBrandName() {
			return brandName;
		}

		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}

		public String getSkuCode() {
			return skuCode;
		}

		public void setSkuCode(String skuCode) {
			this.skuCode = skuCode;
		}

		public double getOriginalPrice() {
			return originalPrice;
		}

		public void setOriginalPrice(double originalPrice) {
			this.originalPrice = originalPrice;
		}

		public double getDiscountPrice() {
			return discountPrice;
		}

		public void setDiscountPrice(double discountPrice) {
			this.discountPrice = discountPrice;
		}

		public int getAvailableQuantity() {
			return availableQuantity;
		}

		public void setAvailableQuantity(int availableQuantity) {
			this.availableQuantity = availableQuantity;
		}

		public int getSoldQuantity() {
			return soldQuantity;
		}

		public void setSoldQuantity(int soldQuantity) {
			this.soldQuantity = soldQuantity;
		}

		public double getAverageRating() {
			return averageRating;
		}

		public void setAverageRating(double averageRating) {
			this.averageRating = averageRating;
		}

		public Long getCategoryId() {
			return category.getCategoryId();
		}

		public void setCategoryId(Long categoryId) {
			this.category.setCategoryId(categoryId);
		}

		public boolean isAvailable() {
			return isAvailable;
		}

		public void setAvailable(boolean isAvailable) {
			this.isAvailable = isAvailable;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}

		@Override
		public String toString() {
			return "Product [productId=" + productId + ", productName=" + productName + ", productDescription="
					+ productDescription + ", brandName=" + brandName + ", skuCode=" + skuCode + ", originalPrice="
					+ originalPrice + ", discountPrice=" + discountPrice + ", availableQuantity=" + availableQuantity
					+ ", soldQuantity=" + soldQuantity + ", averageRating=" + averageRating + ", imageUrl=" + imageUrl
					+ ", categoryId=" + category.getCategoryId() + ", isAvailable=" + isAvailable + ", createdAt=" + createdAt
					+ ", updatedAt=" + updatedAt + ", orderItems=" + orderItems + "]";
		}

		
	    
	    
}
