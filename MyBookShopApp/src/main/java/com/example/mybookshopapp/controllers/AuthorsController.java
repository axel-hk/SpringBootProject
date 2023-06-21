package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.services.AuthorService;
import com.example.mybookshopapp.struct.author.AuthorEntity;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Tag(name = "Authors")
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
                        Collectors.mapping(AuthorEntity::getName, Collectors.toList())
                ));
    }
    @GetMapping("/authors")
    public String authorsPaige(){
        return "/authors/index";
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
