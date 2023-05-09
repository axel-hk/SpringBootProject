package com.example.mybookshopapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Search {

    @GetMapping("/search")
    public String recent(){
        return "/search/index";
    }
}
