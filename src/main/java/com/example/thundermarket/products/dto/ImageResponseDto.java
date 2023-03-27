package com.example.thundermarket.products.dto;

import lombok.Getter;

@Getter
public class ImageResponseDto {

    private String imageId;

    public ImageResponseDto(String key) {
        this.imageId = key;
    }
}
