package com.hfuninternal.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String bio;
    private String profilePictureUrl;
    private String website;
    private String gender;
    
    // Use wrapper Boolean instead of primitive boolean for better Lombok compatibility
    private Boolean privateAccount;
    private Boolean verified;
    
    private Integer followersCount;
    private Integer followingCount;
    private Integer postsCount;
    private Boolean following;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}