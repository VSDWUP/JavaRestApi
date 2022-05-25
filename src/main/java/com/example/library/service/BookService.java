package com.example.library.service;

import com.example.library.model.Book;

import java.util.List;

public interface BookService {

    void createBook (Book book);
    Book getBook (long id);
    boolean updateBook(Book book, long id);
    boolean deleteBook(long id);
    List<Book> getAllBooks ();


}
