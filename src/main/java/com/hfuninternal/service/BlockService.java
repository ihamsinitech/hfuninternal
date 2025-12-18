package com.hfuninternal.service;

import java.security.Principal;

import org.springframework.stereotype.Service;

import com.hfuninternal.model.Block;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.BlockRepository;
import com.hfuninternal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;
    private final UserRepository userRepository;

    public void blockUser(Long userId, Principal principal) {

        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        User blockedBy = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User blockedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!blockRepository.existsByBlockedByAndBlockedUser(blockedBy, blockedUser)) {
            Block block = new Block();
            block.setBlockedBy(blockedBy);
            block.setBlockedUser(blockedUser);
            blockRepository.save(block);
        }
    }
}
