package com.example.thundermarket.products.controller;

import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.products.dto.ProductRequestDto;
import com.example.thundermarket.products.service.ProductSubService;
import com.example.thundermarket.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductSubController {
    private final ProductSubService productSubService;

    @PatchMapping("/{pdid}")
    public MessageResponseDto update(@PathVariable Long pdid,
                                     @RequestBody ProductRequestDto productRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return productSubService.update(pdid, productRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/{pdid}")
    public MessageResponseDto delete(@PathVariable Long pdid,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return productSubService.delete(pdid, userDetails.getUser());
    }
}
