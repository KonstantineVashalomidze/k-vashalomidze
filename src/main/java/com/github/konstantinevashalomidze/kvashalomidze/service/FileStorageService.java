package com.github.konstantinevashalomidze.kvashalomidze.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
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
            log.error("Error: {} in File: {} with Subfolder: {}", e.getMessage(), file, subfolder);
            throw new RuntimeException(e);
        }
    }

    public void delete(String relativePath) {
        try {
            Files.deleteIfExists(Path.of(uploadDir, relativePath));
        } catch (IOException e) {
            log.error("Error: {} in File: {} with Subfolder: {}", e.getMessage(), relativePath, uploadDir);
            throw new RuntimeException(e);
        }
    }



}
