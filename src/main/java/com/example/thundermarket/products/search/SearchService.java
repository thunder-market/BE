package com.example.thundermarket.products.search;

import com.example.thundermarket.products.dto.ProductListResponseDto;
import com.example.thundermarket.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<ProductListResponseDto> searchProducts(String keyword, int page, int size, String sortBy, boolean isAsc, int cateCode) {
        Sort.Direction direction = isAsc? Sort.Direction.ASC : Sort.Direction.DESC;
// 정확도순 구현은 중지.
//        if (sortBy.equals("accuracy")){
//            Sort sort = Sort.by(direction, "id");
//            Pageable pageable = PageRequest.of(page, size, sort);
//            return productRepository.findByTitleOrDescContainingAndCateCodeOrderByAccuracy(keyword, cateCode, pageable)
//                    .map(ProductListResponseDto::new);
//
//        }

        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findByTitleOrDescContainingAndCateCode(keyword, cateCode, pageable)
                .map(ProductListResponseDto::new);

    }
}
