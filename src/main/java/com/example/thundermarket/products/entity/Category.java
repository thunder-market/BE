package com.example.thundermarket.products.entity;

import com.example.thundermarket.products.dto.CategoryRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

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
    private String cateName;

    @Column(nullable = false)
    private int cateCode;

//    @OneToMany(mappedBy = "category")
//    private List<Product> productList = new ArrayList<>();


}
