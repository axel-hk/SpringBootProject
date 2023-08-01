package com.example.mybookshopapp.services;

import com.example.mybookshopapp.data.repositories.BookRepository;
import com.example.mybookshopapp.struct.book.BookEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageImpl;

@Service
public class BooksRatingAndPopularityService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Value("${spring.jpa.properties.hibernate.default_schema}")
    private String schema;


    public  List<BookEntity> getPageOfPopBook(Integer offset, Integer limit) {
        String queryString = "SELECT " +
                "     bu.book_id, " +
                "     COUNT(DISTINCT CASE WHEN but.code = 'PAID' THEN bu.user_id  END) + " +
                "     0.7 *  COUNT(DISTINCT CASE WHEN but.code = 'CART' THEN bu.user_id  END) + " +
                "     0.4 * COUNT(DISTINCT CASE WHEN but.code = 'KEPT' THEN bu.user_id  END) + " +
                "     0.2 *  COUNT(DISTINCT CASE WHEN but.code = 'ARCHIVED' THEN bu.user_id  END) AS popularity " +
                "FROM " +
                schema+".book2user bu " +
                "    INNER JOIN " +schema+".book2user_type but  ON bu.type_id  = but.id " +
                "GROUP BY " +
                "    bu.book_id " +
                "order by  popularity DESC";
        Query query = entityManager.createNativeQuery(queryString);

        List<Object[]> results = query.getResultList();


        List<BookEntity> bookList = bookRepository.findBookEntitiesByIdIn(results.stream().map(b -> (Integer) b[0]).collect(Collectors.toList()));

        PagedListHolder<BookEntity> pagedListHolder = new PagedListHolder<>(bookList);
        pagedListHolder.setPageSize(limit); // Number of elements per page
        pagedListHolder.setPage(offset);

        pagedListHolder.getPage();

        return pagedListHolder.getPageList();
    }

}
