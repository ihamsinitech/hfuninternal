package com.hfuninternal.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hfuninternal.model.Post;
import com.hfuninternal.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/feed") 
    //postman url down one 
     //http://localhost:8080/api/posts/feed?page=0&limit=10
    public ResponseEntity<?> feed(
            @RequestParam int page,
            @RequestParam int limit
    ) {
        var posts = postService.getFeed(page, limit);

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "posts", posts.getContent(),
                        "hasMore", posts.hasNext()
                )
        );
    }

    // ✅ ADD THIS
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Post post) {
        return ResponseEntity.ok(
            Map.of(
                "success", true,
                "post", postService.create(post)
            )
        );
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> like(@PathVariable Long id) {
        postService.like(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<?> unlike(@PathVariable Long id) {
        postService.unlike(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/{id}/share")
    public ResponseEntity<?> share(@PathVariable Long id) {
        postService.share(id);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
