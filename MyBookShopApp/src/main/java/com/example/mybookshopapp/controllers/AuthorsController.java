package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.data.dto.BooksPageDto;
import com.example.mybookshopapp.services.AuthorService;
import com.example.mybookshopapp.services.BookService;
import com.example.mybookshopapp.struct.author.AuthorEntity;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Tag(name = "Authors")
public class AuthorsController {
    private final AuthorService authorService;

    private final BookService bookService;

    @Autowired
    public AuthorsController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @ModelAttribute("authorData")
    public Map<Character, Map<String, String>> authorData(){
        return authorService.getAuthorData().stream()
                .collect(Collectors.groupingBy(
                        author -> author.getName().charAt(0),
                        Collectors.toMap(AuthorEntity::getName, AuthorEntity::getSlug)
                ));
    }


    @GetMapping("/authors")
    public String authorsPaige(){
        return "/authors/index";
    }

    @GetMapping("/authors/slug/{slug}")
    public String authors(@PathVariable(value = "slug") String slug, Model model){
        model.addAttribute("authorDto", authorService.getAuthorBySlug(slug));
        model.addAttribute("booksByAuthor", bookService.getPageOfBooksByAuthorSlug(0,5, slug).getContent());
        return "/authors/slug";
    }

    @GetMapping("/authors/page/{slug}")
    @ResponseBody
    public BooksPageDto getBooksByGenrePage(
            @PathVariable("slug") String slug,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfBooksByAuthorSlug(offset, limit, slug).getContent());
    }

    @GetMapping("/api/authors")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "OK")
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String apiAuthorsPaige(){
        return "/authors/index";
    }
}
