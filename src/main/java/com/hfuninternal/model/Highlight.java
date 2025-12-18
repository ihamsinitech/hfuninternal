package com.hfuninternal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "highlights")
public class Highlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User who owns this highlight
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Media URL (image or video)
    @Column(nullable = false)
    private String mediaUrl;

    // Timestamp when highlight was created
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Optional expiry timestamp (for stories that disappear)
    private LocalDateTime expiresAt;
}
