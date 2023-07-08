package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.data.dto.BooksPageDto;
import com.example.mybookshopapp.services.BookService;
import com.example.mybookshopapp.services.GenreService;
import com.example.mybookshopapp.struct.book.BookEntity;
import com.example.mybookshopapp.struct.genre.GenreEntity;
import com.example.mybookshopapp.struct.tag.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GenresController {
    private final BookService bookService;

    private final GenreService genreService;
    @Autowired
    public GenresController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }
    @ModelAttribute("genresDto")
    public List<GenreEntity> recommendedBooks(){

        return genreService.getGenres();
    }

    @GetMapping("/genres/slug/{slug}")
    public String tags(@PathVariable(value = "slug") String slug, Model model){
        model.addAttribute("genreDto", genreService.getGenreEntityBySlug(slug));
        model.addAttribute("genreService",genreService);
        model.addAttribute("booksByGenre", bookService.getPageOfBooksByGenre(0,5, slug).getContent());
        return "/genres/slug";
    }

    @GetMapping("/genres/page/{slug}")
    @ResponseBody
    public BooksPageDto getBooksByGenrePage(
            @PathVariable("slug") String slug,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfBooksByGenre(offset, limit, slug).getContent());
    }

    @GetMapping("/genres")
    public String document(Model model){
        model.addAttribute("genreService",genreService);
        return "/genres/index";
    }
}
