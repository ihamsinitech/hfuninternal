package com.hfuninternal.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long _id;
    private String username;
    private String fullName;
    private String bio;
    private String profilePictureUrl;
    private boolean isFollowing;
    private int followerCount;
    private int followingCount;
}
