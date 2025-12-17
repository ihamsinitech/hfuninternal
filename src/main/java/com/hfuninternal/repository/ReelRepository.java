package com.hfuninternal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hfuninternal.model.Reel;

public interface ReelRepository extends JpaRepository<Reel, Long> {
    Page<Reel> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
