package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.data.SearchWordDto;
import com.example.mybookshopapp.data.dto.BooksPageDto;
import com.example.mybookshopapp.exceptions.EmptySearchException;
import com.example.mybookshopapp.services.BookService;
import com.example.mybookshopapp.struct.book.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Search {

    private final BookService bookService;


    @Autowired
    public Search(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value ={ "/search","/search/","/search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                   Model model) throws EmptySearchException {
       if(searchWordDto != null) {
           model.addAttribute("searchWordDto", searchWordDto);
           model.addAttribute("searchResults", bookService.getPageOfSearchResultsBooks(searchWordDto.getExample(), 0, 5).getContent());

           return "/search/index";
       }
       else {
           throw new EmptySearchException("Поиск по null невозможен");
       }
    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto
    ){
        return new BooksPageDto(bookService.getPageOfSearchResultsBooks(searchWordDto.getExample(), offset, limit).getContent());
    }


}
