package com.hfuninternal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hfuninternal.model.Reel;
import com.hfuninternal.repository.ReelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReelService {

    private final ReelRepository reelRepository;

    public Page<Reel> feed(int page, int limit) {
        return reelRepository.findAllByOrderByCreatedAtDesc(
                PageRequest.of(page, limit)
        );
    }

    public Reel create(Reel reel) {
        return reelRepository.save(reel);
    }

    public void like(Long id) {
        Reel reel = reelRepository.findById(id).orElseThrow();
        reel.setLikes(reel.getLikes() + 1);
        reelRepository.save(reel);
    }

    public void unlike(Long id) {
        Reel reel = reelRepository.findById(id).orElseThrow();
        reel.setLikes(Math.max(0, reel.getLikes() - 1));
        reelRepository.save(reel);
    }
}
