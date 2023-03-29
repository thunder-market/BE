package com.example.thundermarket.products.controller;

import com.example.thundermarket.products.dto.*;
import com.example.thundermarket.products.service.ProductService;
import com.example.thundermarket.security.UserDetailsImpl;
import com.example.thundermarket.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
    @GetMapping
    public List<ProductListResponseDto> getProductList() {
        return productService.getProductList();
    }

    // 3. 상품 상세 조회
    @GetMapping("/{pdid}")
    public ProductDetailResponseDto getProductDetailList(
            @PathVariable Long pdid, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = null;
        if (userDetails != null) user = userDetails.getUser();

        return productService.getProductDetailList(pdid, user);
    }

//   4. 상품 수정
    @PostMapping(value = "/{pdid}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public MessageResponseDto update(@PathVariable("pdid") Long pdid,
                                     @RequestParam("image") MultipartFile image,
                                     @RequestPart("dto") @Valid ProductRequestDto productRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        // 수정하려는 이미지가 없으면 text만 수정하는 로직으로 이동
        if (image == null || image.isEmpty()) return productService.textUpdate(pdid, productRequestDto, userDetails.getUser());
        // 수정하려는 이미지가 있으면 이미지와 text를 같이 수정하는 로직으로 이동
        validateImage(image);
        return productService.update(pdid, productRequestDto, userDetails.getUser(), image);
    }

//    5. 상품 삭제
    @DeleteMapping("/{pdid}")
    public MessageResponseDto delete(@PathVariable Long pdid,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.delete(pdid, userDetails.getUser());
    }

    //   6. 상품 구매 완료.
    @PatchMapping("/{pdid}/done")
    public MessageResponseDto modifyDone(@PathVariable("pdid") Long pdid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.modifyDone(pdid, userDetails.getUser());
    }

//    페이지 조회 (추후 추가기능으로 적용 예정)
    @GetMapping("/pages")
    public List<ProductListResponseDto> getPagingProducts(ReqProductPageableDto dto, HttpServletResponse resp) {
        Long count = productService.getCountAllProducts();
        resp.addHeader("Total_Count_Products", String.valueOf(count));
        return productService.getPageOfProduct(dto);

    }

//    이미지 유효성 검사
    private void validateImage(MultipartFile image) {
        if (image.isEmpty()) throw new IllegalStateException("상품의 이미지를 업로드해주세요.");
        if (image.getSize() > MAX_FILE_SIZE) throw new IllegalStateException("파일 사이즈가 최대 사이즈(5MB)를 초과합니다.");
        if (!ALLOWED_IMAGE_CONTENT_TYPES.contains(image.getContentType())) throw new IllegalStateException("파일 형식은 JPEG, JPG, PNG, GIF 중 하나여야 합니다.");
    }
}
