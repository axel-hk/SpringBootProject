package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.data.Author;
import com.example.mybookshopapp.data.AuthorService;
import com.example.mybookshopapp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Controller
@RequestMapping("/bookshop")
public class MainPageController {

    private final BookService bookService;

    private final AuthorService authorService;

    @Autowired
    public MainPageController(BookService bookService,
                              AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/main")
    public String mainPage(Model model){
        model.addAttribute("bookData", bookService.getBookData());
        model.addAttribute("authorData", authorService.getAuthorData());
        return "index";
    }
    @GetMapping("/genres")
    public String genrePaige(){
        return "/genres/index";
    }

    @GetMapping("/authors")
    public String authorsPaige(Model model){
        Map<Character, List<String>> authorData = authorService.getAuthorData().stream()
                .collect(Collectors.groupingBy(
                        author -> author.getName().charAt(0),
                        Collectors.mapping(Author::getName, Collectors.toList())
                ));
        model.addAttribute("authorData", authorData);
        return "/authors/index";
    }
}
