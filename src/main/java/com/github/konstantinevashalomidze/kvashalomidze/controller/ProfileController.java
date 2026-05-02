package com.github.konstantinevashalomidze.kvashalomidze.controller;

import com.github.konstantinevashalomidze.kvashalomidze.model.record.ProfileRequest;
import org.springframework.web.bind.annotation.RestController;


import com.github.konstantinevashalomidze.kvashalomidze.model.document.Profile;
import com.github.konstantinevashalomidze.kvashalomidze.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/api/v1/public/profile")
    public ResponseEntity<Profile> getProfile() {
        return ResponseEntity.ok(profileService.getLatest());
    }

    @PostMapping("/api/v1/admin/profile")
    public ResponseEntity<Profile> createProfile(@RequestBody ProfileRequest request) {
        return ResponseEntity.ok(profileService.create(request));
    }
}