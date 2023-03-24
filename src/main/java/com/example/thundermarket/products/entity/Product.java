package com.example.thundermarket.products.entity;

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

    @Column(nullable = false)
    private Long userid;
}
