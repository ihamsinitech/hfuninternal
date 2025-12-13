package com.hfuninternal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private Long id;       // lowercase, matches user.getId()
    private String username;
    private String email;
}
