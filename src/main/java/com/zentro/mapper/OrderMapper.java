package com.zentro.mapper;

import java.util.List;
import com.zentro.dto.order.OrderResponseDto;
import com.zentro.model.Order;

public class OrderMapper {

    private OrderMapper() {
    }

    public static OrderResponseDto
    toResponse(Order order) {

        OrderResponseDto response =
                new OrderResponseDto();

        response.setOrderId(
                order.getOrderId()
        );

        response.setOrderStatus(
                order.getOrderStatus() != null ? order.getOrderStatus().name() : null
        );

        response.setPaymentStatus(
                order.getPaymentStatus() != null ? order.getPaymentStatus().name() : null
        );

        response.setOrderedAt(
                order.getOrderedAt()
        );

        // Example:
        // taking first order item
        // because your DTO design
        // currently supports
        // only one product

        if (!order.getOrderItems()
                  .isEmpty()) {

            var item =
                    order.getOrderItems()
                         .get(0);

            response.setProductId(
                    item.getProduct()
                        .getProductId()
            );

            response.setProductName(
                    item.getProduct()
                        .getProductName()
            );

            response.setQuantity(
                    item.getQuantity()
            );

            response.setPrice(
                    item.getUnitPrice()
            );

            response.setTotalPrice(
                    item.getTotalPrice()
            );
        }

        return response;
    }

    public static List<OrderResponseDto> toResponseList(Order order) {
        List<OrderResponseDto> responses = new java.util.ArrayList<>();
        if (order.getOrderItems() != null) {
            for (var item : order.getOrderItems()) {
                OrderResponseDto response = new OrderResponseDto();
                response.setOrderId(order.getOrderId());
                response.setOrderStatus(order.getOrderStatus() != null ? order.getOrderStatus().name() : null);
                response.setPaymentStatus(order.getPaymentStatus() != null ? order.getPaymentStatus().name() : null);
                response.setOrderedAt(order.getOrderedAt());

                response.setProductId(item.getProduct().getProductId());
                response.setProductName(item.getProduct().getProductName());
                response.setQuantity(item.getQuantity());
                response.setPrice(item.getUnitPrice());
                response.setTotalPrice(item.getTotalPrice());

                responses.add(response);
            }
        }
        return responses;
    }
    
    
    public class OrderItemRequestDto {

        private Long productId;

        private int quantity;

        public OrderItemRequestDto() {
        }

        public OrderItemRequestDto(
                Long productId,
                int quantity
        ) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(
                Long productId
        ) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(
                int quantity
        ) {
            this.quantity = quantity;
        }
    }
}
