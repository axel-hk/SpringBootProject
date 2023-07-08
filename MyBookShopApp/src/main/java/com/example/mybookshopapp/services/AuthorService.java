package com.example.mybookshopapp.services;

import com.example.mybookshopapp.data.repositories.AuthorRepository;
import com.example.mybookshopapp.struct.author.AuthorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorEntity> getAuthorData(){
        return authorRepository.findAll();
    }

    public AuthorEntity getAuthorBySlug(String slug) {
        return authorRepository.getAuthorEntityBySlug(slug);
    }
}
