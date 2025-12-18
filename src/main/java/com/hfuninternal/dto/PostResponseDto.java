package com.hfuninternal.dto;

import lombok.Data;

@Data
public class PostResponseDto {

    private Long _id;
    private UserDto user;

    private String caption;
    private String media;

    private int likesCount;
    private int commentsCount = 0;
    private int sharesCount = 0;

    private boolean liked;   // ✅ FIXED (NO 'is')

    private String createdAt;
}
