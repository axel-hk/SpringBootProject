package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.struct.book.BookEntity;
import com.example.mybookshopapp.struct.tag.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository  extends JpaRepository<TagEntity, Integer> {
    @Query("select COUNT(b) from BookEntity b, Book2TagEntity bt, TagEntity t  " +
            "where t.name =:name and b.id = bt.bookId and t.id = bt.tagId")
    Integer getBookSizeByTagName(@Param("name") String name);

    TagEntity getTagEntityById(Integer id);

    @Query("select t.name from BookEntity b, Book2TagEntity bt, TagEntity t  " +
            "where b.slug =:bookSlug and b.id = bt.bookId and t.id = bt.tagId")
    List<String> findTagsByBookSlug (@Param("bookSlug") String bookSlug);
}
