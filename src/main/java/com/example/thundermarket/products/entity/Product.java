package com.example.thundermarket.products.entity;

import com.example.thundermarket.products.dto.ProductRequestDto;
import com.example.thundermarket.users.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Product extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    이미지 임시
    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private String title;

//    중고면 t 새상품은 f
    @Column(nullable = false)
    private boolean used;

//    교환 가능 t
    @Column(nullable = false)
    private boolean exchange;

    @Column(nullable = false)
    private int price;

//    상품 설명
    @Column(nullable = false)
    private String desc;

    @Column(nullable = false)
    private int quantity;

//    천둥페이 사용 여부 t
    @Column(nullable = false)
    private boolean thunderPay;

//    판매완료면 t, 아니면 f
    @Column(nullable = false)
    private boolean isDone = false;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public Product(ProductRequestDto productRequestDto, User user) {
        this.img = productRequestDto.getImg();
        this.title = productRequestDto.getTitle();
        this.used = productRequestDto.isUsed();
        this.exchange = productRequestDto.isExchange();
        this.price = productRequestDto.getPrice();
        this.desc = productRequestDto.getDesc();
        this.quantity = productRequestDto.getQuantity();
        this.user = user;
        this.thunderPay = productRequestDto.isThunderPay();
        this.isDone = productRequestDto.isDone();
    }
}
