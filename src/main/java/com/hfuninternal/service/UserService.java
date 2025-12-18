package com.hfuninternal.service;

import java.security.Principal;

import org.springframework.stereotype.Service;

import com.hfuninternal.model.User;
import com.hfuninternal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateProfile(User currentUser, User updated) {
        currentUser.setFullName(updated.getFullName());
        currentUser.setBio(updated.getBio());
        return userRepository.save(currentUser);
    }

    public User updateProfilePicture(User currentUser, String url) {
        currentUser.setProfilePictureUrl(url);
        return userRepository.save(currentUser);
    }
}
