package com.example.library.service;

import com.example.library.model.Author;

import java.util.List;

public interface AuthorService {

    void createAuthor(Author author);
    Author getAuthor(long id);
    Author getAuthorWoLog(long id);
    void updateAuthor(Author author, long id);
    void deleteAuthor(long id);
    List<Author> getAllAuthors();
}
