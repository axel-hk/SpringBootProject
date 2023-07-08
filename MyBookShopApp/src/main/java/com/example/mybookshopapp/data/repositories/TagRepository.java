package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.struct.book.BookEntity;
import com.example.mybookshopapp.struct.tag.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository  extends JpaRepository<TagEntity, Integer> {
    @Query("select COUNT(b) from BookEntity b, Book2TagEntity bt, TagEntity t  " +
            "where t.name =:name and b.id = bt.bookId and t.id = bt.tagId")
    Integer getBookSizeByTagName(@Param("name") String name);

    TagEntity getTagEntityById(Integer id);
}
