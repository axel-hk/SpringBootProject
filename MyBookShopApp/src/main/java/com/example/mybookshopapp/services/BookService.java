package com.example.mybookshopapp.services;

import com.example.mybookshopapp.data.repositories.BookRepository;
import com.example.mybookshopapp.struct.author.AuthorEntity;
import com.example.mybookshopapp.struct.book.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;


@Service
public class BookService {

    private JdbcTemplate jdbcTemplate;

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository, JdbcTemplate jdbcTemplate) {
        this.bookRepository = bookRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookEntity> getBookData(){
        return bookRepository.findAll();
        /*List<Book> books = jdbcTemplate.query(("SELECT * " +
                " FROM books "), (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(getAuthorByAuthorId(rs.getInt("author_id")));
            book.setTitle(rs.getString("title"));
            book.setPriceOld(rs.getString("price_old"));
            book.setPrice(rs.getString("price"));

            return book;
        } );

        return new ArrayList<>(books);*/
    }

    private AuthorEntity getAuthorByAuthorId(int author_id) {
        List<AuthorEntity> authorEntities = jdbcTemplate.query(
                ("SELECT * FROM authors WHERE authors.id= " + author_id),
                (ResultSet rs, int row) -> {
                    AuthorEntity Author = new AuthorEntity();
                    Author.setId(rs.getInt("id"));
                    Author.setName(rs.getString("name"));
                    return Author;
                });
        return authorEntities.get(0);
    }

    //new book service methods

    public List<BookEntity> getBooksByAuthor(String authorName){
        return bookRepository.findBookEntitiesByAuthorNameContaining(authorName);
    }

    public List<BookEntity> getBooksByTitle(String title){
        return bookRepository.findBookEntitiesByTitleContaining(title);
    }

    public List<BookEntity> getBooksWithPriceBetween(Integer minPrice, Integer maxPrice){
        return bookRepository.findBookEntitiesByPriceBetween(minPrice, maxPrice);
    }

    public List<BookEntity> getBooksWithPrice(Integer price){
        return bookRepository.findBookEntitiesByPriceIs(price);
    }

    public List<BookEntity> getBooksWithMaxDiscount(){
        return bookRepository.getBooksWithMaxDiscount();
    }
    public List<BookEntity> getBestsellers(){
        return bookRepository.getBestsellers();
    }

    public Page<BookEntity> getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAll(nextPage);
    }

    public Page<BookEntity> getPageOfSearchResultsBooks(String searchWord, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookEntitiesByTitleContaining(searchWord, nextPage);
    }
}
