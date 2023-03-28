package com.example.thundermarket.products.controller;

import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.products.dto.ProductDetailResponseDto;
import com.example.thundermarket.products.dto.ProductListResponseDto;
import com.example.thundermarket.products.dto.ProductRequestDto;
import com.example.thundermarket.products.service.ProductService;
import com.example.thundermarket.products.service.S3ImageService;
import com.example.thundermarket.security.UserDetailsImpl;
import com.example.thundermarket.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_IMAGE_CONTENT_TYPES = List.of("image/jpeg", "image/jpg", "image/png", "image/gif");


    // 1. 상품 작성
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public MessageResponseDto createProduct(
            @RequestParam("image") MultipartFile image,
            @RequestPart("dto") @Valid ProductRequestDto productRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        validateImage(image);
        return productService.createProduct(productRequestDto, userDetails.getUser(), image);
    }

    // 2. 상품 전체 조회
    @GetMapping("")
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

//    상품 수정
    @PostMapping(value = "/{pdid}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public MessageResponseDto update(@PathVariable("pdid") Long pdid,
                                     @RequestParam("image") MultipartFile image,
                                     @RequestPart("dto") @Valid ProductRequestDto productRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        validateImage(image);
        return productService.update(pdid, productRequestDto, userDetails.getUser(), image);
    }

//    상품 삭제
    @DeleteMapping("/{pdid}")
    public MessageResponseDto delete(@PathVariable Long pdid,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.delete(pdid, userDetails.getUser());
    }


//    이미지 유효성 검사
    private void validateImage(MultipartFile image) {
        if (image.getSize() > MAX_FILE_SIZE) {
            throw new IllegalStateException("파일 사이즈가 최대 사이즈(5MB)를 초과합니다.");
        }

        if (!ALLOWED_IMAGE_CONTENT_TYPES.contains(image.getContentType())) {
            throw new IllegalStateException("파일 형식은 JPEG, JPG, PNG, GIF 중 하나여야 합니다.");
        }
    }
}
