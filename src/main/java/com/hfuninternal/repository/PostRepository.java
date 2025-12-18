package com.hfuninternal.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hfuninternal.model.Post;
import com.hfuninternal.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Paginated feed sorted by creation date
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // User-specific posts
    List<Post> findByUser(User user);
}
