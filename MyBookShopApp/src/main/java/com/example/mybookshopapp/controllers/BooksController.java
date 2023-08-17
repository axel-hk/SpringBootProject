package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.data.ResourceStorage;
import com.example.mybookshopapp.data.dto.BooksPageDto;
import com.example.mybookshopapp.services.BookService;
import com.example.mybookshopapp.services.BooksRatingAndPopularityService;
import com.example.mybookshopapp.services.TagService;
import com.example.mybookshopapp.struct.book.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    private final ResourceStorage storage;

    private final TagService tagService;

    private final BooksRatingAndPopularityService booksRatingAndPopularityService;
    @Autowired
    public BooksController(BookService bookService,
                           ResourceStorage storage, TagService tagService, BooksRatingAndPopularityService booksRatingAndPopularityService) {
        this.bookService = bookService;
        this.storage = storage;
        this.tagService = tagService;
        this.booksRatingAndPopularityService = booksRatingAndPopularityService;
    }

    @ModelAttribute("recentBooks")
    public List<BookEntity> recentBooks(){

        return bookService.getPageOfRecentBooks(LocalDate.now().minusYears(1L), LocalDate.now(),0,5).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<BookEntity> popularBooks(){

        return booksRatingAndPopularityService.getPageOfPopBook(0,5);
    }

    @ModelAttribute("booksByTag")
    public List<BookEntity> booksByTag()
    {
        return bookService.getPageOfBooksByTag(0,5, 1).getContent();
    }

    @GetMapping("/recent")
    public String recent(){
        return "/books/recent";
    }

    @GetMapping("/popular")
    public String popular(){
        return "/books/popular";
    }

    @GetMapping("/recommended")
    @ResponseBody
    public BooksPageDto getRecommendedBooks(@RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset,limit).getContent());
    }
    @GetMapping("/recent/page")
    @ResponseBody
    public BooksPageDto getRecentBooksPage(@RequestParam ("from") String from,
            @RequestParam("to") String to,
            @RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return new BooksPageDto(bookService.getPageOfRecentBooks(LocalDate.parse(from, df),LocalDate.parse(to, df), offset,limit).getContent());
    }

    @GetMapping("/popular/page")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit){
        return new BooksPageDto(booksRatingAndPopularityService.getPageOfPopBook(offset, limit));
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model){
        BookEntity book = bookService.getBookBySlug(slug);
        Long rating = bookService.getBookRatingBySlug(slug);
        Map<Integer, Long> starsCount = bookService.getStarsCountBySlug(slug);
        Long usersCount = bookService.getBookCountRatingBySlug(slug);
        model.addAttribute("slugBook", book);
        model.addAttribute("rating", rating);
        model.addAttribute("starsCount", starsCount);
        model.addAttribute("usersCount", usersCount);
        model.addAttribute("bookService", bookService);
        model.addAttribute("bookReviews", bookService.findBookReviewsByBookId(book.getId()));

        //model.addAttribute("tags", tagService.getTagsByBookSlug(slug));
        return "/books/slug";
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug){
        try{
            String savePath = storage.saveNewBookImage(file, slug);
            BookEntity book = bookService.getBookBySlug(slug);
            book.setImage(savePath);
            bookService.saveBook(book);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
        return ("redirect:/books/"+slug);
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {
        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file size: " + data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));

    }

    @PostMapping("/rateBook")
    public ResponseEntity<String> postRating(@RequestParam("bookId") String slug,
                                               @RequestParam("value") String value){
        BookEntity bookEntity = bookService.getBookBySlug(slug);
        bookService.updateRating(bookEntity.getId(), Integer.parseInt(value));
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
