package com.example.thundermarket.products.service;

import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.products.dto.ProductRequestDto;
import com.example.thundermarket.products.entity.Product;
import com.example.thundermarket.products.repository.ProductRepository;
import com.example.thundermarket.users.entity.User;
import com.example.thundermarket.users.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductSubService {

    private final ProductRepository productRepository;
    @Transactional
    public MessageResponseDto update(Long pdid, ProductRequestDto productRequestDto, User user) {
        Product product = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        if (isMatchUser(product, user) || user.getRole() == UserRoleEnum.ADMIN) {
            product.update(productRequestDto);

            return new MessageResponseDto(HttpStatus.OK, "게시글이 수정 되었습니다.");

        } else {

            throw new IllegalArgumentException("해당 권한이 없습니다");

        }
    }

    @Transactional
    public MessageResponseDto delete(Long pdid, User user){

        Product product = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        if (isMatchUser(product, user) || user.getRole() == UserRoleEnum.ADMIN) {
            productRepository.deleteById(pdid);

            return new MessageResponseDto(HttpStatus.OK, "게시글이 삭제 되었습니다.");

        } else {
            throw new IllegalArgumentException("해당 권한이 없습니다");
        }

    }

    private boolean isMatchUser(Product product, User user) {

        return product.getUser().getEmail().equals(user.getEmail());
    }
}
