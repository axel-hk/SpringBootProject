package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.data.dao.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
