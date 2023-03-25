package com.example.thundermarket.products.service;

import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.products.dto.ProductDetailResponseDto;
import com.example.thundermarket.products.dto.ProductListResponseDto;
import com.example.thundermarket.products.dto.ProductRequestDto;
import com.example.thundermarket.products.entity.Product;
import com.example.thundermarket.products.repository.ProductRepository;
import com.example.thundermarket.security.UserDetailsImpl;
import com.example.thundermarket.users.entity.User;
import com.example.thundermarket.users.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public MessageResponseDto createProduct(ProductRequestDto productRequestDto, User user) {
        productRepository.saveAndFlush(new Product(productRequestDto, user));
        return new MessageResponseDto(HttpStatus.OK, "상품이 등록되었습니다.");
    }

    @Transactional(readOnly = true)
    public List<ProductListResponseDto> getProductList() {
        List<ProductListResponseDto> productListResponseDtos = new ArrayList<>();
        List<Product> products = productRepository.findAllByOrderByCreatedAtDesc();
        for (Product product : products) {
            productListResponseDtos.add(new ProductListResponseDto(product));
        }
        return productListResponseDtos;
    }

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

        List<ProductListResponseDto> productListResponseDtos = new ArrayList<>();
        List<Product> products = productRepository.findByIsDoneFalseOrderByCreatedAtDesc();
        for (Product product : products) {
            productListResponseDtos.add(new ProductListResponseDto(product));
        }

        return new ProductDetailResponseDto(getproduct, productListResponseDtos, isAuth);
    }
}
