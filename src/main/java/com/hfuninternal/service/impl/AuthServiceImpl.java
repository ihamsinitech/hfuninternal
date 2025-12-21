package com.hfuninternal.service.impl;

import com.hfuninternal.dto.AuthDTO;  // Changed: lowercase 'dto'
import com.hfuninternal.exception.BadRequestException;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.UserRepository;
import com.hfuninternal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ✅ REGISTER
    @Override
    public User register(AuthDTO.RegisterRequest request) {
        // 1️⃣ Validate passwords
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Password and Confirm Password do not match");
        }

        // 2️⃣ Unique checks
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email is already taken");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("Username is already taken");
        }

        // 3️⃣ Create user
        User user = User.builder()
            .email(request.getEmail())
            .username(request.getUsername())
            .firstName(request.getFullName()) // Using fullName from request as firstName
            .password(passwordEncoder.encode(request.getPassword()))
            .enabled(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        // Set empty/default values for optional fields
        user.setLastName("");
        user.setBio("");
        user.setProfilePicture("");

        // 4️⃣ Save
        return userRepository.save(user);
    }

    // ✅ LOGIN
    @Override
    public User login(AuthDTO.LoginRequest request) {
        // Try to find by email first
        User user = userRepository.findByEmail(request.getEmail())
                .orElseGet(() -> {
                    // If not found by email, try by username
                    return userRepository.findByUsername(request.getEmail())
                            .orElseThrow(() -> new BadRequestException("Invalid email/username or password"));
                });

        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email/username or password");
        }

        // Check if user is enabled
        if (!user.isEnabled()) {
            throw new BadRequestException("Account is disabled");
        }

        return user;
    }

    // ✅ GET ALL USERS
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ FORGOT PASSWORD
    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User with email " + email + " not found"));

        // TODO: Implement actual email sending logic
        System.out.println("Password reset link would be sent to: " + user.getEmail());
        
        // In production, you would:
        // 1. Generate reset token
        // 2. Save token to database
        // 3. Send email with reset link
    }

    // ✅ RESET PASSWORD
    @Override
    public void resetPassword(String email, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new BadRequestException("Password and Confirm Password do not match");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User with email " + email + " not found"));

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        System.out.println("Password reset successfully for user: " + email);
    }
}