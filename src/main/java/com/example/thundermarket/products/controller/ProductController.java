package com.example.thundermarket.products.controller;

import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.products.dto.ProductRequestDto;
import com.example.thundermarket.products.service.ProductService;
import com.example.thundermarket.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

//    1. 상품 작성
    @PostMapping("")
    public MessageResponseDto createProduct(
            @RequestBody @Valid ProductRequestDto productRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.createProduct(productRequestDto, userDetails.getUser());
    }
}
