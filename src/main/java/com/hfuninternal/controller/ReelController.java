package com.hfuninternal.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hfuninternal.model.Reel;
import com.hfuninternal.service.ReelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reels")
@RequiredArgsConstructor
public class ReelController {

    private final ReelService reelService;

    @GetMapping("/feed")
    public ResponseEntity<?> feed(@RequestParam int page, @RequestParam int limit) {
        var reels = reelService.feed(page, limit);

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "reels", reels.getContent(),
                        "hasMore", reels.hasNext()
                )
        );
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Reel reel) {
        return ResponseEntity.ok(Map.of("success", true, "reel", reelService.create(reel)));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Map<String, Boolean>> like(@PathVariable Long id) {
        reelService.like(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Map<String, Boolean>> unlike(@PathVariable Long id) {
        reelService.unlike(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserReels(@PathVariable Long userId) {
        return ResponseEntity.ok(reelService.getReelsByUser(userId));
    }
}
