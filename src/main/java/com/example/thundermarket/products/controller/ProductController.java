package com.example.thundermarket.products.controller;

import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.products.dto.ProductDetailResponseDto;
import com.example.thundermarket.products.dto.ProductListResponseDto;
import com.example.thundermarket.products.dto.ProductRequestDto;
import com.example.thundermarket.products.service.ProductService;
import com.example.thundermarket.security.UserDetailsImpl;
import com.example.thundermarket.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // 1. 상품 작성
    @PostMapping
    public MessageResponseDto createProduct(
            @RequestBody @Valid ProductRequestDto productRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.createProduct(productRequestDto, userDetails.getUser());
    }

    // 2. 상품 전체 조회
    @GetMapping
    public List<ProductListResponseDto> getProductList() {
        return productService.getProductList();
    }

    // 3. 상품 상세 조회
    @GetMapping("/{pdid}")
    public ProductDetailResponseDto getProductDetailList(
            @PathVariable Long pdid, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = null;
        if (userDetails != null){
            user = userDetails.getUser();
        }

        return productService.getProductDetailList(pdid, user);
    }

    @PatchMapping("/{pdid}")
    public MessageResponseDto update(@PathVariable Long pdid,
                                     @RequestBody ProductRequestDto productRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return productService.update(pdid, productRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/{pdid}")
    public MessageResponseDto delete(@PathVariable Long pdid,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return productService.delete(pdid, userDetails.getUser());
    }

    @PostMapping("/{pdid}/dibs")
    public MessageResponseDto dibs(@PathVariable Long pdid,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.dibs(pdid, userDetails.getUser());
    }

}
