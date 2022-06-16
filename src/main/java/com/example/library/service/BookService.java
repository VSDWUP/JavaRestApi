package com.example.library.service;

import com.example.library.model.Book;

import java.util.List;

public interface BookService {

    void createBook (Book book);
    Book getBook (long id);
    Book getBookWoLog (long id);
    void updateBook(Book book, long id);
    void deleteBook(long id);
    List<Book> getAllBooks ();


}
