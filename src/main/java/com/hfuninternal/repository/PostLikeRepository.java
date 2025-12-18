package com.hfuninternal.repository;

import com.hfuninternal.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    long countByPostId(Long postId);
    void deleteByUserIdAndPostId(Long userId, Long postId);
}
