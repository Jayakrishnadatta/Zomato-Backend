package com.zentro.repository;

import com.zentro.dto.wishlist.WishlistResponseDto;
import com.zentro.model.Whishlist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends CrudRepository<Whishlist, Long> {

    @Query("select new com.zentro.dto.wishlist.WishlistResponseDto(w.wishlistId, w.product.productId, w.product.productName, w.product.discountPrice, w.product.imageUrl) from Whishlist w where w.user.Id = :userId")
    List<WishlistResponseDto> findWishlistByUserId(@Param("userId") Long userId);

    Optional<Whishlist> findByUser_IdAndProduct_ProductId(Long userId, Long productId);
}
