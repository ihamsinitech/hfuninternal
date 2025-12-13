package com.hfuninternal.service;

import java.util.List;

import com.hfuninternal.dto.LoginRequest;
import com.hfuninternal.dto.RegisterRequest;
import com.hfuninternal.model.User;

public interface AuthService {
    User register(RegisterRequest request);
    User login(LoginRequest request);
    List<User> getAllUsers();

}
