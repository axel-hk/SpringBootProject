package com.example.mybookshopapp.services;

import com.example.mybookshopapp.data.dao.Author;
import com.example.mybookshopapp.data.repositories.AuthorRepository;
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

    public List<Author> getAuthorData(){
        return authorRepository.findAll();
    }
}
