package com.hfuninternal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hfuninternal.model.Reel;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.ReelRepository;
import com.hfuninternal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReelService {

    private final ReelRepository reelRepository;
    private final UserRepository userRepository;

    // Get paginated feed
    public Page<Reel> feed(int page, int limit) {
        return reelRepository.findAllByOrderByCreatedAtDesc(
                PageRequest.of(page, limit)
        );
    }

    // Create a new reel
    public Reel create(Reel reel) {
        return reelRepository.save(reel);
    }

    // Like a reel
    public void like(Long id) {
        Reel reel = reelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reel not found"));
        reel.setLikes(reel.getLikes() + 1);
        reelRepository.save(reel);
    }

    // Unlike a reel
    public void unlike(Long id) {
        Reel reel = reelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reel not found"));
        reel.setLikes(Math.max(0, reel.getLikes() - 1));
        reelRepository.save(reel);
    }

    // ✅ Get all reels for a specific user
    public List<Reel> getReelsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reelRepository.findByUser(user);
    }
}
