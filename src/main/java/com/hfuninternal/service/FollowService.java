package com.hfuninternal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hfuninternal.model.Follow;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.FollowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    // Check if already following
    public boolean isFollowing(Long followerId, Long followingId) {
        return followRepository.existsByFollower_IdAndFollowing_Id(followerId, followingId);
    }

    // Follow a user
    @Transactional
    public void follow(User follower, User following) {
        if (!isFollowing(follower.getId(), following.getId())) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);
            followRepository.save(follow);
        }
    }

    // Unfollow a user
    @Transactional
    public void unfollow(Long followerId, Long followingId) {
        followRepository.deleteByFollower_IdAndFollowing_Id(followerId, followingId);
    }
}
