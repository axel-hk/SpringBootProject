package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.struct.book.file.BookFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookFileRepository extends JpaRepository<BookFileEntity, Integer> {

    public BookFileEntity getBookFileEntityByHash(String hash);
}
