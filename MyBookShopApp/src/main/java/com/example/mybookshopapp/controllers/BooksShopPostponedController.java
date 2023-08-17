package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.data.repositories.BookRepository;
import com.example.mybookshopapp.struct.book.BookEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
public class BooksShopPostponedController {
    private final BookRepository bookRepository;

    @Autowired
    public BooksShopPostponedController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @ModelAttribute("bookPostponed")
    public List<BookEntity> bookPostponed() {
        return new ArrayList<BookEntity>();
    }

    @GetMapping("/postponed")
    public String handlePostponedRequest(@CookieValue(name = "postponedContents", required = false) String postponedContents,
                                    Model model) {
        if (postponedContents == null || postponedContents.equals("")) {
            model.addAttribute("ispostponedEmpty", true);
        } else {
            model.addAttribute("ispostponedEmpty", false);
            postponedContents = postponedContents.startsWith("/") ? postponedContents.substring(1) : postponedContents;
            postponedContents = postponedContents.endsWith("/") ? postponedContents.substring(0, postponedContents.length() - 1) :
                    postponedContents;
            String[] cookieSlugs = postponedContents.split("/");
            List<BookEntity> booksFromCookieSlugs = bookRepository.findBookEntitiesBySlugIn(cookieSlugs);
            model.addAttribute("bookPostponed", booksFromCookieSlugs);
        }

        return "postponed";
    }
    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
    public String handleRemoveBookFromPostponedRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "postponedContents", required = false) String postponedContents, HttpServletResponse response, Model model) {
        if (postponedContents != null && !postponedContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(postponedContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("postponedContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("ispostponedEmpty", false);
        } else {
            model.addAttribute("ispostponedEmpty", true);
        }

        return "redirect:/books/postponed";
    }

    @PostMapping("/changeBookStatus/postponed/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug, @CookieValue(name = "postponedContents",
            required = false) String postponedContents, HttpServletResponse response, Model model) {

        if (postponedContents == null || postponedContents.equals("")) {
            Cookie cookie = new Cookie("postponedContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("ispostponedEmpty", false);
        } else if (!postponedContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(postponedContents).add(slug);
            Cookie cookie = new Cookie("postponedContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("ispostponedEmpty", false);
        }

        return "redirect:/books/" + slug;
    }
}
