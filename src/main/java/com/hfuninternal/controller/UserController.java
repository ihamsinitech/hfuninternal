package com.hfuninternal.controller;

import com.hfuninternal.dto.UserDTO;
import com.hfuninternal.model.User;
import com.hfuninternal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // Get current logged-in user
    @GetMapping("/me")
    public ResponseEntity<UserDTO> me() {
        User currentUser = userService.getCurrentUser();
        UserDTO dto = userService.convertToDto(currentUser, currentUser);
        return ResponseEntity.ok(dto);
    }

    // Get user profile by ID
    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserDTO> profile(@PathVariable Long userId) {
        User currentUser = userService.getCurrentUser();
        User targetUser = userService.getUserProfile(userId);
        UserDTO dto = userService.convertToDto(currentUser, targetUser);
        return ResponseEntity.ok(dto);
    }

    // Update profile
    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody User updated) {
        UserDTO dto = userService.updateProfile(updated);
        return ResponseEntity.ok(dto);
    }

    // Update profile picture
    @PostMapping("/upload-profile-picture")
    public ResponseEntity<?> uploadProfilePic(@RequestParam String imageUrl) {
        userService.updateProfilePicture(imageUrl);
        return ResponseEntity.ok(Map.of("success", true));
    }

    // Health check
    @GetMapping("/check")
    public ResponseEntity<?> check() {
        return ResponseEntity.ok(Map.of(
            "status", "OK",
            "service", "User Controller",
            "time", System.currentTimeMillis()
        ));
    }
}