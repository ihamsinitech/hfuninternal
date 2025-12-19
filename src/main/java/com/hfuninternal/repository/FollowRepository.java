package com.hfuninternal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.hfuninternal.model.Follow;
import com.hfuninternal.model.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // Check if a user is following another
    boolean existsByFollower_IdAndFollowing_Id(Long followerId, Long followingId);

    // Unfollow
    @Transactional
    void deleteByFollower_IdAndFollowing_Id(Long followerId, Long followingId);

    // Users who follow this user
    List<Follow> findByFollowing(User following);

    // Users this user is following
    List<Follow> findByFollower(User follower);
}
