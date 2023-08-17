package com.example.mybookshopapp.struct.book.review;

import com.example.mybookshopapp.struct.book.file.BookFileEntity;
import com.example.mybookshopapp.struct.user.UserEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_review")
public class BookReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "INT NOT NULL")
    private int bookId;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String text;

    @Transient
    private long like;

    @Transient
    private long dislike;
    public List<BookReviewLikeEntity> getBookReviewLikeEntities() {
        return bookReviewLikeEntities;
    }


    public void setBookReviewLikeEntities(List<BookReviewLikeEntity> bookReviewLikeEntities) {
        this.bookReviewLikeEntities = bookReviewLikeEntities;
    }

    public long getDislike() {
        return bookReviewLikeEntities.stream().filter(b -> b.getValue() == 0 && b.getUserId() == this.user.getId()).count();
    }


    @OneToMany(mappedBy = "review")
    private List<BookReviewLikeEntity> bookReviewLikeEntities;

    public long getLike() {
        return this.bookReviewLikeEntities.stream().filter(b -> b.getValue() == 1 && b.getUserId() == this.user.getId()).count();
    }

    public void setLike(long like) {
        this.like = like;
    }

    public void setDislike(long dislike) {
        this.dislike = dislike;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
