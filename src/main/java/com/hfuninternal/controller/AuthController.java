package com.hfuninternal.controller;

import com.hfuninternal.dto.*;
import com.hfuninternal.model.User;
import com.hfuninternal.security.JwtUtil;
import com.hfuninternal.service.AuthService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        logger.info("Login attempt for {}", request.getEmailOrUsername());

        User user = authService.login(request);

        String token = jwtUtil.generateToken(user.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("tokenType", "Bearer");
        response.put("token", token);
        response.put("userId", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());

        return ResponseEntity.ok(response);
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok(
                Map.of("message", "User registered", "userId", user.getId())
        );
    }

    // ✅ GET USERS (ADMIN / INTERNAL)
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    // ✅ FORGOT PASSWORD
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(
                Map.of("message", "Password reset link sent")
        );
    }

    // ✅ RESET PASSWORD
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        authService.resetPassword(
                request.getEmail(),
                request.getNewPassword(),
                request.getConfirmPassword()
        );

        return ResponseEntity.ok(
                Map.of("message", "Password reset successful")
        );
    }

    // ✅ TEST JWT (NO validateToken call)
    @GetMapping("/test-token")
    public ResponseEntity<?> testToken() {

        String token = jwtUtil.generateToken("test@example.com");

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "extractedEmail", jwtUtil.extractEmail(token),
                        "status", "JWT working"
                )
        );
    }
}
