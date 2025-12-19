package com.hfuninternal.service.impl;

import com.hfuninternal.dto.LoginRequest;
import com.hfuninternal.dto.RegisterRequest;
import com.hfuninternal.exception.BadRequestException;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.UserRepository;
import com.hfuninternal.service.AuthService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ REGISTER
    @Override
    public User register(RegisterRequest request) {

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
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());   // ✅ REQUIRED
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setSignup(LocalDate.now().toString()); // ✅ REQUIRED (NOT NULL)

        // Optional fields
        user.setBio(null);
        user.setProfilePictureUrl(null);

        // 4️⃣ Save
        return userRepository.save(user);
    }

    // ✅ LOGIN
    @Override
    public User login(LoginRequest request) {

        Optional<User> userOptional =
                userRepository.findByEmail(request.getEmailOrUsername());

        if (userOptional.isEmpty()) {
            userOptional =
                    userRepository.findByUsername(request.getEmailOrUsername());
        }

        User user = userOptional
                .orElseThrow(() ->
                        new BadRequestException("Invalid email/username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email/username or password");
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
                .orElseThrow(() ->
                        new BadRequestException("User with email " + email + " not found"));

        // TODO: send email
        System.out.println("Password reset link sent to: " + user.getEmail());
    }

    // ✅ RESET PASSWORD
    @Override
    public void resetPassword(String email,
                              String newPassword,
                              String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            throw new BadRequestException("Password and Confirm Password do not match");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BadRequestException("User with email " + email + " not found"));

        // ✅ FIXED LINE
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        System.out.println("Password reset for user: " + email);
    }
}
