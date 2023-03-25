package com.example.thundermarket.products.dto;

import com.example.thundermarket.products.entity.Product;
import com.example.thundermarket.util.TimeInteval;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
public class ProductListResponseDto {

    private Long id;
    private String img;
    private String title;
    private int price;
    private boolean isDone;
    private boolean thunderPay;
    private String timeInterval;

    public ProductListResponseDto(Product product) {
        this.id = product.getId();
        this.img = product.getImg();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.isDone = product.isDone();
        this.thunderPay = product.isThunderPay();
        this.timeInterval = TimeInteval.Calculate(product.getCreatedAt());
    }
}
