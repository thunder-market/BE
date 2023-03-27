package com.example.thundermarket.products.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ImageService {

    private final S3Client s3Client;
    private final String bucketName;

    public S3ImageService(S3Client s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public String uploadImage(MultipartFile image) throws IOException {
        String key = UUID.randomUUID().toString(); // 또는 다른 고유한 키 생성 방법

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("uploaded-image/" + key)
                .contentType(image.getContentType())
                .contentLength(image.getSize())
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(image.getInputStream(), image.getSize()));
        return key;
    }

    public void deleteImage(String key) {
        String div = "uploaded-image";
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(div + "/" + key).build());
    }

    public ResponseInputStream<GetObjectResponse> downloadImage(String key) {
        return s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key("uploaded-image/" + key)
                .build());
    }

}
