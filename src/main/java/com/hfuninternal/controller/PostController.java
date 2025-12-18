package com.hfuninternal.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hfuninternal.dto.FeedResponseDto;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.UserRepository;
import com.hfuninternal.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    @GetMapping("/feed")
    public ResponseEntity<?> feed(@RequestParam int page,
                                  @RequestParam int limit,
                                  Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        FeedResponseDto feed = postService.getFeed(page, limit, user);
        return ResponseEntity.ok(feed);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Map<String, Boolean>> like(@PathVariable Long postId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        postService.like(postId, user.getId());
        return ResponseEntity.ok(Map.of("success", true));
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Map<String, Boolean>> unlike(@PathVariable Long postId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        postService.unlike(postId, user.getId());
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/{postId}/share")
    public ResponseEntity<Map<String, Boolean>> share(@PathVariable Long postId) {
        postService.share(postId);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }
}
