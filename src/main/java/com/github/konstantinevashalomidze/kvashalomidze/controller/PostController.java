package com.github.konstantinevashalomidze.kvashalomidze.controller;

import com.github.konstantinevashalomidze.kvashalomidze.model.document.Post;
import com.github.konstantinevashalomidze.kvashalomidze.repository.PostRepository;
import com.github.konstantinevashalomidze.kvashalomidze.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/public/posts")
public class PostController {
    private final PostRepository postRepository;
    private final EmailService emailService;

    @Autowired
    public PostController(PostRepository postRepository, EmailService emailService) {
        this.postRepository = postRepository;
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<?> getPosts(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Post> posts = search.isBlank()
                ? postRepository.findAll(pageable)
                : postRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable);


        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> like(@PathVariable Long id) {
        Post p = postRepository.findById(id).orElse(new Post());
        p.setLikeCount(p.getLikeCount() + 1);
        postRepository.save(p);
        return ResponseEntity.ok(Map.of("likeCount", p.getLikeCount()));
    }

}
