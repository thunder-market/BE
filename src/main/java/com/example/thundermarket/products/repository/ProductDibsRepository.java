package com.example.thundermarket.products.repository;

import com.example.thundermarket.products.entity.ProductDibs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductDibsRepository extends JpaRepository<ProductDibs, Long> {
    Optional<ProductDibs> findByUserIdAndProductId(Long userId, Long productId);

    Optional<ProductDibs> deleteByUserIdAndProductId(Long userId, Long ProductId);
}
