package com.example.library.service;

import com.example.library.model.Author;

import java.util.List;

public interface AuthorService {

    void createAuthor(Author author);
    Author getAuthor(long id);
    boolean updateAuthor(Author author, long id);
    boolean deleteAuthor(long id);
    List<Author> getAllAuthors();
}
