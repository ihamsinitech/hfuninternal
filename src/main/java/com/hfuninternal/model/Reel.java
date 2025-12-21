package com.hfuninternal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String videoUrl;
    private String thumbnailUrl;
    private String caption;
    private String music;
    private Integer duration; // in seconds
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reel_likes",
        joinColumns = @JoinColumn(name = "reel_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likes = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "reel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "reel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Share> shares = new HashSet<>();
    
    @Builder.Default
    private Integer views = 0;
    
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
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