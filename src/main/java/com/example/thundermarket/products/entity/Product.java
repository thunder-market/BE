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

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private boolean exchange;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, length = 6000)
    private String desc;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "userid")
    @Column(nullable = false)
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
    }
}
