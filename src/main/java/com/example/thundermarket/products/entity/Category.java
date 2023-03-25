package com.example.thundermarket.products.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int cateCode;

    @Column(nullable = false)
    private String cateName;

    @OneToMany(mappedBy = "cateCode")
    private List<Product> productList = new ArrayList<>();

}
