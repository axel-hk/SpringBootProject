package com.example.mybookshopapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BooksController {

    @GetMapping("/recent")
    public String recent(){
        return "/books/recent";
    }

    @GetMapping("/popular")
    public String popular(){
        return "/books/popular";
    }
}
