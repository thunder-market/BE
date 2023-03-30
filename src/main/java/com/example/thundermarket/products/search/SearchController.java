package com.example.thundermarket.products.search;

import com.example.thundermarket.products.dto.ProductListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/search")
public class SearchController {

    private final SearchService searchService;
/*
상품 검색 기능 구현 (백엔드에서만 구현)
keyword에 대해 검색하면 product 테이블의 title과 desc에서 keyword를 검색해 결과를 Page<ProductListResponseDto>로 반환
프론트에서는 page에 현재 페이지, size에 한 페이지에 보여줄 상품 수, sortBy에 게시글 정렬 기준,
isAsc에 오름차순이면 true / 내림차순이면 false, cateCode에 카테고리 코드를 보내줘야 한다.
현재 번개장터에 구현되어있는 검색은 전체검색/카테고리검색 아래에  최신순 / 저가순 / 고가순 / 정확도순이 구현되어 있다.
카테고리와 상관없이 검색하려면 cateCode에 0을 보내주고, 특정 카테고리 내의 상품만 검색하려면 그에 맞는 cateCode를 보내주면 된다.
최신순은 sortBy에 id(혹은 createdAt), isAsc에 false를 담아 보내주면 된다.
저가순은 sordBy에 price, isAsc에 true를 담아 보내주면 된다.
고가순은 sortBy에 price, isAsc에 false를 담아 보내주면 된다.
정확도순 검색은 일단 구현 중지.
 */

    @GetMapping
    public Page<ProductListResponseDto> searchProducts(@RequestParam(value = "keyword") String keyword,
                                                       @RequestParam(value = "page") int page,
                                                       @RequestParam(value = "size") int size,
                                                       @RequestParam(value = "sortBy") String sortBy,
                                                       @RequestParam(value = "isAsc") boolean isAsc,
                                                       @RequestParam(value = "cateCode") int cateCode) {
        if (keyword.isBlank()) throw new IllegalArgumentException("검색어를 입력해 주세요.");
        return searchService.searchProducts(keyword, page-1, size, sortBy, isAsc, cateCode);
    }
}

