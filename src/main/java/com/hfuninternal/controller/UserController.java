package com.hfuninternal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.hfuninternal.dto.UserDto;
import com.hfuninternal.model.User;
import com.hfuninternal.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // Get current logged-in user
    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        User currentUser = userService.getCurrentUser();
        UserDto dto = userService.convertToDto(currentUser, currentUser);
        return ResponseEntity.ok(dto);
    }

    // Get user profile by ID
    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserDto> profile(@PathVariable Long userId) {
        User currentUser = userService.getCurrentUser();
        User targetUser = userService.getUserProfile(userId);
        UserDto dto = userService.convertToDto(currentUser, targetUser);
        return ResponseEntity.ok(dto);
    }

    // Update profile
    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateProfile(@RequestBody User updated) {
        UserDto dto = userService.updateProfile(updated);
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
