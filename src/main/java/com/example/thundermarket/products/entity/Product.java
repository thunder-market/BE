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

    @Column(nullable = false) // 이미지 임시
    private String img;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false) // 중고면 t 새상품은 f
    private boolean used;

    @Column(nullable = false) // 교환 가능 t
    private boolean exchange;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private boolean deliveryFee;

    @Column(nullable = false) // 상품 설명
    private String desc;

    @Column(nullable = false) // 판매완료면 t, 아니면 f
    private boolean isDone = false;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false) // 천둥페이 사용 여부 t
    private boolean thunderPay;

    @Column(nullable = false)
    private int category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Product(ProductRequestDto productRequestDto, User user) {
        this.img = productRequestDto.getImg();
        this.title = productRequestDto.getTitle();
        this.used = productRequestDto.isUsed();
        this.exchange = productRequestDto.isExchange();
        this.price = productRequestDto.getPrice();
        this.deliveryFee = productRequestDto.isDeliveryFee();
        this.desc = productRequestDto.getDesc();
        this.quantity = productRequestDto.getQuantity();
        this.user = user;
        this.thunderPay = productRequestDto.isThunderPay();
        this.isDone = productRequestDto.isDone();
        this.category = productRequestDto.getCateCode();
    }
}
