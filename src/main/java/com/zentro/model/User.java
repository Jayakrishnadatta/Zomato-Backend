package com.zentro.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long Id;

	    private String firstName;
	    private String lastName;

	    @Column(unique = true)
	    private String email;

	    private String mobileNumber;

	    private String passwordHash;

	    private String role;

	    private boolean isAccountVerified;

	    private boolean isActive;

	    @CreationTimestamp
	    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	    private LocalDateTime createdAt;

	    @UpdateTimestamp
	    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	    private LocalDateTime updatedAt;
	    
	    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
	    private List<Order> order;
	    
	    
	    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	    private List<Address> address;
	    
	    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
	    private Cart cart;
	    
	    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	    private List<Review> review;
	    
	    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	    private List<Whishlist> whishList;
	    
	    
	    

	    	public User() {
				// TODO Auto-generated constructor stub
			}
	    	
		public User(String firstName, String lastName, String email, String mobileNumber, String passwordHash,
				String role, boolean isAccountVerified, boolean isActive, LocalDateTime updatedAt) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.mobileNumber = mobileNumber;
			this.passwordHash = passwordHash;
			this.role = role;
			this.isAccountVerified = isAccountVerified;
			this.isActive = isActive;
			this.updatedAt = updatedAt;
		}

		
		
		
		public List<Review> getReview() {
			return review;
		}

		public void setReview(List<Review> review) {
			this.review = review;
		}

		public List<Whishlist> getWhishList() {
			return whishList;
		}

		public void setWhishList(List<Whishlist> whishList) {
			this.whishList = whishList;
		}

		public Cart getCart() {
			return cart;
		}

		public void setCart(Cart cart) {
			this.cart = cart;
		}

		public List<Address> getAddress() {
			return address;
		}

		public void setAddress(List<Address> address) {
			this.address = address;
		}

		public List<Order> getOrder() {
			return order;
		}

		public void setOrder(List<Order> order) {
			this.order = order;
		}

		public Long getId() {
			return Id;
		}

		public void setId(Long Id) {
			this.Id = Id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getMobileNumber() {
			return mobileNumber;
		}

		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}

		public String getPasswordHash() {
			return passwordHash;
		}

		public void setPasswordHash(String passwordHash) {
			this.passwordHash = passwordHash;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public boolean isAccountVerified() {
			return isAccountVerified;
		}

		public void setAccountVerified(boolean isAccountVerified) {
			this.isAccountVerified = isAccountVerified;
		}

		public boolean isActive() {
			return isActive;
		}

		public void setActive(boolean isActive) {
			this.isActive = isActive;
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
			return "User [userId=" + Id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
					+ ", mobileNumber=" + mobileNumber + ", passwordHash=" + passwordHash + ", role=" + role
					+ ", isAccountVerified=" + isAccountVerified + ", isActive=" + isActive + ", createdAt=" + createdAt
					+ ", updatedAt=" + updatedAt + "]";
		}

		
		
	    
	
	
}
