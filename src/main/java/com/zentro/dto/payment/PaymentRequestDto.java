package com.zentro.dto.payment;

public class PaymentRequestDto {
	

    private Long orderId;

    private String paymentMethod;
    
    public PaymentRequestDto() {
		// TODO Auto-generated constructor stub
	}

	public PaymentRequestDto(Long orderId, String paymentMethod) {
		this.orderId = orderId;
		this.paymentMethod = paymentMethod;
	}

	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "PaymentRequestDto [orderId=" + orderId + ", paymentMethod=" + paymentMethod + "]";
	}
    
    

}
