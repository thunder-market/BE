package com.example.thundermarket.products.repository;

import com.example.thundermarket.products.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllIsDoneFalseByOrderByCreatedAtDesc();
    List<Product> findTop6ByIdNotAndIsDoneFalseOrderByCreatedAtDesc(Long id);
}
