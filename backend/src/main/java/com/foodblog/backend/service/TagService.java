package com.foodblog.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.model.Tag;

public interface TagService{
    Tag createTag(Tag tag);
    List<Tag> getAllTags();
    Optional<Tag> getTagById(Long id);
    Tag updateTag(Long id, Tag tag);
    void deleteTag(Long id);
    List<Tag> searchTagsByName(String name);
    Tag findOrCreateByNormalizedName(String name);
    boolean existsByName(String name);
    Page<Recipe> getRecipesByTag(String name, Pageable pageable);
}