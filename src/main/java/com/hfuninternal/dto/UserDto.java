package com.hfuninternal.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long _id;
    private String username;
    private String fullName;
    private boolean isFollowing;
}
