package com.example.thundermarket.products.service;

import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.products.dto.ProductDetailResponseDto;
import com.example.thundermarket.products.dto.ProductListResponseDto;
import com.example.thundermarket.products.dto.ProductRequestDto;
import com.example.thundermarket.products.entity.Product;
import com.example.thundermarket.products.repository.ProductRepository;
import com.example.thundermarket.users.entity.User;
import com.example.thundermarket.users.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

//    상품 작성
    @Transactional
    public MessageResponseDto createProduct(ProductRequestDto productRequestDto, User user) {
        productRepository.saveAndFlush(new Product(productRequestDto, user));
        return new MessageResponseDto(HttpStatus.OK, "상품이 등록되었습니다.");
    }

//    전체 상품 조회
    @Transactional(readOnly = true)
    public List<ProductListResponseDto> getProductList() {
        List<ProductListResponseDto> productListResponseDtos = new ArrayList<>();
        List<Product> products = productRepository.findAllIsDoneFalseByOrderByCreatedAtDesc();
        for (Product product : products) {
            productListResponseDtos.add(new ProductListResponseDto(product));
        }
        return productListResponseDtos;
    }

//    선택 상품 조회
    @Transactional(readOnly = true)
    public ProductDetailResponseDto getProductDetailList(Long pdid, User user) {
        Product getproduct = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("게시물을 찾을 수 없습니다.")
        );
        boolean isAuth = false;
        if (user != null) {
            if (user.getRole() == UserRoleEnum.ADMIN || user.getId().equals(getproduct.getUser().getId())){
                isAuth = true;
            }
        }

//        최신 상품 리스트 6개 같이반환
        List<ProductListResponseDto> productListResponseDtos = productRepository
                .findTop6ByIdNotAndIsDoneFalseOrderByCreatedAtDesc(pdid).stream()
                .map(ProductListResponseDto::new)
                .collect(Collectors.toList());

        return new ProductDetailResponseDto(getproduct, productListResponseDtos, isAuth);
    }
}
