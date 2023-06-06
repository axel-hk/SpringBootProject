package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.data.dao.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
