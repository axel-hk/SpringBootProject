package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.struct.author.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}
