package com.hfuninternal.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.hfuninternal.model.Highlight;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.HighlightRepository;
import com.hfuninternal.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;
    private final UserRepository userRepository;

    // Get highlights for a user
    public List<Highlight> getHighlightsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return highlightRepository.findByUser(user);
    }

    // Create a highlight for a user
    public Highlight createHighlight(Long userId, String mediaUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Highlight highlight = new Highlight();
        highlight.setUser(user);
        highlight.setMediaUrl(mediaUrl);

        return highlightRepository.save(highlight);
    }
}
