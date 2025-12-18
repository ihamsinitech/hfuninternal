package com.hfuninternal.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hfuninternal.model.Follow;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.FollowRepository;
import com.hfuninternal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // Follow a user
    @PostMapping("/{id}/follow")
    public ResponseEntity<?> follow(@PathVariable Long id, Principal principal) {

        // Get logged-in user from Principal
        User currentUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        // Get the user to follow
        User following = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        // Check if already following
        boolean alreadyFollowing = followRepository
                .existsByFollower_IdAndFollowing_Id(currentUser.getId(), id);

        if (!alreadyFollowing) {
            Follow follow = new Follow();
            follow.setFollower(currentUser);
            follow.setFollowing(following);
            followRepository.save(follow);
        }

        return ResponseEntity.ok(Map.of("success", true));
    }

    // Unfollow a user
    @DeleteMapping("/{id}/unfollow")
    public ResponseEntity<?> unfollow(@PathVariable Long id, Principal principal) {

        User currentUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        followRepository.deleteByFollower_IdAndFollowing_Id(currentUser.getId(), id);

        return ResponseEntity.ok(Map.of("success", true));
    }

    // Optional: Get followers list
    @GetMapping("/{id}/followers")
    public ResponseEntity<?> getFollowers(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var followers = followRepository.findByFollowing(user)
                .stream()
                .map(Follow::getFollower)
                .toList();

        return ResponseEntity.ok(followers);
    }

    // Optional: Get following list
    @GetMapping("/{id}/following")
    public ResponseEntity<?> getFollowing(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var following = followRepository.findByFollower(user)
                .stream()
                .map(Follow::getFollowing)
                .toList();

        return ResponseEntity.ok(following);
    }
}
