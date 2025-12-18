package com.hfuninternal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hfuninternal.model.Block;
import com.hfuninternal.model.User;

public interface BlockRepository extends JpaRepository<Block, Long> {

    boolean existsByBlockedByAndBlockedUser(User blockedBy, User blockedUser);
}
