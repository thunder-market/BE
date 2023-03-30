package com.example.thundermarket.products.s3;

import lombok.Getter;

@Getter
public class ImageResponseDto {

    private String imageId;

    public ImageResponseDto(String key) {
        this.imageId = key;
    }
}
// 현재 프로젝트에서 쓰지않는 클래스
