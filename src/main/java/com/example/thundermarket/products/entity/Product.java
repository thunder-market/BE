package com.example.thundermarket.products.entity;

import com.example.thundermarket.products.dto.ProductRequestDto;
import com.example.thundermarket.users.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Size;


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

    @Column(nullable = false, length = 2000) // 상품 설명, 최대 2000자
    private String desc;

    @Column(nullable = false) // 판매완료면 t, 아니면 f
    private boolean isDone = false;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false) // 천둥페이 사용 여부 t
    private boolean thunderPay;

    @Column(nullable = false)
    private int cateCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany
    @JoinColumn(name = "productId")
    List<ProductDibs> productDibsList = new ArrayList<>();

    public Product(ProductRequestDto productRequestDto, User user, String key) {
        this.img = key;
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
        this.cateCode = productRequestDto.getCateCode();
    }

    public void update(ProductRequestDto productRequestDto, String key) {
        this.img = key;
        this.title = productRequestDto.getTitle();
        this.used = productRequestDto.isUsed();
        this.exchange = productRequestDto.isExchange();
        this.price = productRequestDto.getPrice();
        this.deliveryFee = productRequestDto.isDeliveryFee();
        this.desc = productRequestDto.getDesc();
        this.quantity = productRequestDto.getQuantity();
        this.thunderPay = productRequestDto.isThunderPay();
        this.isDone = productRequestDto.isDone();
        this.cateCode = productRequestDto.getCateCode();
    }

    public void textUpdate(ProductRequestDto productRequestDto) {
        this.title = productRequestDto.getTitle();
        this.used = productRequestDto.isUsed();
        this.exchange = productRequestDto.isExchange();
        this.price = productRequestDto.getPrice();
        this.deliveryFee = productRequestDto.isDeliveryFee();
        this.desc = productRequestDto.getDesc();
        this.quantity = productRequestDto.getQuantity();
        this.thunderPay = productRequestDto.isThunderPay();
        this.isDone = productRequestDto.isDone();
        this.cateCode = productRequestDto.getCateCode();
    }

    public void setDone(boolean done) {
        isDone = done;
    }

}
