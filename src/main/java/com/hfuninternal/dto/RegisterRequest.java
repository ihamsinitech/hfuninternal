package com.hfuninternal.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String username;
    private String fullName;
    private String password;
    private String confirmPassword;
    //private String signup;     // e.g., date/time of signup
    
}

//name,username,mobileNumber/email otp should be come to email/movileNumberand verify otp 
