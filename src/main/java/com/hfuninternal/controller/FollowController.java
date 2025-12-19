package com.hfuninternal.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hfuninternal.dto.UserDto;
import com.hfuninternal.model.User;
import com.hfuninternal.service.UserService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class FollowController {

    private final UserService userService;

    // Follow a user
    @PostMapping("/{id}/follow-user")
    public ResponseEntity<UserDto> follow(@PathVariable Long id) {
        UserDto dto = userService.followUser(id);
        return ResponseEntity.ok(dto);
    }

    // Unfollow a user
    @DeleteMapping("/{id}/unfollow-user")
    public ResponseEntity<?> unfollow(@PathVariable Long id) {
        userService.unfollowUser(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    // Get followers
    @GetMapping("/{id}/followers")
    public ResponseEntity<List<UserDto>> getFollowers(@PathVariable Long id) {
        List<UserDto> dtos = userService.getFollowers(id);
        return ResponseEntity.ok(dtos);
    }

    // Get following
    @GetMapping("/{id}/following")
    public ResponseEntity<List<UserDto>> getFollowing(@PathVariable Long id) {
        List<UserDto> dtos = userService.getFollowing(id);
        return ResponseEntity.ok(dtos);
    }
}
