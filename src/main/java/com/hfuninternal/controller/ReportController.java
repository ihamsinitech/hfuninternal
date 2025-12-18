package com.hfuninternal.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hfuninternal.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // ✅ POST /api/users/{userId}/report
    @PostMapping("/{userId}/report")
    public ResponseEntity<?> reportUser(
            @PathVariable Long userId,
            @RequestParam String reason,
            Principal principal) {

        reportService.reportUser(userId, reason, principal);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
