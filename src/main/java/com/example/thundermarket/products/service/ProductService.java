package com.example.thundermarket.products.service;

import com.example.thundermarket.products.dto.*;
import com.example.thundermarket.products.entity.Product;
import com.example.thundermarket.products.repository.CategoryRepository;
import com.example.thundermarket.products.repository.ProductRepository;
import com.example.thundermarket.users.entity.User;
import com.example.thundermarket.users.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final S3Client s3Client;
    private final String bucketName;

    //    상품 작성
    @Transactional
    public MessageResponseDto createProduct(ProductRequestDto productRequestDto, User user, MultipartFile image) throws IOException {
        String key = UUID.randomUUID().toString(); // 또는 다른 고유한 키 생성 방법

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("uploaded-image/" + key)
                .contentType(image.getContentType())
                .contentLength(image.getSize())
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(image.getInputStream(), image.getSize()));

        productRepository.saveAndFlush(new Product(productRequestDto, user, key));
        return new MessageResponseDto(HttpStatus.OK, "상품이 등록되었습니다.");
    }

    //    전체 상품 조회
    @Transactional(readOnly = true)
    public List<ProductListResponseDto> getProductList() {
        List<ProductListResponseDto> productListResponseDtos = new ArrayList<>();
//        List<Product> products = productRepository.findAllByOrderByCreatedAtDesc();
        List<Product> products = productRepository.findAllByIsDoneFalseOrderByCreatedAtDesc();
        for (Product product : products) {
            productListResponseDtos.add(new ProductListResponseDto(product));
        }
        return productListResponseDtos;
    }

    //    선택 상품 상세 조회
    @Transactional(readOnly = true)
    public ProductDetailResponseDto getProductDetailList(Long pdid, User user) {
        Product getproduct = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("게시물을 찾을 수 없습니다.")
        );
        boolean isAuth = false;
        if (user != null) {
            if (user.getRole() == UserRoleEnum.ADMIN || user.getId().equals(getproduct.getUser().getId())) {
                isAuth = true;
            }
        }

//        최신 상품 리스트 6개 같이반환
        List<ProductListResponseDto> productListResponseDtos = productRepository
//                .findTop6ByIdNotOrderByCreatedAtDesc(pdid).stream()
                .findLatestNotDoneProductsByCategoryIdExceptGivenId(6, pdid).stream()
                .map(ProductListResponseDto::new)
                .collect(Collectors.toList());

        return new ProductDetailResponseDto(getproduct, productListResponseDtos, isAuth);
    }

//    상품 수정
    @Transactional
    public MessageResponseDto update(Long pdid, ProductRequestDto productRequestDto, User user, MultipartFile image) throws IOException {
        Product product = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        if (isMatchUser(product, user) || user.getRole() == UserRoleEnum.ADMIN) {
//           기존 이미지 삭제
            String img = product.getImg();
            String div = "uploaded-image";
            s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(div + "/" + img).build());
//            새 이미지 등록
            String key = UUID.randomUUID().toString(); // 또는 다른 고유한 키 생성 방법

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key("uploaded-image/" + key)
                    .contentType(image.getContentType())
                    .contentLength(image.getSize())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(image.getInputStream(), image.getSize()));
//          업데이트 메서드
            product.update(productRequestDto, key);
            return new MessageResponseDto(HttpStatus.OK, "게시글이 수정 되었습니다.");
        }
        throw new IllegalArgumentException("해당 권한이 없습니다");

    }

//    상품 삭제
    @Transactional
    public MessageResponseDto delete(Long pdid, User user) {

        Product product = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        if (isMatchUser(product, user) || user.getRole() == UserRoleEnum.ADMIN) {
            String img = product.getImg();
            String div = "uploaded-image";
            s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(div + "/" + img).build());
            productRepository.deleteById(pdid);
            return new MessageResponseDto(HttpStatus.OK, "게시글이 삭제 되었습니다.");
        }
        throw new IllegalArgumentException("해당 권한이 없습니다");
    }

//    구매 완료 메서드
    @Transactional
    public MessageResponseDto modifyDone(Long pdid, User user) {
        Product product = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
//        해당 상품이 판매중일때만
        if (!product.isDone()){
//            자신이 올린 상품이면 예외처리
            if (product.getUser().getId().equals(user.getId())){
                throw new IllegalArgumentException("자신이 올린 상품은 구매하실 수 없습니다.");
            }
            product.setDone(true);
            productRepository.save(product);
            return new MessageResponseDto(HttpStatus.OK, "상품을 정상적으로 구매하셨습니다.");
        }
        throw new IllegalArgumentException("이미 판매 완료된 상품입니다.");


    }

//    유저 검증 메서드
    private boolean isMatchUser(Product product, User user) {
        return product.getUser().getEmail().equals(user.getEmail());
    }


    @Transactional(readOnly = true)
    public Long getCountAllProducts(){

        return productRepository.countProducts();
    }

    @Transactional(readOnly = true)
    public List<ProductListResponseDto> getPageOfProduct(ReqProductPageableDto dto) {
        Page<Product> products = productRepository.findAll(configPageAble(dto));
        List<ProductListResponseDto> list = new ArrayList<>();
        for(Product product : products){
            if(product.isDone() == false){
                list.add(new ProductListResponseDto(product));
            }
        }
        return list;
    }

    private Pageable configPageAble(ReqProductPageableDto dto){

        Sort.Direction direction = dto.isAsc()? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction,dto.getSortBy());

        return PageRequest.of(dto.getPage()-1,dto.getSize(),sort);
    }

}
