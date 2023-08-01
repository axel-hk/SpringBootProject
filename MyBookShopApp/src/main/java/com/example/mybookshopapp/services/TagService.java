package com.example.mybookshopapp.services;

import com.example.mybookshopapp.data.repositories.TagRepository;
import com.example.mybookshopapp.struct.tag.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public  List<TagEntity> getTags() {
       return tagRepository.findAll();
    }

    public String getTagSize(String name) {
        int size = tagRepository.getBookSizeByTagName(name);
        if (size > 0 && size < 5) {
            return "Tag_xs";
        } else if (size < 10) {
            return "Tag_sm";
        } else if (size < 100) {
            return "Tag";
        } else if (size < 500) {
            return "Tag_md";
        } else if (size > 0) return "Tag_lg";
        return "Tag";
    }

    public TagEntity getTag(int id) {
       return tagRepository.getTagEntityById(id);
    }

    public List<String> getTagsByBookSlug(String slug) {
        return tagRepository.findTagsByBookSlug(slug);
    }
}
