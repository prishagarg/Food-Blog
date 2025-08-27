package com.foodblog.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodblog.backend.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
    Page<Tag> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
}