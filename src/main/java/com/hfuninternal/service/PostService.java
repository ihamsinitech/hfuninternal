package com.hfuninternal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hfuninternal.dto.FeedResponseDto;
import com.hfuninternal.dto.PostResponseDto;
import com.hfuninternal.dto.UserDto;
import com.hfuninternal.model.Post;
import com.hfuninternal.model.PostLike;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.FollowRepository;
import com.hfuninternal.repository.PostLikeRepository;
import com.hfuninternal.repository.PostRepository;
import com.hfuninternal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepo;
    private final PostLikeRepository likeRepo;
    private final FollowRepository followRepo;
    private final UserRepository userRepository; // Add this for fetching users

    public FeedResponseDto getFeed(int page, int limit, User currentUser) {

        Page<Post> posts = postRepo.findAllByOrderByCreatedAtDesc(
                PageRequest.of(page - 1, limit)
        );

        List<PostResponseDto> list = posts.getContent().stream().map(post -> mapPostToDto(post, currentUser))
                .toList();

        return new FeedResponseDto(true, list, posts.hasNext());
    }

    public void like(Long postId, Long userId) {
        if (!likeRepo.existsByUserIdAndPostId(userId, postId)) {
            likeRepo.save(new PostLike(null, userId, postId));
        }
    }

    public void unlike(Long postId, Long userId) {
        likeRepo.deleteByUserIdAndPostId(userId, postId);
    }

    public void share(Long postId) {
        // future enhancement
    }

    // ✅ New method: get posts by a specific user
    public List<PostResponseDto> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Post> posts = postRepo.findByUser(user);

        // Map posts to DTOs
        return posts.stream()
                .map(post -> mapPostToDto(post, user))
                .toList();
    }

    // Helper method to map Post -> PostResponseDto
    private PostResponseDto mapPostToDto(Post post, User currentUser) {
        PostResponseDto dto = new PostResponseDto();
        dto.set_id(post.getId());
        dto.setCaption(post.getCaption());
        dto.setMedia(post.getMediaUrl());
        dto.setCreatedAt(post.getCreatedAt().toString());

        dto.setLikesCount((int) likeRepo.countByPostId(post.getId()));
        dto.setLiked(
                likeRepo.existsByUserIdAndPostId(
                        currentUser.getId(),
                        post.getId()
                )
        );

        UserDto userDto = new UserDto();
        userDto.set_id(post.getUser().getId());
        userDto.setUsername(post.getUser().getUsername());
        userDto.setFullName(post.getUser().getFullName());
        userDto.setFollowing(
                followRepo.existsByFollower_IdAndFollowing_Id(
                        currentUser.getId(),
                        post.getUser().getId()
                )
        );

        dto.setUser(userDto);
        return dto;
    }
}
