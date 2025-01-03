package com.dsj.imoveis.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dsj.imoveis.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
        } catch (AmazonServiceException e) {
            throw new IOException("Erro ao acessar o serviço da AWS: " + e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new IOException("Erro no cliente da AWS: " + e.getMessage(), e);
        }

        return amazonS3.getUrl(bucketName, fileName).toString();
    }

}
