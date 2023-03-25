package com.example.thundermarket.products.service;

import com.example.thundermarket.products.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    //   일단 테스트용으로 만든 서비스

    private final CategoryRepository categoryRepository;


}
