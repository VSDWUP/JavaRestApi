package com.example.library.service;

import com.example.library.controller.AuthorController;
import com.example.library.exceptions.*;
import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService{

    public static final AtomicInteger Author_Id_Holder = new AtomicInteger();

    @Autowired
    AuthorRepository authorRepository;


    @Override
    public void createAuthor(Author author) {
        authorRepository.save(new Author(Author_Id_Holder.incrementAndGet(),author.getName(),author.getSurname()));
        log.info("Created author: id:{}, name:{}, surname:{}",Author_Id_Holder.get(), author.getName(), author.getSurname());
    }

    @Override
    public Author getAuthor(long id) {
        Optional<Author> authorData = authorRepository.findById(id);
        if (authorData.isEmpty() ) {
            log.error("Author with id: {} not found", id);
            throw new AuthorNotFoundException("Author not found");
        } else {
            Author author = authorData.get();
            log.info("Get author: id:{}, name:{}, surname:{}",author.getId(),author.getName(),author.getSurname());
            return author;
        }
    }

    @Override
    public Author getAuthorWoLog(long id) {
        Optional<Author> authorData = authorRepository.findById(id);
        if (authorData.isEmpty() ) {
            throw new AuthorNotFoundException("Author not found");
        } else {
            Author author = authorData.get();
            return author;
        }
    }

    @Override
    public void updateAuthor(Author author, long id) {
        Optional<Author> authorData = authorRepository.findById(id);
        if (authorData.isPresent()) {
            Author _author = authorData.get();
            _author.setName(author.getName());
            _author.setSurname(author.getSurname());
            authorRepository.save(_author);
            log.info("Updated author: id:{}, name:{}, surname:{}", _author.getId(),_author.getName(),_author.getSurname());
        }
        else {
            log.error("Error updating Author with id:{}",id);
            throw new AuthorNotUpdatedException("Author not found");
        }
    }

    @Override
    public void deleteAuthor(long id) {
        Optional<Author> authorData = authorRepository.findById(id);
        if (authorData.isEmpty() ) {
            log.error("Error deleting Author with id:{}",id);
            throw new AuthorNotDeletedException("Author not found");
        } else {
            authorRepository.deleteById(id);
            log.info("Deleted author with: id:{}",id);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<Author>();
        authorRepository.findAll().forEach(authors::add);
        if (!authors.isEmpty()){
            log.info("Requested all authors");
            return authors;
        }
        else {
            log.error("There are no Authors found");
            throw new NoAuthorsFoundException("There are no authors");
        }
    }
}
