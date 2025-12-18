package com.hfuninternal.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hfuninternal.service.BlockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class BlockController {

    private final BlockService blockService;

    // ✅ POST /api/users/{userId}/block
    @PostMapping("/{userId}/block")
    public ResponseEntity<?> blockUser(
            @PathVariable Long userId,
            Principal principal) {

        blockService.blockUser(userId, principal);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
