package com.hfuninternal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.hfuninternal.model.User;
import com.hfuninternal.service.UserService;

import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")  // ← ADD THIS LINE
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        System.out.println("=== /api/users/me endpoint called ===");
        try {
            User user = userService.getCurrentUser();
            System.out.println("✅ Success - Returning user: " + user.getEmail());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return ResponseEntity.status(401).body(Map.of(
                "error", "Authentication failed",
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<User> profile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody User updated) {
        try {
            User currentUser = userService.getCurrentUser();
            User updatedUser = userService.updateProfile(currentUser, updated);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/upload-profile-picture")
    public ResponseEntity<?> uploadProfilePic(@RequestParam String imageUrl) {
        try {
            User currentUser = userService.getCurrentUser();
            userService.updateProfilePicture(currentUser, imageUrl);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
    
    // ✅ TEST ENDPOINT
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of(
            "message", "User API is working",
            "timestamp", System.currentTimeMillis()
        ));
    }
    
    // ✅ ADD THIS DEBUG ENDPOINT
    @GetMapping("/debug-security")
    public ResponseEntity<?> debugSecurity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> response = new HashMap<>();
        response.put("authExists", auth != null);
        response.put("authName", auth != null ? auth.getName() : "null");
        response.put("isAuthenticated", auth != null && auth.isAuthenticated());
        response.put("authorities", auth != null ? auth.getAuthorities().toString() : "null");
        
        if (auth != null) {
            response.put("principalClass", auth.getPrincipal().getClass().getName());
            if (auth.getPrincipal() instanceof User) {
                User user = (User) auth.getPrincipal();
                response.put("userEmail", user.getEmail());
                response.put("userId", user.getId());
            }
        }
        
        return ResponseEntity.ok(response);
    }
    
    // ✅ ADD THIS SIMPLE CHECK ENDPOINT
    @GetMapping("/check")
    public ResponseEntity<?> check() {
        return ResponseEntity.ok(Map.of(
            "status", "OK",
            "service", "User Controller",
            "time", System.currentTimeMillis()
        ));
    }
}