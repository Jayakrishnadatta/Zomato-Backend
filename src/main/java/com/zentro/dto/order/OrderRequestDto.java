package com.zentro.dto.order;

public class OrderRequestDto {

    private Long addressId;

    private String paymentMethod;

    public OrderRequestDto() {
    }

    public OrderRequestDto(
            Long addressId,
            String paymentMethod
    ) {
        this.addressId = addressId;
        this.paymentMethod = paymentMethod;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(
            String paymentMethod
    ) {
        this.paymentMethod = paymentMethod;
    }
}
