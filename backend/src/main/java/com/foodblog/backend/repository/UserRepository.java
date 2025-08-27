package com.foodblog.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodblog.backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String keyword);
    @Query("SELECT u.followers FROM User u WHERE u.id = :userId")
    Page<User> findFollowersByUserId(@Param("userId") Long userId);

    @Query("SELECT u.following FROM User u WHERE u.id = :userId")
    Page<User> findFollowingByUserId(@Param("userId") Long userId);
}