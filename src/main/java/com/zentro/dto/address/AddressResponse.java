package com.zentro.dto.address;

public class AddressResponse {

	private Long addressId;

	private String fullName;

	private String mobileNumber;

	private String houseNumber;

	private String streetName;

	private String city;

	private String state;

	private String country;

	private String postalCode;

	

	public AddressResponse(Long addressId, String fullName, String mobileNumber, String houseNumber, String streetName,
			String city, String state, String country, String postalCode) {
		super();
		this.addressId = addressId;
		this.fullName = fullName;
		this.mobileNumber = mobileNumber;
		this.houseNumber = houseNumber;
		this.streetName = streetName;
		this.city = city;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
	}

	public AddressResponse() {
		// TODO Auto-generated constructor stub
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
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

	@Override
	public String toString() {
		return "AddressResponse [addressId=" + addressId + ", fullName=" + fullName + ", mobileNumber=" + mobileNumber
				+ ", houseNumber=" + houseNumber + ", streetName=" + streetName + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", postalCode=" + postalCode + "]";
	}
	
	
}
