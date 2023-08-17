package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.data.ApiResponse;
import com.example.mybookshopapp.exceptions.BookStoreApiWrongParameterException;
import com.example.mybookshopapp.services.BookService;
import com.example.mybookshopapp.struct.book.BookEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api")
public class BooksRestApiController {
    private final BookService bookService;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<BookEntity>> handleMissingServletRequetParameterException(
            MissingServletRequestParameterException exception){
        return new ResponseEntity<ApiResponse<BookEntity>>(new ApiResponse<BookEntity>(
                HttpStatus.BAD_REQUEST,
                "Missing requiered parameters",
                exception
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookStoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<BookEntity>> handleBookstoreApiWrongParameterException(
            BookStoreApiWrongParameterException exception){
        return new ResponseEntity<ApiResponse<BookEntity>>(new ApiResponse<BookEntity>(
                HttpStatus.BAD_REQUEST,
                "Bad parameter value",
                exception
        ), HttpStatus.BAD_REQUEST);
    }

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
    public ResponseEntity<ApiResponse<BookEntity>> booksByTitle(
            @RequestParam("title") String title
    ) throws BookStoreApiWrongParameterException {
        ApiResponse<BookEntity> response = new ApiResponse<>();
        List<BookEntity> data = bookService.getBooksByTitle(title);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setHttpStatus(HttpStatus.OK);
        response.setTimestamp(LocalDateTime.now());
        response.setDate(data);
        return ResponseEntity.ok(response);
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
