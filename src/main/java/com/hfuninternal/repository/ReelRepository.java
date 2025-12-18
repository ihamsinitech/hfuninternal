package com.hfuninternal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hfuninternal.model.Reel;
import com.hfuninternal.model.User;

public interface ReelRepository extends JpaRepository<Reel, Long> {

    // Paginated feed
    Page<Reel> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // Get all reels for a specific user
    List<Reel> findByUser(User user);
}
