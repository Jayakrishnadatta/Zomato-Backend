package com.zentro.mapper;

import com.zentro.dto.orderItem.OrderItemResponse;
import com.zentro.model.OrderItems;

public class OrderItemMapper {

    private OrderItemMapper() {
    }

    public static OrderItemResponse
    toResponse(OrderItems orderItems) {

        OrderItemResponse response =
                new OrderItemResponse();

        response.setProductId(
                orderItems.getProduct()
                    .getProductId()
        );

        response.setProductName(
                orderItems.getProduct()
                    .getProductName()
        );

        response.setQuantity(
                orderItems.getQuantity()
        );

        response.setPrice(
                orderItems.getUnitPrice()
        );

        response.setTotalPrice(
                orderItems.getTotalPrice()
        );

        return response;
    }
}