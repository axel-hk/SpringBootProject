package com.example.mybookshopapp.data.dao;

import com.example.mybookshopapp.struct.book.BookEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public List<BookEntity> getBookList() {
        return bookEntityList;
    }

    public void setBookList(List<BookEntity> bookEntityList) {
        this.bookEntityList = bookEntityList;
    }

    @OneToMany
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private List<BookEntity> bookEntityList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                ", name='" + name + '\'' +
                '}';
    }

}
