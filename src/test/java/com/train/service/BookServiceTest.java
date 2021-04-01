package com.train.service;

import com.train.model.Book;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Тесты для демонстрации работоспособности полученного кода,
 * являются зависимыми от порядка, так что в идеале это пример плохой, но его цель не тестирование
 * а показать тебе как можно создать контекст самостоятельно и получать работоспособные бины
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookServiceTest {

    private BookService bookService;

    public BookServiceTest() {
        //инициализируем контекст
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //получаем из него настроенный сервис
        bookService = context.getBean(BookService.class);
    }

    @Before
    public void before() {
        System.out.println("<<<<<<<<<<<<<<< Новый тест >>>>>>>>>>>>>>>>");
    }

    @After
    public void after() {
        System.out.println("<<<<<<<<<<<<<<< Тест закончен >>>>>>>>>>>>>>>>");
    }

    @Test
    public void A_testInitialDataCount() {
        //проверяем как liquibase вставил нам значения
        List<Book> all = bookService.findAll();
        printBooks(all, "all books");
        Assert.assertEquals(all.size(), 2);
    }

    @Test
    public void B_testUpdateBook() {
        //проверяем что значений всё ещё 2
        List<Book> books = bookService.findAll();
        Assert.assertEquals(books.size(), 2);
        printBooks(books, "all books");

        //берем первую книгу и меняем ей значений на случайные
        Book book = books.get(0);
        printBooks(Collections.singletonList(book), "old book values");
        Long id = book.getId();
        String name = UUID.randomUUID().toString();
        String author = UUID.randomUUID().toString();
        book.setName(name);
        book.setAuthor(author);
        bookService.save(book);
        printBooks(Collections.singletonList(book), "new book values");

        List<Book> booksAfterUpdate = bookService.findAll();
        Assert.assertEquals(booksAfterUpdate.size(), 2);
        printBooks(booksAfterUpdate, "new books");

        book = booksAfterUpdate.stream().filter(x -> x.getId().equals(id)).findFirst().get();
        Assert.assertEquals(book.getName(), name);
        Assert.assertEquals(book.getAuthor(), author);
    }

    @Test
    public void C_testCreateNewBook() {
        List<Book> books = bookService.findAll();
        Assert.assertEquals(books.size(), 2);
        printBooks(books, "books");

        String name = UUID.randomUUID().toString();
        String author = UUID.randomUUID().toString();
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        bookService.save(book);
        List<Book> booksAfterInsert = bookService.findAll();
        Assert.assertEquals(booksAfterInsert.size(), 3);
        printBooks(booksAfterInsert, "new books");
        booksAfterInsert.removeAll(books);
        Assert.assertEquals(booksAfterInsert.size(), 1);

        book = booksAfterInsert.get(0);
        printBooks(Collections.singletonList(book), "inserted book values");
        Assert.assertEquals(book.getName(), name);
        Assert.assertEquals(book.getAuthor(), author);
    }

    private void printBooks(List<Book> books, String title){
        System.out.println(title);
        books.forEach(book ->
                System.out.println(String.format("id=%s, name=%s, author=%s", book.getId(), book.getName(), book.getAuthor()))
        );
    }
}
