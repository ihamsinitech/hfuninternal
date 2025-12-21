package com.hfuninternal.repository;

import com.hfuninternal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> searchUsers(@Param("query") String query);
    
    @Query("SELECT u.followers FROM User u WHERE u.id = :userId")
    List<User> findFollowersByUserId(@Param("userId") Long userId);
    
    @Query("SELECT u.following FROM User u WHERE u.id = :userId")
    List<User> findFollowingByUserId(@Param("userId") Long userId);
}
// NO EXTRA CODE OR PROPERTIES AFTER THIS LINE!