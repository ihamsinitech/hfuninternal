package com.hfuninternal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 2200)
    private String caption;
    
    private String mediaUrl;
    private String thumbnailUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PostType type; // IMAGE, VIDEO, CAROUSEL
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_likes",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likes = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Share> shares = new HashSet<>();
    
    @Builder.Default
    private Boolean isArchived = false;
    
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Helper methods
    public Integer getLikesCount() {
        return likes != null ? likes.size() : 0;
    }
    
    public Integer getCommentsCount() {
        return comments != null ? comments.size() : 0;
    }
    
    public Integer getSharesCount() {
        return shares != null ? shares.size() : 0;
    }
}

enum PostType {
    IMAGE, VIDEO, CAROUSEL
}