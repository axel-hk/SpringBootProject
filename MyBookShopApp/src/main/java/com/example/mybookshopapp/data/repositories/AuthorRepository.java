package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.struct.author.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    AuthorEntity getAuthorEntityBySlug(@Param("slug") String slug);
}
