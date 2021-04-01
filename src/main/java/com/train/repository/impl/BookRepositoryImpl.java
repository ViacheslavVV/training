package com.train.repository.impl;

import com.train.model.Book;
import com.train.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Book book) {
        if (book.getId() == null) {
            jdbcTemplate.update("insert into MYDB.BOOK values ( MYDB.ID_SEQUENCE.NEXTVAL, ?, ? )",
                    book.getName(), book.getAuthor());
        } else {
            jdbcTemplate.update("update MYDB.BOOK set name = ?, author = ? where id = ?", preparedStatement -> {
                preparedStatement.setString(1, book.getName());
                preparedStatement.setString(2, book.getAuthor());
                preparedStatement.setLong(3, book.getId());
            });

        }
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("select * from MYDB.BOOK", (resultSet, i) -> {
            Book book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthor(resultSet.getString("author"));
            return book;
        });
    }
}
