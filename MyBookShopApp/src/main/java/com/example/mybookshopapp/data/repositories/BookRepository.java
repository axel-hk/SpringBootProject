package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.struct.book.BookEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    List<BookEntity> findBookEntitiesByAuthorName(String name);

    @Query("from BookEntity ")
    List<BookEntity> customFindAllBooks();

    //NEW BOOK REST REPOSISITORY COMMANDS

    List<BookEntity> findBookEntitiesByAuthorNameContaining(String authorFirstName);

    List<BookEntity> findBookEntitiesByTitleContaining(String bookTitle);

    List<BookEntity> findBookEntitiesByPriceBetween(Integer min, Integer max);

    List<BookEntity> findBookEntitiesByPriceIs(Integer price);

    @Query("from BookEntity where isBestseller=1")
    List<BookEntity> getBestsellers();

    @Query(value = "select * from books where discount = (select max(discount) from books)", nativeQuery = true)
    List<BookEntity> getBooksWithMaxDiscount();

    Page<BookEntity> findBookEntitiesByTitleContaining(String bookTitle, Pageable nextpage);
}
