package com.example.thundermarket.products.repository;

import com.example.thundermarket.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
