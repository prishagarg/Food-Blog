package com.foodblog.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodblog.backend.model.Tag;
import com.foodblog.backend.repository.TagRepository;
import com.foodblog.backend.service.TagService;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag createTag(Tag tag) {
        // Normalize the tag name before saving
        if (tag.getName() != null) {
            tag.setName(tag.getName().toLowerCase().trim());
        }
        return tagRepository.save(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        return tagRepository.findById(id)
            .map(existingTag -> {
                if (tag.getName() != null) {
                    existingTag.setName(tag.getName()); // Entity will normalize via @PreUpdate
                }
                return tagRepository.save(existingTag);
            })
            .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }

    @Override
    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new RuntimeException("Tag not found with id: " + id);
        }
        tagRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> searchTagsByName(String name) {
        String normalizedName = name.toLowerCase().trim();
        return tagRepository.findByNameContainingIgnoreCase(normalizedName);
    }


    @Override
    public Tag findOrCreateByNormalizedName(String name) {
        String normalizedName = name.toLowerCase().trim();
        Optional<Tag> existingTag = tagRepository.findByName(normalizedName);
        
        if (existingTag.isPresent()) {
            return existingTag.get();
        } else {
            Tag newTag = new Tag();
            newTag.setName(normalizedName);
            return tagRepository.save(newTag);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        String normalizedName = name.toLowerCase().trim();
        return tagRepository.existsByName(normalizedName);
    }

    
}