package com.peepl.peepl_codetest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class LocalStorageService {

    private static final String BASE_DIR = "uploads/clients/";

    public String uploadFile(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(BASE_DIR));

        String fileName = generateFileName(file.getOriginalFilename());
        Path filePath = Paths.get(BASE_DIR + fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return buildFileUrl(fileName);
    }

    private String generateFileName(String originalFilename) {
        String ext = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID() + ext;
    }

    private String buildFileUrl(String fileName) {
        return "http://localhost:8086/uploads/clients/" + fileName;
    }
}