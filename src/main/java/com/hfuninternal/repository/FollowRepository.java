package com.hfuninternal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.hfuninternal.model.Follow;
import com.hfuninternal.model.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // follow check
    boolean existsByFollower_IdAndFollowing_Id(Long followerId, Long followingId);

    // unfollow
    @Transactional
    void deleteByFollower_IdAndFollowing_Id(Long followerId, Long followingId);

    // ✅ users who follow this user
    List<Follow> findByFollowing(User following);

    // ✅ users this user is following
    List<Follow> findByFollower(User follower);
}
