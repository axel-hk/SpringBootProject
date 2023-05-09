package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.data.Author;
import com.example.mybookshopapp.data.AuthorService;
import com.example.mybookshopapp.data.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AuthorsController {
    @Autowired
    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    private final AuthorService authorService;

    @ModelAttribute("authorData")
    public Map<Character, List<String>> authorData(){
        return authorService.getAuthorData().stream()
                .collect(Collectors.groupingBy(
                        author -> author.getName().charAt(0),
                        Collectors.mapping(Author::getName, Collectors.toList())
                ));
    }
    @GetMapping("/authors")
    public String authorsPaige(){
        return "/authors/index";
    }
}
