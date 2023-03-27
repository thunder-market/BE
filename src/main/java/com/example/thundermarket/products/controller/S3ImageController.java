package com.example.thundermarket.products.controller;

import com.example.thundermarket.products.dto.ImageResponseDto;
import com.example.thundermarket.products.service.S3ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/image")
public class S3ImageController {

    private final S3ImageService s3ImageService;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_IMAGE_CONTENT_TYPES = List.of("image/jpeg", "image/png", "image/gif");

    @Autowired
    public S3ImageController(S3ImageService s3ImageService) {
        this.s3ImageService = s3ImageService;
    }

    // 객체 1개 업로드
    @PostMapping("/upload")
    public ResponseEntity<ImageResponseDto> uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            validateImage(image);
            String key = s3ImageService.uploadImage(image);
            return ResponseEntity.ok().body(new ImageResponseDto(key));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 객체 여러개 업로드
    @PostMapping("/uploads")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("image") List<MultipartFile> images) {
        List<String> keys = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                validateImage(image);
                String key = s3ImageService.uploadImage(image);
                keys.add(key);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.ok(keys);
    }

    // 객체 삭제
    @DeleteMapping("/delete/{key}")
    public ResponseEntity<Void> deleteImage(@PathVariable String key) {
        s3ImageService.deleteImage(key);
        return ResponseEntity.noContent().build();
    }

    // 객체 다운로드
    @GetMapping("/download/{key}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable String key) {
        try {
            ResponseInputStream<GetObjectResponse> s3Object = s3ImageService.downloadImage(key);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(s3Object.response().contentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + key)
                    .body(new InputStreamResource(s3Object));
        } catch (S3Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void validateImage(MultipartFile image) {
        if (image.getSize() > MAX_FILE_SIZE) {
            throw new IllegalStateException("File size exceeds the maximum allowed size (5MB).");
        }

        if (!ALLOWED_IMAGE_CONTENT_TYPES.contains(image.getContentType())) {
            throw new IllegalStateException("Invalid file format. Allowed formats: JPEG, PNG, GIF.");
        }
    }

}