package com.hfuninternal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String confirmPassword;

    @Column(nullable = false)
    private String signup; // registration date as string

    @Column(nullable = false)
    private String fullName;

    private String profilePictureUrl;

    private String bio;

    // Example of bi-directional relationship: posts
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore // prevents infinite recursion when serializing
    private List<Post> posts;

    // Followers / Following (if using Follow entity)
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Follow> following;

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Follow> followers;

    // Blocked users (if using Block entity)
    @OneToMany(mappedBy = "blockedBy", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Block> blockedUsers;
}
