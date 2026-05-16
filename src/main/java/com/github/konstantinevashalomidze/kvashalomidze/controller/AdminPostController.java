package com.github.konstantinevashalomidze.kvashalomidze.controller;

import com.github.konstantinevashalomidze.kvashalomidze.model.document.Post;
import com.github.konstantinevashalomidze.kvashalomidze.model.document.Subscriber;
import com.github.konstantinevashalomidze.kvashalomidze.repository.PostRepository;
import com.github.konstantinevashalomidze.kvashalomidze.repository.SubscriberRepository;
import com.github.konstantinevashalomidze.kvashalomidze.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/admin/posts")
public class AdminPostController {
    private final PostRepository postRepository;
    private final EmailService emailService;
    private final SubscriberRepository subscriberRepository;

    public AdminPostController(PostRepository postRepository, EmailService emailService, SubscriberRepository subscriberRepository) {
        this.postRepository = postRepository;
        this.emailService = emailService;
        this.subscriberRepository = subscriberRepository;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        Post p = new Post();
        p.setTitle((String) body.get("title"));
        p.setDescription((String) body.get("description"));
        p.setReferencesLink((String) body.get("referencesLink"));
        p.setTags((String) body.get("richContent"));
        p.setProject((Boolean) body.get("isProject"));
        postRepository.save(p);
        List<Subscriber> subscribers = subscriberRepository.findAll();
        subscribers.forEach(s -> emailService.sendMessage(s.getEmail(),
                """
                     <p>Hello</p>
                     <p>Check out my new post guys.</p>
                     """
                ));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Post p = postRepository.findById(id).orElse(new Post());
        p.setTitle((String) body.get("title"));
        p.setDescription((String) body.get("description"));
        p.setReferencesLink((String) body.get("referencesLink"));
        p.setRichContent((String) body.get("richContent"));
        p.setProject((Boolean) body.getOrDefault("isProject", false));
        postRepository.save(p);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        postRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
