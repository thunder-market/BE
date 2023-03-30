package com.example.thundermarket.products.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReqProductPageableDto {
    private final String sortBy;

    private final boolean isAsc;

    private final int size;

    private final int page;

}

