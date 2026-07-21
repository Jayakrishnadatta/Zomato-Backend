package com.zentro.repository;

import com.zentro.dto.order.OrderResponseDto;
import com.zentro.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("select new com.zentro.dto.order.OrderResponseDto(o.orderId, i.product.productId, i.product.productName, i.quantity, i.unitPrice, i.totalPrice, o.orderStatus, o.paymentStatus, o.orderedAt) from Order o join o.orderItems i where o.orderId = :orderId")
    List<OrderResponseDto> findOrderResponsesByOrderId(@Param("orderId") Long orderId);

    @Query("select new com.zentro.dto.order.OrderResponseDto(o.orderId, i.product.productId, i.product.productName, i.quantity, i.unitPrice, i.totalPrice, o.orderStatus, o.paymentStatus, o.orderedAt) from Order o join o.orderItems i where o.user.Id = :userId")
    List<OrderResponseDto> findOrderResponsesByUserId(@Param("userId") Long userId);

    @Query("select new com.zentro.dto.order.OrderResponseDto(o.orderId, i.product.productId, i.product.productName, i.quantity, i.unitPrice, i.totalPrice, o.orderStatus, o.paymentStatus, o.orderedAt) from Order o join o.orderItems i")
    List<OrderResponseDto> findAllOrderResponses();
}
