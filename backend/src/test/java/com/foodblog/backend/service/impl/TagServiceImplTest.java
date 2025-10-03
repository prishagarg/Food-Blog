package com.foodblog.backend.service.impl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.foodblog.backend.model.Tag;
import com.foodblog.backend.repository.TagRepository;

class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTag_shouldSaveAndReturnTag() {
        Tag tag = new Tag();
        tag.setName("spicy");

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        Tag saved = tagService.createTag(tag);

        assertEquals("spicy", saved.getName());
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void getTagById_shouldReturnTag() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("vegan");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        Optional<Tag> found = tagService.getTagById(1L);

        assertNotNull(found);
        assertEquals("vegan", found.get().getName());
    }

    @Test
    void deleteTag_shouldCallDeleteById() {
        Long tagId = 2L;
        Tag tag = new Tag();
        tag.setId(tagId);
        tag.setName("dessert");
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));
        tagService.deleteTag(tagId);
        verify(tagRepository).deleteById(tagId);
    }
}
