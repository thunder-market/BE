package com.example.thundermarket.products.dto;

import com.example.thundermarket.products.entity.Product;
import com.example.thundermarket.util.TimeInteval;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductDetailResponseDto {

    private Long id;
    private String img;
    private String title;
    private int cateCode;
    private boolean used;
    private boolean exchange;
    private boolean deliveryFee;
    private String desc;
    private int price;
    private boolean isDone;
    private boolean thunderPay;
    private int quantity;
    private List<ProductListResponseDto> productList = new ArrayList<>();
    private boolean isAuth = false;
    private String timeInterval;

    public ProductDetailResponseDto(Product getproduct, List<ProductListResponseDto> productListResponseDtos, boolean isAuth) {
        this.id = getproduct.getId();
        this.img = getproduct.getImg();
        this.title = getproduct.getTitle();
        this.cateCode = getproduct.getCategory();
        this.used = getproduct.isUsed();
        this.exchange = getproduct.isExchange();
        this.deliveryFee = getproduct.isDeliveryFee();
        this.desc = getproduct.getDesc();
        this.price = getproduct.getPrice();
        this.isDone = getproduct.isDone();
        this.thunderPay = getproduct.isThunderPay();
        this.quantity = getproduct.getQuantity();
        this.productList = productListResponseDtos;
        this.isAuth = isAuth;
        this.timeInterval = TimeInteval.Calculate(getproduct.getCreatedAt());
    }
}
