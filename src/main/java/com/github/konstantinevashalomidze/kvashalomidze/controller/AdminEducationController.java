package com.github.konstantinevashalomidze.kvashalomidze.controller;

import com.github.konstantinevashalomidze.kvashalomidze.model.document.Education;
import com.github.konstantinevashalomidze.kvashalomidze.repository.EducationRepository;
import com.github.konstantinevashalomidze.kvashalomidze.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/admin/education")
public class AdminEducationController {
    private final EducationRepository educationRepository;
    private final FileStorageService fileStorageService;

    public AdminEducationController(EducationRepository educationRepository, FileStorageService fileStorageService) {
        this.educationRepository = educationRepository;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> add(
                @RequestParam String title,
                @RequestParam String instituteName,
                @RequestParam String instituteUrl,
                @RequestParam String startDate,
                @RequestParam(required = false) String endDate,
                @RequestParam String description,
                @RequestParam(required = false) MultipartFile logo
    ) {
        Education e = new Education();
        e.setTitle(title);
        e.setInstituteName(instituteName);
        e.setInstituteUrl(instituteUrl);
        e.setStartDate(Instant.parse(startDate + "T00:00:00Z"));
        if (endDate != null && !endDate.isEmpty()) {
            e.setEndDate(Instant.parse(endDate + "T00:00:00Z"));
        }
        e.setDescription(description);

        if (logo != null && !logo.isEmpty()) {
            e.setLogoFilePath(fileStorageService.store(logo, "education"));
        }

        educationRepository.save(e);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        educationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
