package com.hfuninternal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hfuninternal.model.Highlight;
import com.hfuninternal.model.User;

public interface HighlightRepository extends JpaRepository<Highlight, Long> {
    List<Highlight> findByUser(User user); // fetch all highlights for a user
}
