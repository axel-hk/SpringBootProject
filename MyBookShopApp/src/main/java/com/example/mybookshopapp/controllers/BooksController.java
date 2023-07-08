package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.data.dto.BooksPageDto;
import com.example.mybookshopapp.services.BookService;
import com.example.mybookshopapp.services.BooksRatingAndPopularityService;
import com.example.mybookshopapp.struct.book.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    private final BooksRatingAndPopularityService booksRatingAndPopularityService;
    @Autowired
    public BooksController(BookService bookService,
                           BooksRatingAndPopularityService booksRatingAndPopularityService) {
        this.bookService = bookService;
        this.booksRatingAndPopularityService = booksRatingAndPopularityService;
    }

    @ModelAttribute("recentBooks")
    public List<BookEntity> recentBooks(){

        return bookService.getPageOfRecentBooks(LocalDate.now().minusYears(1L), LocalDate.now(),0,5).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<BookEntity> popularBooks(){

        return booksRatingAndPopularityService.getPageOfPopBook(0,5);
    }

    @ModelAttribute("booksByTag")
    public List<BookEntity> booksByTag()
    {
        return bookService.getPageOfBooksByTag(0,5, 1).getContent();
    }

    @GetMapping("/recent")
    public String recent(){
        return "/books/recent";
    }

    @GetMapping("/popular")
    public String popular(){
        return "/books/popular";
    }

    @GetMapping("/recent/page")
    @ResponseBody
    public BooksPageDto getRecentBooksPage(@RequestParam ("from") String from,
            @RequestParam("to") String to,
            @RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return new BooksPageDto(bookService.getPageOfRecentBooks(LocalDate.parse(from, df),LocalDate.parse(to, df), offset,limit).getContent());
    }

    @GetMapping("/popular/page")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit){
        return new BooksPageDto(booksRatingAndPopularityService.getPageOfPopBook(offset, limit));
    }

}
