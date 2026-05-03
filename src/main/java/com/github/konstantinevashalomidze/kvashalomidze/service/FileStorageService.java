package com.github.konstantinevashalomidze.kvashalomidze.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    @Value("${app.upload-dir}")
    private String uploadDir;


    public String store(MultipartFile file, String subfolder) {
        try {
            Path dir = Path.of(uploadDir, subfolder).normalize();
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), dir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            return subfolder + "/" + filename;
        } catch (IOException e) {
            System.out.printf("Error: %s in File: %s with Subfolder: %s", e.getMessage(), file, subfolder);
            throw new RuntimeException(e);
        }
    }

    public void delete(String relativePath) {
        try {
            Files.deleteIfExists(Path.of(uploadDir, relativePath));
        } catch (IOException e) {
            System.out.printf("Error: %s in File: %s with Subfolder: %s", e.getMessage(), relativePath, uploadDir);
            throw new RuntimeException(e);
        }
    }



}
