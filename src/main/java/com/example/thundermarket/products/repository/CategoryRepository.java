package com.example.thundermarket.products.repository;

import com.example.thundermarket.products.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
