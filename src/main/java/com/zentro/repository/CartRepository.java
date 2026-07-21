package com.zentro.repository;

import com.zentro.model.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

    @Query("select c from Cart c where c.user.Id = :userId")
    Optional<Cart> findByUserId(@Param("userId") Long userId);

}
