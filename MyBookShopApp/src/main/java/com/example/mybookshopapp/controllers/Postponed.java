package com.example.mybookshopapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Postponed {
    @GetMapping("/postponed")
    public String recent(){
        return "/postponed";
    }
}
