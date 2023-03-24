package com.example.thundermarket.products.dto;

import com.example.thundermarket.products.entity.Product;
import lombok.Getter;

@Getter
public class ProductListResponseDto {

    private String img;
    private String title;
    private int price;
    private boolean isDone;
    private boolean thunderPay;

    public ProductListResponseDto(Product product) {
        this.img = product.getImg();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.isDone = product.isDone();
        this.thunderPay = product.isThunderPay();
    }
}
