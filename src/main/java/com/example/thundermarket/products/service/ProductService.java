package com.example.thundermarket.products.service;

import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.products.dto.ProductReponseDto;
import com.example.thundermarket.products.dto.ProductRequestDto;
import com.example.thundermarket.products.entity.Product;
import com.example.thundermarket.products.repository.ProductRepository;
import com.example.thundermarket.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public MessageResponseDto createProdect(ProductRequestDto productRequestDto,
                                           User user) {
        productRepository.saveAndFlush(new Product(productRequestDto, user));
        return new MessageResponseDto(HttpStatus.OK, "succees");
    }
}
