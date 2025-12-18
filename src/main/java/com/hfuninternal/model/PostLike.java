package com.hfuninternal.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "post_likes",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"user_id", "post_id"}
    ),
    indexes = {
        @Index(name = "idx_post_like_user", columnList = "user_id"),
        @Index(name = "idx_post_like_post", columnList = "post_id")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "post_id", nullable = false)
    private Long postId;
}
