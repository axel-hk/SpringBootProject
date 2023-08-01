package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.struct.book.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    List<BookEntity> findBookEntitiesByAuthorName(String name);

    @Query("from BookEntity ")
    List<BookEntity> customFindAllBooks();

    BookEntity findBookEntitiesById(Integer id);

    BookEntity findBookEntityBySlug(String slug);

    List<BookEntity> findBookEntitiesByIdIn(List<Integer> id);

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

    Page<BookEntity> findBookEntitiesByPubDateBetween(LocalDate start, LocalDate end, Pageable nextpage);

    @Query("select b from BookEntity b, Book2TagEntity bt, TagEntity t  " +
            "where t.id = :id and b.id = bt.bookId and t.id = bt.tagId")
    Page<BookEntity> findBookEntitiesByTagId(@Param("id") Integer id, Pageable nextpage);

    @Query("select b from BookEntity b, Book2GenreEntity bt, GenreEntity g  " +
            "where g.slug = :slug and b.id = bt.bookId and g.id = bt.genreId")
    Page<BookEntity> findBookEntitiesByGenreSlug(@Param("slug") String slug, Pageable nextpage);

    Page<BookEntity> findBookEntitiesByAuthor_Slug(@Param("slug") String slug, Pageable nextpage);
}

