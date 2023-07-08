package com.example.mybookshopapp.struct.book.links;

import jakarta.persistence.*;

@Entity
@Table(name = "book2tags")
public class Book2TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "INT NOT NULL")
    private int bookId;

    @Column(columnDefinition = "INT NOT NULL")
    private int tagId;
}
