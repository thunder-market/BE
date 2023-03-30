package com.example.thundermarket.products.service;

import com.example.thundermarket.products.dto.*;
import com.example.thundermarket.products.entity.Product;

import com.example.thundermarket.products.entity.ProductDibs;
import com.example.thundermarket.products.repository.ProductDibsRepository;
import com.example.thundermarket.products.repository.ProductRepository;
import com.example.thundermarket.users.entity.User;
import com.example.thundermarket.users.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDibsRepository productDibsRepository;

    private final S3Client s3Client;
    private final String bucketName;


    // S3 이미지 업로드 메서드
    private String S3ImageUpload(MultipartFile image) throws IOException {
        String key = UUID.randomUUID() + "_" + image.getOriginalFilename(); // 또는 다른 고유한 키 생성 방법

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("uploaded-image/" + key)
                .contentType(image.getContentType())
                .contentLength(image.getSize())
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(image.getInputStream(), image.getSize()));
        return key;
    }

    // S3 이미지 삭제 메서드
    private void S3ImageDelete(String img) {
        String div = "uploaded-image";
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(div + "/" + img).build());

    }


    // 상품 작성
    @Transactional
    public MessageResponseDto createProduct(ProductRequestDto productRequestDto, User user, MultipartFile image) throws IOException {

        String key = S3ImageUpload(image);

        productRepository.saveAndFlush(new Product(productRequestDto, user, key));
        return new MessageResponseDto(HttpStatus.OK, "상품이 등록되었습니다.");
    }

    // 전체 상품 조회
    @Transactional(readOnly = true)
    public List<ProductListResponseDto> getProductList() {
        List<ProductListResponseDto> productListResponseDtos = new ArrayList<>();
        List<Product> products = productRepository.findAllByIsDoneFalseOrderByCreatedAtDesc();
        for (Product product : products) {
            productListResponseDtos.add(new ProductListResponseDto(product));
        }
        return productListResponseDtos;
    }

    //    선택 상품 상세 조회
    @Transactional(readOnly = true)
    public ProductDetailResponseDto getProductDetailList(Long pdid, User user) {
        Product getProduct = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("상품을 찾을 수 없습니다.")
        );

        boolean isAuth = false;
        if (user != null) {
            if (user.getRole() == UserRoleEnum.ADMIN || user.getId().equals(getProduct.getUser().getId())) {
                isAuth = true;
            }
        }

        boolean isDibs = false;
        Optional<ProductDibs> userProductDib = productDibsRepository.findByUserIdAndProductId(user.getId(), getproduct.getId());
        if (userProductDib.isPresent()) {
            isDibs = true;
        }

        // 조회하고 있는 상품과 동일한 카테고리인 최신 상품 리스트 6개 반환
        List<ProductListResponseDto> productListResponseDtos = productRepository
                .findLatestNotDoneProductsByCategoryIdExceptGivenId(6, pdid).stream()
                .map(ProductListResponseDto::new)
                .collect(Collectors.toList());

        return new ProductDetailResponseDto(getproduct, productListResponseDtos, isAuth, isDibs);
    }

    //    상품 수정
    @Transactional
    public MessageResponseDto update(Long pdid, ProductRequestDto productRequestDto, User user, MultipartFile image) throws IOException {
        Product getProduct = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );
        if (isMatchUserOrAdmin(getProduct, user)) {

//           기존 이미지 삭제
            String img = getProduct.getImg();
            S3ImageDelete(img);

//            새 이미지 등록
            String key = S3ImageUpload(image);

//          업데이트 메서드
            getProduct.update(productRequestDto, key);
            return new MessageResponseDto(HttpStatus.OK, "상품이 수정 되었습니다.");
        }
        throw new IllegalArgumentException("상품을 수정할 권한이 없습니다.");
    }

    // 상품 사진제외하고 수정
    @Transactional
    public MessageResponseDto textUpdate(Long pdid, ProductRequestDto productRequestDto, User user) {
        Product getProduct = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );

        if (isMatchUserOrAdmin(getProduct, user)) {
            getProduct.textUpdate(productRequestDto);
            return new MessageResponseDto(HttpStatus.OK, "상품이 수정 되었습니다.");
        }
        throw new IllegalArgumentException("상품을 수정할 권한이 없습니다");
    }


    //    상품 삭제
    @Transactional
    public MessageResponseDto delete(Long pdid, User user) {
        Product getProduct = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );

        if (isMatchUserOrAdmin(getProduct, user)) {
            String img = getProduct.getImg();
            S3ImageDelete(img);
            productRepository.deleteById(pdid);
            return new MessageResponseDto(HttpStatus.OK, "상품이 삭제 되었습니다.");
        }
        throw new IllegalArgumentException("상품을 삭제할 권한이 없습니다");
    }

    //    구매 완료 메서드
    @Transactional
    public MessageResponseDto modifyDone(Long pdid, User user) {
        Product getProduct = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );
//        해당 상품이 판매완료이면 구매불가
        if (getProduct.isDone()) throw new IllegalArgumentException("이미 판매 완료된 상품입니다.");
//        자신이 올린 상품이면 구매불가
        if (getProduct.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("자신이 올린 상품은 구매하실 수 없습니다.");
        }
        getProduct.setDone(true);
        productRepository.save(getProduct);
        return new MessageResponseDto(HttpStatus.OK, "상품을 정상적으로 구매하셨습니다.");

    }

    //    유저 검증 메서드
    private boolean isMatchUserOrAdmin(Product product, User user) {
        return (product.getUser().getEmail().equals(user.getEmail())) || (user.getRole() == UserRoleEnum.ADMIN);
    }

    // 추가 기능 추후에 적용 예정 (무한스크롤)
    @Transactional(readOnly = true)
    public Long getCountAllProducts() {

        return productRepository.countProducts();
    }

    // 추가 기능 추후에 적용 예정 (무한스크롤)
    @Transactional(readOnly = true)
    public List<ProductListResponseDto> getPageOfProduct(ReqProductPageableDto dto) {

        return productRepository.findAllByIsDoneFalse(configPageAble(dto))
                .stream().map(ProductListResponseDto::new).toList();
    }

    // 추가 기능 추후에 적용 예정 (무한스크롤)
    private Pageable configPageAble(ReqProductPageableDto dto) {

        Sort.Direction direction = dto.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSortBy());

        return PageRequest.of(dto.getPage() - 1, dto.getSize(), sort);
    }

    @Transactional
    public MessageResponseDto dibs(Long pdid, User user) {

        Product getProduct = productRepository.findById(pdid).orElseThrow(
                () -> new IllegalArgumentException("찜 할 게시물이 없습니다.")
        );

        Optional<ProductDibs> productDibs = productDibsRepository.findByUserIdAndProductId(user.getId(), getProduct.getId());

        if (productDibs.isEmpty()) {
            productDibsRepository.save(new ProductDibs(user.getId(), getProduct.getId()));
            return new MessageResponseDto(HttpStatus.OK, "상품 찜 성공");
        }
        productDibsRepository.deleteByUserIdAndProductId(user.getId(), getProduct.getId());
        return new MessageResponseDto(HttpStatus.OK, "상품 찜 삭제 성공");
}
