package com.example.thundermarket.products.repository;

import com.example.thundermarket.products.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //    isDone이 false이고 createdAt을 기준으로 내림차순으로 정렬한 product를 모두 가져오는 쿼리를 직접 적용.
    @Query(value = "SELECT * FROM product WHERE is_done = false ORDER BY created_at DESC", nativeQuery = true)
    List<Product> findAllByIsDoneFalseOrderByCreatedAtDesc();

    //    같은 카테고리 게시물 최신순 6개를 불러온다. isDone이 false이고 주어진 productid값이 아닌 product를 limit개수만큼 가져오는 쿼리를 직접 적용.
    @Query(value = "SELECT * FROM product WHERE is_done = false AND id != :productId AND cate_code = (SELECT cate_code FROM product WHERE id = :productId) ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<Product> findLatestNotDoneProductsByCategoryIdExceptGivenId(@Param("limit") int limit, @Param("productId") Long productId);

    Page<Product> findAllByIsDoneFalse(Pageable page);

    @Query(value = "SELECT count(*) FROM product WHERE is_done = false", nativeQuery = true)
    Long countProducts();

//    제목과 내용에서 "keyword"가 포함된 상품을 검색하는 쿼리. cateCode가 0이면 카테고리와 상관없이 전체검색. cateCode가 들어오면 카테고리와 일치하는 상품만 검색
    @Query("SELECT p FROM Product p WHERE (p.title LIKE %:keyword% OR p.desc LIKE %:keyword%) AND (:cateCode = 0 OR p.cateCode = :cateCode)")
    Page<Product> findByTitleOrDescContainingAndCateCode(@Param("keyword") String keyword, @Param("cateCode") int cateCode, Pageable pageable);

//    정확도순 검색
//    @Query("SELECT p FROM Product p WHERE (p.title LIKE %:keyword% OR p.desc LIKE %:keyword%) AND (:cateCode = 0 OR p.cateCode = :cateCode) ORDER BY CASE WHEN p.title = :keyword OR p.desc = :keyword THEN 0 WHEN p.title LIKE :keyword% OR p.desc LIKE :keyword% THEN 1 WHEN p.title LIKE %:keyword% OR p.desc LIKE %:keyword% THEN 2 WHEN p.title LIKE %:keyword OR p.desc LIKE %:keyword THEN 3 END")
//    Page<Product> findByTitleOrDescContainingAndCateCodeOrderByAccuracy(@Param("keyword") String keyword, @Param("cateCode") int cateCode, Pageable pageable);

}
