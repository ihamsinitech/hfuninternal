package com.hfuninternal.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
    name = "blocks",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"blocked_by", "blocked_user"}
    )
)
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_by", nullable = false)
    private User blockedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_user", nullable = false)
    private User blockedUser;
}
