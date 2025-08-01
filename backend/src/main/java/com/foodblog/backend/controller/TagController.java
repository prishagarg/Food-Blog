package com.foodblog.backend.controller;

import com.foodblog.backend.model.Tag;
import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.service.TagService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "*") // Optional: adjust for your frontend origin
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Create a tag
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.createTag(tag);
        return ResponseEntity.ok(createdTag);
    }

    // Get all tags
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    // Get tag by ID
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        Tag tag = tagService.getTagById(id);
        if (tag == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tag);
    }

    // Update tag
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody Tag tag) {
        Tag updated = tagService.updateTag(id, tag);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // Delete tag
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    // Search tags by name (e.g., partial match)
    @GetMapping("/search")
    public ResponseEntity<List<Tag>> searchTags(@RequestParam String name) {
        return ResponseEntity.ok(tagService.searchTagsByName(name));
    }

    // Get recipes by tag name
    @GetMapping("/{name}/recipes")
    public ResponseEntity<List<Recipe>> getRecipesByTag(@PathVariable String name) {
        return ResponseEntity.ok(tagService.getRecipesByTagName(name));
    }
}
