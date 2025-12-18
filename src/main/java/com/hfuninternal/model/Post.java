package com.hfuninternal.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "posts")
@Data   // IMPORTANT: generates getters/setters
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String caption;

    @Column(name = "media_url", nullable = false)
    private String mediaUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
