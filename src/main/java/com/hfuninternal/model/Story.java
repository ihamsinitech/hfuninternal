package com.hfuninternal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 500)
    private String caption;
    
    private String mediaUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StoryType type; // IMAGE, VIDEO
    
    private Integer duration = 5; // Default 5 seconds for images
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AudienceType audience = AudienceType.EVERYONE; // EVERYONE, CLOSE_FRIENDS
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "story_views",
        joinColumns = @JoinColumn(name = "story_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> views = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "story", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<StoryReply> replies = new HashSet<>();
    
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Builder.Default
    private LocalDateTime expiresAt = LocalDateTime.now().plusHours(24); // 24-hour stories
    
    @Builder.Default
    private Boolean isArchived = false;
    
    // Helper methods
    public Integer getViewsCount() {
        return views != null ? views.size() : 0;
    }
    
    public Integer getRepliesCount() {
        return replies != null ? replies.size() : 0;
    }
    
    public Boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}

enum StoryType {
    IMAGE, VIDEO
}

enum AudienceType {
    EVERYONE, CLOSE_FRIENDS
}