package com.github.konstantinevashalomidze.kvashalomidze.controller;

import com.github.konstantinevashalomidze.kvashalomidze.model.document.Resume;
import com.github.konstantinevashalomidze.kvashalomidze.repository.ResumeRepository;
import com.github.konstantinevashalomidze.kvashalomidze.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@RestController
@RequestMapping("/api/v1/admin/resumes")
@RequiredArgsConstructor
public class AdminResumeController {
    private final ResumeRepository resumeRepository;
    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(
            @RequestParam String versionLabel,
            @RequestParam MultipartFile file
            ) {
        Resume r = new Resume();
        r.setVersionLabel(versionLabel);
        r.setOriginalFilename(file.getOriginalFilename());
        r.setFilePath(fileStorageService.store(file, "resumes"));
        resumeRepository.save(r);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Resume r = resumeRepository.findById(id).orElseThrow();
        fileStorageService.delete(r.getFilePath());
        resumeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
