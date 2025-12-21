package com.hfuninternal.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class AuthDTO {
    
    @Data
    public static class LoginRequest {
        @NotBlank
        @Email
        private String email;
        
        @NotBlank
        @Size(min = 6)
        private String password;
    }
    
    @Data
    public static class RegisterRequest {
        @NotBlank
        @Size(min = 3, max = 50)
        private String username;
        
        @NotBlank
        @Email
        private String email;
        
        @NotBlank
        @Size(min = 6)
        private String password;
        
        private String fullName;
        
        @Size(min = 6)
        private String confirmPassword;
    }
    
    @Data
    public static class ForgotPasswordRequest {
        @NotBlank
        @Email
        private String email;
    }
    
    @Data
    public static class ResetPasswordRequest {
        @NotBlank
        @Size(min = 6)
        private String password;
        
        @NotBlank
        private String token;
    }
    
    @Data
    public static class LoginResponse {
        private String message = "Login successful";
        private String tokenType = "Bearer";
        private String token;
        private Long userId;
        private String email;
        private String username;
        
        public LoginResponse(String token, Long userId, String email, String username) {
            this.token = token;
            this.userId = userId;
            this.email = email;
            this.username = username;
        }
    }
}