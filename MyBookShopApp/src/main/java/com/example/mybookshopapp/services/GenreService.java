package com.example.mybookshopapp.services;

import com.example.mybookshopapp.data.repositories.GenreRepository;
import com.example.mybookshopapp.struct.genre.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreEntity> getGenres() {
        return genreRepository.findAll();
    }

    public String getBookSizeByGenreSlug(String slug){
        return "(" + genreRepository.getBookSizeByGenreSlug(slug) +")";
    }

    public GenreEntity getGenreEntityBySlug(String slug){
        return genreRepository.getGenreEntityBySlug(slug);
    }

    public GenreEntity getGenreById(Integer id){
        return genreRepository.findGenreEntityById(id);
    }

    public List<GenreEntity> getChildrenById(Integer id){
        return genreRepository.findGenreEntitiesByParentId(id);
    }

    public Boolean isParent(Integer id){
        return !getChildrenById(id).isEmpty();
    }
}
