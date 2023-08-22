package com.example.mybookshopapp.controllers;


import com.example.mybookshopapp.data.SearchWordDto;
import com.example.mybookshopapp.services.TagService;
import com.example.mybookshopapp.struct.book.BookEntity;
import com.example.mybookshopapp.services.BookService;
import com.example.mybookshopapp.struct.tag.TagEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toMap;

@Controller
public class MainPageController {

    private final BookService bookService;

    private final TagService tagService;
    @Autowired
    public MainPageController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }
    @ModelAttribute("recommendedBooks")
    public List<BookEntity> recommendedBooks(){

        return bookService.getPageOfRecommendedBooks(0,6).getContent();
    }

    @ModelAttribute("tags")
    public List<TagEntity> getTags(){

        return tagService.getTags();
    }
    @ModelAttribute("searchWordDto")
    private SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    private List<BookEntity> searchResults(){
        return new ArrayList<>();
    }

    @GetMapping("/")
    public String mainPage(Model model){
        model.addAttribute("tagService", tagService);
        return "index";
    }

    @GetMapping("/cart")
    public String cart(){
        return "cart";
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

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable(value = "id") Integer id, Model model){
        model.addAttribute("tagDto", tagService.getTag(id));
        model.addAttribute("booksByTag", bookService.getPageOfBooksByTag(0,5, id).getContent());
        return "tags/index";
    }


}
