package com.example.library.service;

import com.example.library.model.Author;

import java.util.List;

public interface AuthorService {

    void createAuthor(Author author);
    Author getAuthor(int id);
    boolean updateAuthor(Author author, int id);
    boolean deleteAuthor(int id);
    List<Author> getAllAuthors();
}
