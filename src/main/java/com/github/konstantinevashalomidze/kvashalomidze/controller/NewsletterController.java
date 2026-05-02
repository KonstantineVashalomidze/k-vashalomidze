package com.github.konstantinevashalomidze.kvashalomidze.controller;

import com.github.konstantinevashalomidze.kvashalomidze.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NewsletterController {

    private final EmailService emailService;

    public NewsletterController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/api/v1/newsletter")
    public ResponseEntity<?> subscribe(@RequestBody Map<String, String> body) {
        String subscriberEmail = body.get("email");
        if (subscriberEmail == null || subscriberEmail.isBlank())
            return ResponseEntity.badRequest().build();

        // notify yourself
        emailService.sendMessage(
                "vashalomidzekonstantine@gmail.com",
                "New newsletter subscriber: " + subscriberEmail
        );

        // confirm to subscriber
        emailService.sendMessage(
                subscriberEmail,
                "Thanks for subscribing to my newsletter!"
        );

        return ResponseEntity.ok().build();
    }
}