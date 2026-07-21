package com.zentro.serviceImple;

import com.zentro.dto.cart.CartResponse;
import com.zentro.dto.cartItem.AddToCartDto;
import com.zentro.mapper.CartMapper;
import com.zentro.model.Cart;
import com.zentro.model.CartItems;
import com.zentro.model.Product;
import com.zentro.model.User;
import com.zentro.repository.CartItemsRepository;
import com.zentro.repository.CartRepository;
import com.zentro.repository.ProductRepository;
import com.zentro.repository.UserRepository;
import com.zentro.service.CartServlet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartServiceImple implements CartServlet {

    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImple(
            CartRepository cartRepository,
            CartItemsRepository cartItemsRepository,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartResponse addToCart(Long userId, AddToCartDto dto) {
        validateUserId(userId);
        if (dto == null || dto.getProductId() == null) {
            throw new IllegalArgumentException("Product is required");
        }
        if (dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Product product = getProduct(dto.getProductId());
        validateStock(product, dto.getQuantity());

        Cart cart = getOrCreateCart(userId);
        CartItems cartItem = findCartItem(cart, dto.getProductId());

        if (cartItem == null) {
            cartItem = new CartItems();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setItemPrice(getProductPrice(product));
            cart.getCartItems().add(cartItem);
        } else {
            validateStock(product, cartItem.getQuantity() + dto.getQuantity());
        }

        cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
        updateItemTotal(cartItem);
        recalculateCart(cart);

        return CartMapper.toResponse(cartRepository.save(cart));
    }

    @Override
    public CartResponse updateCartItemQuantity(Long userId, Long productId, int quantity) {
        if (quantity <= 0) {
            return removeFromCart(userId, productId);
        }

        Cart cart = getExistingCart(userId);
        CartItems cartItem = getExistingCartItem(cart, productId);
        Product product = cartItem.getProduct();

        validateStock(product, quantity);
        cartItem.setQuantity(quantity);
        cartItem.setItemPrice(getProductPrice(product));
        updateItemTotal(cartItem);
        recalculateCart(cart);

        return CartMapper.toResponse(cartRepository.save(cart));
    }

    @Override
    public CartResponse removeFromCart(Long userId, Long productId) {
        Cart cart = getExistingCart(userId);
        CartItems cartItem = getExistingCartItem(cart, productId);

        cart.getCartItems().remove(cartItem);
        cartItemsRepository.delete(cartItem);
        recalculateCart(cart);

        return CartMapper.toResponse(cartRepository.save(cart));
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCartByUserId(Long userId) {
        return CartMapper.toResponse(getExistingCart(userId));
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getExistingCart(userId);

        for (CartItems item : new ArrayList<>(cart.getCartItems())) {
            cart.getCartItems().remove(item);
            cartItemsRepository.delete(item);
        }

        recalculateCart(cart);
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));

                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setCreatedAt(LocalDateTime.now());
                    cart.setCartItems(new ArrayList<>());
                    return cart;
                });
    }

    private Cart getExistingCart(Long userId) {
        validateUserId(userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        ensureCartItems(cart);
        return cart;
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    private CartItems findCartItem(Cart cart, Long productId) {
        ensureCartItems(cart);

        if (cart.getCartId() != null) {
            return cartItemsRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId)
                    .orElse(null);
        }

        return cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct() != null && productId.equals(item.getProduct().getProductId()))
                .findFirst()
                .orElse(null);
    }

    private CartItems getExistingCartItem(Cart cart, Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product is required");
        }

        CartItems cartItem = findCartItem(cart, productId);
        if (cartItem == null) {
            throw new IllegalArgumentException("Product is not available in cart");
        }

        return cartItem;
    }

    private void recalculateCart(Cart cart) {
        ensureCartItems(cart);

        int totalItems = 0;
        double totalPrice = 0;

        for (CartItems item : cart.getCartItems()) {
            updateItemTotal(item);
            totalItems += item.getQuantity();
            totalPrice += item.getTotalPrice();
        }

        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);
    }

    private void updateItemTotal(CartItems item) {
        item.setTotalPrice(item.getItemPrice() * item.getQuantity());
    }

    private double getProductPrice(Product product) {
        if (product.getDiscountPrice() > 0) {
            return product.getDiscountPrice();
        }

        return product.getOriginalPrice();
    }

    private void validateStock(Product product, int quantity) {
        if (quantity > product.getAvailableQuantity()) {
            throw new IllegalArgumentException("Requested quantity is not available");
        }
    }

    private void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User is required");
        }
    }

    private void ensureCartItems(Cart cart) {
        List<CartItems> items = cart.getCartItems();
        if (items == null) {
            cart.setCartItems(new ArrayList<>());
        }
    }
}
