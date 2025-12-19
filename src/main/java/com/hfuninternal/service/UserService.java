package com.hfuninternal.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hfuninternal.model.User;
import com.hfuninternal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // ===============================
    // Get logged-in user from JWT
    // ===============================
    public User getCurrentUser() {
        System.out.println("=== UserService.getCurrentUser() ===");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        Object principal = auth.getPrincipal();

        // ✅ JWT Filter sets User object directly
        if (principal instanceof User) {
            return (User) principal;
        }

        // 🔁 Fallback (safe)
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }

    // ===============================
    // Get user profile by ID
    // ===============================
    public User getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ===============================
    // Update profile (SAFE MERGE)
    // ===============================
    public User updateProfile(User currentUser, User updated) {

        // 🔒 DO NOT overwrite required fields with null

        if (updated.getFullName() != null && !updated.getFullName().isBlank()) {
            currentUser.setFullName(updated.getFullName());
        }

        if (updated.getBio() != null) {
            currentUser.setBio(updated.getBio());
        }

        if (updated.getProfilePictureUrl() != null) {
            currentUser.setProfilePictureUrl(updated.getProfilePictureUrl());
        }

        if (updated.getUsername() != null && !updated.getUsername().isBlank()) {
            currentUser.setUsername(updated.getUsername());
        }

        // ❌ NEVER TOUCH THESE
        // email
        // password
        // confirmPassword
        // signup

        return userRepository.save(currentUser);
    }

    // ===============================
    // Update profile picture
    // ===============================
    public void updateProfilePicture(User currentUser, String url) {
        currentUser.setProfilePictureUrl(url);
        userRepository.save(currentUser);
    }
}
