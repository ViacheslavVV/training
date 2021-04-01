package com.train.repository;

import com.train.model.Book;

import java.util.List;

public interface BookRepository {

    void save(Book book);

    List<Book> findAll();
}
