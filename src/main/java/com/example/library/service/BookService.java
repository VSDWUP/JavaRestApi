package com.example.library.service;

import com.example.library.model.Book;

import java.util.List;

public interface BookService {

    void createBook (Book book);
    Book getBook (int id);
    boolean updateBook(Book book, int id);
    boolean deleteBook(int id);
    List<Book> getAllBooks ();


}
