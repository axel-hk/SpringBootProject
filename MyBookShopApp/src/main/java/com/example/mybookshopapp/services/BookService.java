package com.example.mybookshopapp.services;

import com.example.mybookshopapp.data.repositories.BookRepository;
import com.example.mybookshopapp.data.repositories.BookReviewRepository;
import com.example.mybookshopapp.exceptions.BookStoreApiWrongParameterException;
import com.example.mybookshopapp.struct.author.AuthorEntity;
import com.example.mybookshopapp.struct.book.BookEntity;
import com.example.mybookshopapp.struct.book.review.BookReviewEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BookService {

    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    private BookRepository bookRepository;
    private BookReviewRepository bookReviewRepository;



    @Autowired
    public BookService(BookRepository bookRepository, JdbcTemplate jdbcTemplate, BookReviewRepository bookReviewRepository) {
        this.bookRepository = bookRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.bookReviewRepository = bookReviewRepository;
    }

    public List<BookEntity> getBookData(){
        return bookRepository.findAll();
        /*List<Book> books = jdbcTemplate.query(("SELECT * " +
                " FROM books "), (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(getAuthorByAuthorId(rs.getInt("author_id")));
            book.setTitle(rs.getString("title"));
            book.setPriceOld(rs.getString("price_old"));
            book.setPrice(rs.getString("price"));

            return book;
        } );

        return new ArrayList<>(books);*/
    }

    private AuthorEntity getAuthorByAuthorId(int author_id) {
        List<AuthorEntity> authorEntities = jdbcTemplate.query(
                ("SELECT * FROM authors WHERE authors.id= " + author_id),
                (ResultSet rs, int row) -> {
                    AuthorEntity Author = new AuthorEntity();
                    Author.setId(rs.getInt("id"));
                    Author.setName(rs.getString("name"));
                    return Author;
                });
        return authorEntities.get(0);
    }

    public void saveBook(BookEntity book){
        bookRepository.save(book);
    }

    //new book service methods
    public BookEntity getBookBySlug(String slug) {
        return bookRepository.findBookEntityBySlug(slug);
    }
    public List<BookEntity> getBooksByAuthor(String authorName){
        return bookRepository.findBookEntitiesByAuthorNameContaining(authorName);
    }

    public List<BookEntity> getBooksByTitle(String title) throws BookStoreApiWrongParameterException {
        if (title.length() <= 1) {
            throw new BookStoreApiWrongParameterException("Wrong values passed to parameter title");
        } else {
            List<BookEntity> data = bookRepository.findBookEntitiesByTitleContaining(title);

            if (data.size() > 0) {
                return data;
            } else {
                throw new BookStoreApiWrongParameterException("No data found with specified title");
            }
        }
    }

    public List<BookEntity> getBooksWithPriceBetween(Integer minPrice, Integer maxPrice){
        return bookRepository.findBookEntitiesByPriceBetween(minPrice, maxPrice);
    }

    public List<BookEntity> getBooksWithPrice(Integer price){
        return bookRepository.findBookEntitiesByPriceIs(price);
    }

    public List<BookEntity> getBooksWithMaxDiscount(){
        return bookRepository.getBooksWithMaxDiscount();
    }
    public List<BookEntity> getBestsellers(){
        return bookRepository.getBestsellers();
    }

    public Page<BookEntity> getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAll(nextPage);
    }

    public Page<BookEntity> getPageOfSearchResultsBooks(String searchWord, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookEntitiesByTitleContaining(searchWord, nextPage);
    }

    public Page<BookEntity> getPageOfRecentBooks(
            LocalDate start, LocalDate end, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookEntitiesByPubDateBetween(start, end, nextPage);
    }

    public Page<BookEntity> getPageOfBooksByTag(Integer offset, Integer limit, Integer id){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookEntitiesByTagId(id, nextPage);
    }

    public Page<BookEntity> getPageOfBooksByGenre(Integer offset, Integer limit, String slug){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookEntitiesByGenreSlug(slug, nextPage);
    }

    public Page<BookEntity> getPageOfBooksByAuthorSlug(Integer offset, Integer limit, String slug){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookEntitiesByAuthor_Slug(slug, nextPage);
    }

    public Long getBookRatingBySlug(String slug) {
        String queryString = ("select sum(rating)/count(user_id)  " +
                "from book_app.rate_book rb " +
                "left join book_app.books b on b.id = rb.book_id " +
                "where b.slug = ?1");

        Query query = entityManager.createNativeQuery(queryString).setParameter(1, slug);
        return query.getSingleResult() == null ? 0 : (Long) query.getSingleResult();
    }

    public Long getBookCountRatingBySlug(String slug) {
        String queryString = ("select count(user_id)  " +
                "from book_app.rate_book rb " +
                "left join book_app.books b on b.id = rb.book_id " +
                "where b.slug = ?1");

        Query query = entityManager.createNativeQuery(queryString).setParameter(1, slug);
        return query.getSingleResult() == null ? 0 : (Long) query.getSingleResult();
    }
    public Map<Integer, Long> getStarsCountBySlug(String slug) {
        String queryString = ("select rating, count(user_id) " +
                "from book_app.rate_book rb \n" +
                "left join book_app.books b on b.id = rb.book_id\n" +
                "where b.slug = ?1 " +
                "group by rating");
        Query query = entityManager.createNativeQuery(queryString).setParameter(1, slug);
        List<Object[]> queryList = query.getResultList();
        Map<Integer, Long> result = new HashMap<Integer, Long>();
        queryList.forEach(item -> result.put((Integer) item[0], (Long) item[1]));

        return result;
    }

    public Integer getStarsByUserId(Integer userId, Integer bookId) {

        String queryString = ("select rb.rating  " +
                "from book_app.rate_book rb where rb.user_id = ?1 and rb.book_id = ?2");

        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter(1, userId);
        query.setParameter(2, bookId);
       try{
           return ((Long) query.getSingleResult()).intValue();
       }catch (NoResultException ex){
           return 0;
       }
    }

    @Transactional
    public void updateRating(Integer bookId, Integer rating){
        String queryString = ("insert into book_app.rate_book (user_id, book_id, rating) values(0, ?, ?);");
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter(1, bookId);
        query.setParameter(2, rating);
        query.executeUpdate();
    }

    public List<BookReviewEntity> findBookReviewsByBookId(Integer bookId){
        return bookReviewRepository.findBookReviewEntitiesByBookId(bookId);
    }


}
