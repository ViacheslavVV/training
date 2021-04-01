package com.train.service;

import com.train.model.Book;

import java.util.List;

public interface BookService {

    void save(Book book);

    List<Book> findAll();
}
