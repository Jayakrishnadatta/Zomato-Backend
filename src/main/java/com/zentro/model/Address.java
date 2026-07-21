package com.zentro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long addressId;


	    private String fullName;

	    private String mobileNumber;

	    private String houseNumber;

	    private String streetName;

	    private String landmark;

	    private String city;

	    private String state;

	    private String country;

	    private String postalCode;

	    private String addressType;

	    private boolean isDefault;
	    
	    @JoinColumn(name = "userId")
	    @ManyToOne
	    private User user;
	    
	    public Address() {
			// TODO Auto-generated constructor stub
		}

		public Address(String fullName, String mobileNumber, String houseNumber, String streetName,
				String landmark, String city, String state, String country, String postalCode, String addressType,
				boolean isDefault) {
			super();
			this.fullName = fullName;
			this.mobileNumber = mobileNumber;
			this.houseNumber = houseNumber;
			this.streetName = streetName;
			this.landmark = landmark;
			this.city = city;
			this.state = state;
			this.country = country;
			this.postalCode = postalCode;
			this.addressType = addressType;
			this.isDefault = isDefault;
		}

		public Long getAddressId() {
			return addressId;
		}

		public void setAddressId(Long addressId) {
			this.addressId = addressId;
		}

		

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getFullName() {
			return fullName;
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}

		public String getMobileNumber() {
			return mobileNumber;
		}

		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}

		public String getHouseNumber() {
			return houseNumber;
		}

		public void setHouseNumber(String houseNumber) {
			this.houseNumber = houseNumber;
		}

		public String getStreetName() {
			return streetName;
		}

		public void setStreetName(String streetName) {
			this.streetName = streetName;
		}

		public String getLandmark() {
			return landmark;
		}

		public void setLandmark(String landmark) {
			this.landmark = landmark;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}

		public String getAddressType() {
			return addressType;
		}

		public void setAddressType(String addressType) {
			this.addressType = addressType;
		}

		public boolean isDefault() {
			return isDefault;
		}

		public void setDefault(boolean isDefault) {
			this.isDefault = isDefault;
		}

		@Override
		public String toString() {
			return "Address [addressId=" + addressId + ", fullName=" + fullName + ", mobileNumber=" + mobileNumber
					+ ", houseNumber=" + houseNumber + ", streetName=" + streetName + ", landmark=" + landmark
					+ ", city=" + city + ", state=" + state + ", country=" + country + ", postalCode=" + postalCode
					+ ", addressType=" + addressType + ", isDefault=" + isDefault + ", user=" + user + "]";
		}

	
	    
}
