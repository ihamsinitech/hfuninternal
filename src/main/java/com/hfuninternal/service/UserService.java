package com.hfuninternal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hfuninternal.dto.UserDto;
import com.hfuninternal.model.Follow;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.FollowRepository;
import com.hfuninternal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    // Get logged-in user
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            throw new RuntimeException("User not authenticated");
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof User) return (User) principal;

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + auth.getName()));
    }

    // Get any user's profile
    public User getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Follow a user
    @Transactional
    public UserDto followUser(Long targetUserId) {
        User currentUser = getCurrentUser();
        User targetUser = getUserProfile(targetUserId);

        boolean alreadyFollowing = followRepository.existsByFollower_IdAndFollowing_Id(currentUser.getId(), targetUser.getId());
        if (!alreadyFollowing) {
            Follow follow = new Follow();
            follow.setFollower(currentUser);
            follow.setFollowing(targetUser);
            followRepository.save(follow);
        }

        return convertToDto(currentUser, targetUser);
    }

    // Unfollow a user
    @Transactional
    public void unfollowUser(Long targetUserId) {
        User currentUser = getCurrentUser();
        User targetUser = getUserProfile(targetUserId);

        followRepository.deleteByFollower_IdAndFollowing_Id(currentUser.getId(), targetUser.getId());
    }

    // Get followers as DTOs
    @Transactional(readOnly = true)
    public List<UserDto> getFollowers(Long userId) {
        User currentUser = getCurrentUser();
        User targetUser = getUserProfile(userId);

        return followRepository.findByFollowing(targetUser).stream()
                .map(f -> convertToDto(currentUser, f.getFollower()))
                .collect(Collectors.toList());
    }

    // Get following as DTOs
    @Transactional(readOnly = true)
    public List<UserDto> getFollowing(Long userId) {
        User currentUser = getCurrentUser();
        User targetUser = getUserProfile(userId);

        return followRepository.findByFollower(targetUser).stream()
                .map(f -> convertToDto(currentUser, f.getFollowing()))
                .collect(Collectors.toList());
    }

    // Convert User → UserDto
    @Transactional(readOnly = true)
    public UserDto convertToDto(User currentUser, User targetUser) {
        UserDto dto = new UserDto();
        dto.set_id(targetUser.getId());
        dto.setUsername(targetUser.getUsername());
        dto.setFullName(targetUser.getFullName());
        dto.setBio(targetUser.getBio());
        dto.setProfilePictureUrl(targetUser.getProfilePictureUrl());

        boolean isFollowing = followRepository.existsByFollower_IdAndFollowing_Id(currentUser.getId(), targetUser.getId());
        dto.setFollowing(isFollowing);

        dto.setFollowerCount(followRepository.findByFollowing(targetUser).size());
        dto.setFollowingCount(followRepository.findByFollower(targetUser).size());

        return dto;
    }

    // Update profile
    @Transactional
    public UserDto updateProfile(User updated) {
        User currentUser = getCurrentUser();

        if (updated.getFullName() != null && !updated.getFullName().isBlank())
            currentUser.setFullName(updated.getFullName());
        if (updated.getBio() != null)
            currentUser.setBio(updated.getBio());
        if (updated.getProfilePictureUrl() != null)
            currentUser.setProfilePictureUrl(updated.getProfilePictureUrl());
        if (updated.getUsername() != null && !updated.getUsername().isBlank())
            currentUser.setUsername(updated.getUsername());

        userRepository.save(currentUser);

        return convertToDto(currentUser, currentUser);
    }

    // Update profile picture
    @Transactional
    public void updateProfilePicture(String url) {
        User currentUser = getCurrentUser();
        currentUser.setProfilePictureUrl(url);
        userRepository.save(currentUser);
    }
}
