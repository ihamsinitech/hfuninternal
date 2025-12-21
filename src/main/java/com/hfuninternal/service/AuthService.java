package com.hfuninternal.service;

import com.hfuninternal.dto.AuthDTO;
import com.hfuninternal.model.User;

import java.util.List;

public interface AuthService {
    User register(AuthDTO.RegisterRequest request);
    User login(AuthDTO.LoginRequest request);
    List<User> getAllUsers();
    void forgotPassword(String email);
    void resetPassword(String email, String newPassword, String confirmPassword);
}