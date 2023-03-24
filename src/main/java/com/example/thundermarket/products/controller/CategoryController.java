package com.example.thundermarket.products.controller;

import com.example.thundermarket.products.dto.CategoryRequestDto;
import com.example.thundermarket.products.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
//    일단 테스트용으로 만든 컨트롤러

    private final CategoryService categoryService;

    @PostMapping("")
    public String createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.createCategory(categoryRequestDto);
    }

}
