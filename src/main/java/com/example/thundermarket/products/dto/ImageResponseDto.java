package com.example.thundermarket.products.dto;

import lombok.Getter;

@Getter
public class ImageResponseDto {

    private String imageid;

    public ImageResponseDto(String key) {
        this.imageid = key;
    }
}
