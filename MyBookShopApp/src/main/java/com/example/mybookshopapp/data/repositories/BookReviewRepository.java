package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.struct.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Integer> {
    List<BookReviewEntity> findBookReviewEntitiesByBookId(Integer bookId);
}
