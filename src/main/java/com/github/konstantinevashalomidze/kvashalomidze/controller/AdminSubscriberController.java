package com.github.konstantinevashalomidze.kvashalomidze.controller;

import com.github.konstantinevashalomidze.kvashalomidze.repository.SubscriberRepository;
import com.github.konstantinevashalomidze.kvashalomidze.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/subscribers")
@RequiredArgsConstructor
public class AdminSubscriberController {
    private final SubscriberRepository subscriberRepository;
    private final EmailService emailService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(subscriberRepository.findAll());
    }

    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@RequestBody Map<String, String> body) {
        String text = body.get("text");
        subscriberRepository.findAll()
                .forEach(s -> emailService.sendMessage(s.getEmail(), text));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
