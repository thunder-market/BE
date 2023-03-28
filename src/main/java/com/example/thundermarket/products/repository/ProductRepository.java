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

    //    isDone이 false이고 주어진 productid값이 아닌 product를 limit개수만큼 가져오는 쿼리를 직접 적용.
    @Query(value = "SELECT * FROM product WHERE is_done = false AND id != :productId  ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<Product> findLatestNotDoneProductsExceptGivenId(@Param("limit") int limit, @Param("productId") Long productId);

    //    같은 카테고리 게시물 최신순 6개를 불러온다. isDone이 false이고 주어진 productid값이 아닌 product를 limit개수만큼 가져오는 쿼리를 직접 적용.
    @Query(value = "SELECT * FROM product WHERE is_done = false AND id != :productId AND cate_code = (SELECT cate_code FROM product WHERE id = :productId) ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<Product> findLatestNotDoneProductsByCategoryIdExceptGivenId(@Param("limit") int limit, @Param("productId") Long productId);
    //@Query(value = "SELECT * FROM product WHERE is_done = false order by id  \n — #pageable\n",countQuery = "SELECT count(*) FROM product where is_done = false", nativeQuery = true)
    Page<Product> findAllByIsDoneFalse(Pageable page);
    @Query(value = "SELECT count(*) FROM product WHERE is_done = false", nativeQuery = true)
    Long countProducts();

    //    아래는 isDone 상관없이 간단하게 jpa 쓸때 코드
//    List<Product> findAllByOrderByCreatedAtDesc();

    //    아래는 isDone 상관없이 간단하게 jpa 쓸때 코드
//    List<Product> findTop6ByIdNotOrderByCreatedAtDesc(Long id);

}
