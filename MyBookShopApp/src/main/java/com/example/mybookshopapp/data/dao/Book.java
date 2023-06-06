package com.example.mybookshopapp.data.dao;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String priceOld;
    private String price;

    public Author getAuthor() {
        return Author;
    }

    public void setAuthor(Author Author) {
        this.Author = Author;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author Author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(String priceOld) {
        this.priceOld = priceOld;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
