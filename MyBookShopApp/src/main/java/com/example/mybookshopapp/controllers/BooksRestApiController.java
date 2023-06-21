package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.services.BookService;
import com.example.mybookshopapp.struct.book.BookEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class BooksRestApiController {
    private final BookService bookService;


    @Autowired
    public BooksRestApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/by-author")
    @Operation(description = "operation to get book list of bookshop by passed name of author")
    public ResponseEntity<List<BookEntity>> booksByAuthor(
            @RequestParam("author") String authorName
    ){
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorName));
    }

    @GetMapping("/books/by-title")
    @Operation(description = "operation to get book list of bookshop by title")
    public ResponseEntity<List<BookEntity>> booksByTitle(
            @RequestParam("title") String title
    ){
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/books/by-price-range")
    @Operation(description = "operation to get book list of bookshop by price range")
    public ResponseEntity<List<BookEntity>> booksByPriceRange(
            @RequestParam("minPrice") Integer minPrice,
            @RequestParam("maxPrice") Integer maxPrice
    ){
        return ResponseEntity.ok(bookService.getBooksWithPriceBetween(minPrice, maxPrice));
    }

    @GetMapping("/books/with-max-dicsount")
    @Operation(description = "operation to get book list of bookshop with max discount")
    public ResponseEntity<List<BookEntity>> maxDiscount(
    ){
        return ResponseEntity.ok(bookService.getBooksWithMaxDiscount());
    }

    @GetMapping("/books/bestsellers")
    @Operation(description = "operation to get book list of bestsellers")
    public ResponseEntity<List<BookEntity>> bestsellers(
    ){
        return ResponseEntity.ok(bookService.getBestsellers());
    }
}
