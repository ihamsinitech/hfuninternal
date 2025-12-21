package com.hfuninternal.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private Long id;
    private String caption;
    private String mediaUrl;
    private String thumbnailUrl;
    private String type;
    private UserDTO user; // If UserDTO is in different package, add import
    private Integer likesCount;
    private Integer commentsCount;
    private Integer sharesCount;
    private Boolean liked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Data
    public static class CreateRequest {
        private String caption;
        private String mediaUrl;
        private String type;
    }
}