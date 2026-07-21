package com.zentro.model;

public enum OrderStatus {
    PENDING("Pending - Order awaiting payment"),
    PLACED("Placed - Order has been placed"),
    CONFIRMED("Confirmed - Order is confirmed"),
    SHIPPED("Shipped - Order is on the way"),
    DELIVERED("Delivered - Order has been delivered"),
    CANCELLED("Cancelled - Order has been cancelled"),
    FAILED("Failed - Order creation failed"),
    RETURNED("Returned - Order has been returned");

    private final String description;

    OrderStatus(String description) {
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
