package com.foodblog.backend.service;

import java.util.List;
import java.util.Optional;

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
}