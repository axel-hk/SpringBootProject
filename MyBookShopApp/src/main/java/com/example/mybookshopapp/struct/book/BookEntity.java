package com.example.mybookshopapp.struct.book;

import com.example.mybookshopapp.struct.author.AuthorEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Entity
@Table(name="book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(nullable = false)
    private LocalDate pubDate;
    @Column(nullable = false)
    private short isBestseller;
    @Column(nullable = false)
    private String slug;
    @Column(nullable = false)
    private String title;

    private String image;

    private String description;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    @ColumnDefault("0")
    private short discount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }

    public short getIsBestseller() {
        return isBestseller;
    }

    public void setIsBestseller(short isBestseller) {
        this.isBestseller = isBestseller;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public short getDiscount() {
        return discount;
    }

    public void setDiscount(short discount) {
        this.discount = discount;
    }
}
