package com.hfuninternal.repository;

import com.hfuninternal.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
