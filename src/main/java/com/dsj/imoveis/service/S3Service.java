package com.dsj.imoveis.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    String uploadImage(MultipartFile file) throws IOException;
}
