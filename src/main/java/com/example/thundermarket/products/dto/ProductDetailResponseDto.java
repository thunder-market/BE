package com.example.thundermarket.products.dto;

import com.example.thundermarket.products.entity.Product;
import lombok.Getter;

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

    public ProductDetailResponseDto(Product getproduct, List<ProductListResponseDto> productListResponseDtos) {
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
        for (int i = 0; i < 6; i++) {
            productList.add(productListResponseDtos.get(i));
        }
    }
}
