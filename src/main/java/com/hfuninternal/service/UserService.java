package com.hfuninternal.service;

import com.hfuninternal.dto.UserDTO;
import com.hfuninternal.exception.ResourceNotFoundException;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    public User getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
    
    public UserDTO convertToDto(User currentUser, User targetUser) {
        if (targetUser == null) {
            throw new ResourceNotFoundException("User not found");
        }
        
        UserDTO dto = new UserDTO();
        dto.setId(targetUser.getId());
        dto.setUsername(targetUser.getUsername());
        dto.setEmail(targetUser.getEmail());
        
        // Combine firstName and lastName for fullName
        String fullName = "";
        if (targetUser.getFirstName() != null) {
            fullName = targetUser.getFirstName();
        }
        if (targetUser.getLastName() != null && !targetUser.getLastName().isEmpty()) {
            if (!fullName.isEmpty()) {
                fullName += " ";
            }
            fullName += targetUser.getLastName();
        }
        dto.setFullName(fullName);
        
        // Set optional fields with null safety
        dto.setBio(targetUser.getBio() != null ? targetUser.getBio() : "");
        dto.setProfilePictureUrl(targetUser.getProfilePicture() != null ? targetUser.getProfilePicture() : "");
        
        // Set default values
        dto.setWebsite("");
        dto.setGender("");
        
        // FIX: Comment out problematic lines for now
        // dto.setPrivate(false);     // Comment this out
        // dto.setVerified(false);    // Comment this out  
        // dto.setFollowing(false);   // Comment this out
        
        // Initialize counts to 0
        dto.setFollowersCount(0);
        dto.setFollowingCount(0);
        dto.setPostsCount(0);
        
        // Set timestamps
        dto.setCreatedAt(targetUser.getCreatedAt());
        dto.setUpdatedAt(targetUser.getUpdatedAt());
        
        return dto;
    }
    
    public UserDTO updateProfile(User updatedUser) {
        User currentUser = getCurrentUser();
        
        if (updatedUser.getFirstName() != null) {
            currentUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            currentUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getBio() != null) {
            currentUser.setBio(updatedUser.getBio());
        }
        if (updatedUser.getProfilePicture() != null) {
            currentUser.setProfilePicture(updatedUser.getProfilePicture());
        }
        
        User savedUser = userRepository.save(currentUser);
        return convertToDto(savedUser, savedUser);
    }
    
    public void updateProfilePicture(String imageUrl) {
        User currentUser = getCurrentUser();
        currentUser.setProfilePicture(imageUrl);
        userRepository.save(currentUser);
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
    
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> convertToDto(user, user))
                .collect(Collectors.toList());
    }
    
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }
    
    public List<UserDTO> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllUsers();
        }
        
        return userRepository.searchUsers(query).stream()
                .map(user -> convertToDto(user, user))
                .collect(Collectors.toList());
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public long getUserCount() {
        return userRepository.count();
    }
}