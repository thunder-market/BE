package com.example.thundermarket.products.dto;

import lombok.Getter;

@Getter
public class ProductRequestDto {

    private String img;
    private String title;
    private int cateCode;
    private boolean used;
    private boolean exchange;
    private int price;
    private boolean deliveryFee;
    private String desc;
    private boolean isDone;
    private int quantity;
    private boolean thunderPay;
}
