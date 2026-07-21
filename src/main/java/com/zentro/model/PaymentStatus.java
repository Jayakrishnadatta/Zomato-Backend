package com.zentro.model;

public enum PaymentStatus {
    PENDING("Pending - Awaiting payment confirmation"),
    SUCCESS("Success - Payment completed successfully"),
    FAILED("Failed - Payment failed"),
    PAID("Paid - Payment has been received"),
    REFUNDED("Refunded - Payment has been refunded"),
    CANCELLED("Cancelled - Payment was cancelled");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
