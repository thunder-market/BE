package com.example.thundermarket.products.controller;

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
@RequestMapping("/s3")
public class S3ImageController {

    private final S3ImageService s3ImageService;

    @Autowired
    public S3ImageController(S3ImageService s3ImageService) {
        this.s3ImageService = s3ImageService;
    }

    // 객체 1개 업로드
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            String key = s3ImageService.uploadImage(image);
            return ResponseEntity.ok(key);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 객체 여러개 업로드
    @PostMapping("/upload-images")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("image") List<MultipartFile> images) {
        List<String> keys = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
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
    @DeleteMapping("/delete-image/{key}")
    public ResponseEntity<Void> deleteImage(@PathVariable String key) {
        s3ImageService.deleteImage(key);
        return ResponseEntity.noContent().build();
    }

    // 객체 다운로드
    @GetMapping("/download-image/{key}")
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

}