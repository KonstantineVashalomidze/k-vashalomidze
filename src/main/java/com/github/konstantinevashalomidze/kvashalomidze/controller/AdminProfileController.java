package com.github.konstantinevashalomidze.kvashalomidze.controller;

import com.github.konstantinevashalomidze.kvashalomidze.model.document.Contact;
import com.github.konstantinevashalomidze.kvashalomidze.model.document.Profile;
import com.github.konstantinevashalomidze.kvashalomidze.repository.ContactRepository;
import com.github.konstantinevashalomidze.kvashalomidze.repository.ProfileRepository;
import com.github.konstantinevashalomidze.kvashalomidze.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/admin/profile")
public class AdminProfileController {
    private final ProfileRepository profileRepository;
    private final FileStorageService fileStorageService;
    private final ContactRepository contactRepository;

    public AdminProfileController(ProfileRepository profileRepository, FileStorageService fileStorageService, ContactRepository contactRepository) {
        this.profileRepository = profileRepository;
        this.fileStorageService = fileStorageService;
        this.contactRepository = contactRepository;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(
            @RequestParam String name,
            @RequestParam String profession,
            @RequestParam String aboutMe,
            @RequestParam(required = false) MultipartFile image
        ) {
        Profile p = profileRepository.findFirstByOrderByIdDesc().orElse(new Profile());
        p.setName(name);
        p.setAboutMe(aboutMe);
        p.setProfession(profession);

        if (image != null && !image.isEmpty()) {
            String path = fileStorageService.store(image, "profile");
            p.setImageFilePath(path);
        }

        profileRepository.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/contacts")
    public ResponseEntity<?> updateContacts(
            @RequestParam String codeforces,
            @RequestParam String email,
            @RequestParam String facebook,
            @RequestParam String github,
            @RequestParam String hackerrank,
            @RequestParam String leetcode,
            @RequestParam String linkedin,
            @RequestParam String phone,
            @RequestParam String typeRacer
    ) {
        Contact contact = contactRepository.findFirstByOrderByIdDesc().orElse(new Contact());
        contact.setCodeforces(codeforces);
        contact.setEmail(email);
        contact.setFacebook(facebook);
        contact.setGithub(github);
        contact.setHackerrank(hackerrank);
        contact.setLeetcode(leetcode);
        contact.setLinkedin(linkedin);
        contact.setPhone(phone);
        contact.setTypeRacer(typeRacer);
        contactRepository.save(contact);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




}
