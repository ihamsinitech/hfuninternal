package com.hfuninternal.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private String signup;     // e.g., date/time of signup
    private String fullName;
}
