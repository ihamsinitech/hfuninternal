package com.hfuninternal.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hfuninternal.model.User;
import com.hfuninternal.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ✅ GET /api/users/me
    @GetMapping("/me")
    public ResponseEntity<User> me(Principal principal) {
        return ResponseEntity.ok(userService.getCurrentUser(principal));
    }

    // ✅ GET /api/users/{userId}/profile
    @GetMapping("/{userId}/profile")
    public ResponseEntity<User> profile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    // ✅ PUT /api/users/profile
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(
            @RequestBody User updated,
            Principal principal) {

        User currentUser = userService.getCurrentUser(principal);
        return ResponseEntity.ok(userService.updateProfile(currentUser, updated));
    }

    // ✅ POST /api/users/upload-profile-picture
    @PostMapping("/upload-profile-picture")
    public ResponseEntity<?> uploadProfilePic(
            @RequestParam String imageUrl,
            Principal principal) {

        User currentUser = userService.getCurrentUser(principal);
        userService.updateProfilePicture(currentUser, imageUrl);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
