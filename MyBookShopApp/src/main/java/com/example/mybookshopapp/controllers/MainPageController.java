package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.data.dao.Book;
import com.example.mybookshopapp.struct.book.BookEntity;
import com.example.mybookshopapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

import static java.util.stream.Collectors.toMap;

@Controller
public class MainPageController {

    private final BookService bookService;
    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }
    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks(){

        return bookService.getBookData();
    }
    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

    @GetMapping("/cart")
    public String cart(){
        return "cart";
    }

    @GetMapping("/signin")
    public String signin(){
        return "signin";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/faq")
    public String faq(){
        return "faq";
    }

    @GetMapping("/contacts")
    public String contacts(){
        return "contacts";
    }


}
