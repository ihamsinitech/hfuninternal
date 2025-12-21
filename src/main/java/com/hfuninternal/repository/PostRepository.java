package com.hfuninternal.repository;

import com.hfuninternal.model.Post;
import com.hfuninternal.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    
    @Query("SELECT p FROM Post p WHERE p.user IN :followingUsers ORDER BY p.createdAt DESC")
    Page<Post> findPostsFromFollowingUsers(@Param("followingUsers") List<User> followingUsers, Pageable pageable);
    
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.isArchived = false ORDER BY p.createdAt DESC")
    List<Post> findRecentPostsByUserId(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT COUNT(p) FROM Post p WHERE p.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);
}