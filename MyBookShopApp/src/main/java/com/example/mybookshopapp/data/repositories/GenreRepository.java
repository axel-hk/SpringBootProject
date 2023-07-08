package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.struct.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
    @Query("select COUNT(b) from BookEntity b, Book2GenreEntity bt, GenreEntity g  " +
            "where g.slug =:slug and b.id = bt.bookId and g.id = bt.genreId")
    Integer getBookSizeByGenreSlug(@Param("slug") String slug);

    GenreEntity getGenreEntityBySlug(String slug);

    List<GenreEntity> findGenreEntitiesByParentId(Integer parentId);

    GenreEntity findGenreEntityById(Integer id);
}