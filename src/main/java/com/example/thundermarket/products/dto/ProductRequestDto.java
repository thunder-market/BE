package com.example.thundermarket.products.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class ProductRequestDto {

    // 제약사항 나중에 추가 예정

    @Size(min = 2, max = 40)
    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotNull(message = "카테고리를 입력하세요.")
    private int cateCode;

    @NotNull(message = "중고 유/무를 입력하세요.")
    private boolean used;

    @NotNull(message = "교환 가능 유/무를 입력하세요.")
    private boolean exchange;

    @NotNull(message = "가격을 입력하세요.")
    private int price;

    @NotNull(message = "배송비 포함 여부를 입력하세요.")
    private boolean deliveryFee;

    @Size(min = 10, max = 2000) // 추후 크기 조정
    @NotBlank(message = "내용을 입력하세요.")
    private String desc;
    
    private boolean isDone;

    @NotNull(message = "수량을 입력하세요.")
    private int quantity;

    @NotNull(message = "번개 페이 여부를 입력하세요.")
    private boolean thunderPay;
}
