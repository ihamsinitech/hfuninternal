package com.hfuninternal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hfuninternal.model.Post;
import com.hfuninternal.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> getFeed(int page, int limit) {
        return postRepository.findAllByOrderByCreatedAtDesc(
                PageRequest.of(page, limit)
        );
    }

    // ✅ ADD THIS METHOD
    public Post create(Post post) {
        return postRepository.save(post);
    }

    public void like(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }

    public void unlike(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setLikes(Math.max(0, post.getLikes() - 1));
        postRepository.save(post);
    }

    public void share(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setShares(post.getShares() + 1);
        postRepository.save(post);
    }
}
