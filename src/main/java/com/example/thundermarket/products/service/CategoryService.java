package com.example.thundermarket.products.service;

import com.example.thundermarket.products.dto.CategoryRequestDto;
import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.products.entity.Category;
import com.example.thundermarket.products.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    //    일단 테스트용으로 만든 서비스

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(CategoryRequestDto categoryRequestDto) {
        return categoryRepository.save(new Category(categoryRequestDto));
    }
}
