package com.zentro.service;

import java.util.List;

import com.zentro.dto.order.OrderRequestDto;
import com.zentro.dto.order.OrderResponseDto;

public interface OrderService {

    List<OrderResponseDto> placeOrder(
            Long userId,
            OrderRequestDto dto
    );

    List<OrderResponseDto> getOrderById(
            Long orderId
    );

    List<OrderResponseDto>
    getOrdersByUserId(
            Long userId
    );

    List<OrderResponseDto>
    getAllOrders();

    void cancelOrder(Long orderId);

    void updateOrderStatus(
            Long orderId,
            String status
    );
}