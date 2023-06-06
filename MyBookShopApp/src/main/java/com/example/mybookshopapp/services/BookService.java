package com.example.mybookshopapp.services;


import com.example.mybookshopapp.data.dao.Author;
import com.example.mybookshopapp.data.dao.Book;
import com.example.mybookshopapp.data.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Book> getBookData(){
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

    private Author getAuthorByAuthorId(int author_id) {
        List<Author> authorEntities = jdbcTemplate.query(
                ("SELECT * FROM authors WHERE authors.id= " + author_id),
                (ResultSet rs, int row) -> {
                    Author Author = new Author();
                    Author.setId(rs.getInt("id"));
                    Author.setName(rs.getString("name"));
                    return Author;
                });
        return authorEntities.get(0);
    }
}
