package com.zentro.serviceImple;

import com.zentro.dto.order.OrderRequestDto;
import com.zentro.dto.order.OrderResponseDto;
import com.zentro.mapper.OrderMapper;
import com.zentro.model.Cart;
import com.zentro.model.CartItems;
import com.zentro.model.Order;
import com.zentro.model.OrderItems;
import com.zentro.model.OrderStatus;
import com.zentro.model.PaymentStatus;
import com.zentro.model.Product;
import com.zentro.model.User;
import com.zentro.repository.AddressRepository;
import com.zentro.repository.CartItemsRepository;
import com.zentro.repository.CartRepository;
import com.zentro.repository.OrderRepository;
import com.zentro.repository.ProductRepository;
import com.zentro.repository.UserRepository;
import com.zentro.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImple implements OrderService {

    private static final double SHIPPING_CHARGE = 0;
    private static final double TAX_RATE = 0;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;

    public OrderServiceImple(
            OrderRepository orderRepository,
            UserRepository userRepository,
            CartRepository cartRepository,
            CartItemsRepository cartItemsRepository,
            ProductRepository productRepository,
            AddressRepository addressRepository
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<OrderResponseDto> placeOrder(Long userId, OrderRequestDto dto) {
        validateUserId(userId);
        validateOrderRequest(dto);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        validateAddressBelongsToUser(dto.getAddressId(), userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderedAt(LocalDateTime.now());
        order.setOrderTrackingNumber(UUID.randomUUID().toString());

        List<OrderItems> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (CartItems cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            if (product.getAvailableQuantity() < cartItem.getQuantity()) {
                throw new IllegalArgumentException("Requested quantity is not available");
            }

            double unitPrice = getProductPrice(product);
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(unitPrice);
            orderItem.setTotalPrice(unitPrice * cartItem.getQuantity());
            orderItems.add(orderItem);

            product.setAvailableQuantity(product.getAvailableQuantity() - cartItem.getQuantity());
            product.setSoldQuantity(product.getSoldQuantity() + cartItem.getQuantity());
            productRepository.save(product);

            totalAmount += orderItem.getTotalPrice();
        }

        double taxAmount = totalAmount * TAX_RATE;
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setTaxAmount(taxAmount);
        order.setShippingCharge(SHIPPING_CHARGE);
        order.setDiscountAmount(0);
        order.setFinalAmount(totalAmount + taxAmount + SHIPPING_CHARGE);

        Order savedOrder = orderRepository.save(order);
        clearCart(cart);

        return OrderMapper.toResponseList(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrderById(Long orderId) {
        validateOrderId(orderId);
        List<OrderResponseDto> response = orderRepository.findOrderResponsesByOrderId(orderId);
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("Order not found");
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        validateUserId(userId);
        return orderRepository.findOrderResponsesByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAllOrderResponses();
    }

    @Override
    public void cancelOrder(Long orderId) {
        validateOrderId(orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        validateOrderId(orderId);
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Order status is required");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            order.setOrderStatus(orderStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
        orderRepository.save(order);
    }

    private double getProductPrice(Product product) {
        if (product.getDiscountPrice() > 0) {
            return product.getDiscountPrice();
        }

        return product.getOriginalPrice();
    }

    private void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User is required");
        }
    }

    private void validateOrderId(Long orderId) {
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("Order is required");
        }
    }

    private void validateOrderRequest(OrderRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Order data is required");
        }
        if (dto.getAddressId() == null || dto.getAddressId() <= 0) {
            throw new IllegalArgumentException("Address is required");
        }
        if (dto.getPaymentMethod() == null || dto.getPaymentMethod().isBlank()) {
            throw new IllegalArgumentException("Payment method is required");
        }
    }

    private void validateAddressBelongsToUser(Long addressId, Long userId) {
        var address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        if (address.getUser() == null || !userId.equals(address.getUser().getId())) {
            throw new IllegalArgumentException("Address does not belong to user");
        }
    }

    private void clearCart(Cart cart) {
        for (CartItems cartItem : new ArrayList<>(cart.getCartItems())) {
            cart.getCartItems().remove(cartItem);
            cartItemsRepository.delete(cartItem);
        }
        cart.setTotalItems(0);
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }
}
