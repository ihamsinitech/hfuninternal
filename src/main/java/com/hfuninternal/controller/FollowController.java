package com.hfuninternal.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hfuninternal.model.Follow;
import com.hfuninternal.repository.FollowRepository;
import com.hfuninternal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @PostMapping("/{id}/follow")
    public ResponseEntity<?> follow(@PathVariable Long id) {

        Follow follow = new Follow();
        follow.setFollower(userRepository.findById(1L).orElseThrow());
        follow.setFollowing(userRepository.findById(id).orElseThrow());

        followRepository.save(follow);

        return ResponseEntity.ok(Map.of("success", true));
    }

    @DeleteMapping("/{id}/unfollow")
    public ResponseEntity<?> unfollow(@PathVariable Long id) {

        followRepository.deleteByFollowerIdAndFollowingId(1L, id);

        return ResponseEntity.ok(Map.of("success", true));
    }
}
