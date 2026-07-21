package com.zentro.repository;

import com.zentro.model.CartItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends CrudRepository<CartItems, Long> {

    Optional<CartItems> findByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);
}
