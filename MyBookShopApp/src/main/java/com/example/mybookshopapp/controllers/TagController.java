package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.data.dto.BooksPageDto;
import com.example.mybookshopapp.services.BookService;
import com.example.mybookshopapp.services.TagService;
import com.example.mybookshopapp.struct.book.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tags")
public class TagController {

    private final BookService bookService;

    private final TagService tagService;
    @Autowired
    public TagController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }


    @GetMapping("/page/{id}")
    @ResponseBody
    public BooksPageDto getBooksByTagPage(
            @PathVariable("id") Integer id,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfBooksByTag(offset, limit, id).getContent());
    }
}
