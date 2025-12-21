package com.hfuninternal.repository;

import com.hfuninternal.model.Reel;
import com.hfuninternal.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelRepository extends JpaRepository<Reel, Long> {
    Page<Reel> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    
    @Query("SELECT r FROM Reel r WHERE r.user IN :followingUsers ORDER BY r.createdAt DESC")
    Page<Reel> findReelsFromFollowingUsers(@Param("followingUsers") List<User> followingUsers, Pageable pageable);
    
    @Query("SELECT r FROM Reel r ORDER BY r.views DESC, r.createdAt DESC")
    Page<Reel> findTrendingReels(Pageable pageable);
}