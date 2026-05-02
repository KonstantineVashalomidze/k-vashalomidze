package com.github.konstantinevashalomidze.kvashalomidze.controller;

import com.github.konstantinevashalomidze.kvashalomidze.model.document.Contact;
import com.github.konstantinevashalomidze.kvashalomidze.model.document.Profile;
import com.github.konstantinevashalomidze.kvashalomidze.model.document.Subscriber;
import com.github.konstantinevashalomidze.kvashalomidze.repository.*;
import com.github.konstantinevashalomidze.kvashalomidze.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/v1/public/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileRepository profileRepository;
    private final ContactRepository contactRepository;
    private final EducationRepository educationRepository;
    private final ResumeRepository resumeRepository;
    private final SubscriberRepository subscriberRepository;

    private final EmailService emailService;

    @GetMapping
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok(
                Map.of(
                        "profile", profileRepository.findFirstByOrderByIdDesc().orElseThrow(),
                        "contact", contactRepository.findFirstByOrderByIdDesc().orElseThrow(),
                        "education", educationRepository.findAll(),
                        "resumes", resumeRepository.findAll(),
                        "subscribers", subscriberRepository.findAll()
                )
        );
    }

    @PostMapping("/charisma")
    public ResponseEntity<?> incrementCharisma() {
        Profile p = profileRepository.findFirstByOrderByIdDesc().orElseThrow();
        p.setCharismaCount(p.getCharismaCount() + 1);
        profileRepository.save(p);


        // TODO: Send me telegram message that my charisma have been increased.

        return ResponseEntity.ok(Map.of("count", p.getCharismaCount()));
    }


    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody Map<String, String> b) {
        Subscriber s = new Subscriber();
        String email = b.get("email");
        s.setEmail(email);
        subscriberRepository.save(s);
        emailService.sendMessage(email, """
                Hello,
                
                From now on, you are going to receive email notifications from me.
                
                Best regards,
                Konstantine Vashalomidze
                """);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
