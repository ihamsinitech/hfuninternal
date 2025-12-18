package com.hfuninternal.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hfuninternal.model.Highlight;
import com.hfuninternal.service.HighlightService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users/{userId}/highlights")
@RequiredArgsConstructor
public class HighlightController {

    private final HighlightService highlightService;

    // Get highlights
    @GetMapping
    public ResponseEntity<List<Highlight>> getHighlights(@PathVariable Long userId) {
        List<Highlight> highlights = highlightService.getHighlightsByUser(userId);
        return ResponseEntity.ok(highlights);
    }

    // Create highlight
    @PostMapping
    public ResponseEntity<Highlight> createHighlight(
            @PathVariable Long userId,
            @RequestParam String mediaUrl) { // or @RequestBody if sending JSON
        Highlight highlight = highlightService.createHighlight(userId, mediaUrl);
        return ResponseEntity.ok(highlight);
    }
}
