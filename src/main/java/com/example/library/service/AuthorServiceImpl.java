package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AuthorServiceImpl implements AuthorService{

    private static final Map <Integer,Author> Authors = new HashMap<>();
    private static final AtomicInteger Author_Id_Holder = new AtomicInteger();
    @Autowired
    AuthorRepository authorRepository;


    @Override
    public void createAuthor(Author author) {
        authorRepository.save(new Author(author.getId(),author.getName(),author.getSurname()));
    }

    @Override
    public Author getAuthor(long id) {
        Optional<Author> authorData = authorRepository.findById(id);
        if (authorData.isEmpty() ) {
            return new Author(323,"DFsdfsd","fsdfsd");//Заглушка
        } else {
            Author author = authorData.get();
            return author;
        }
    }

    @Override
    public boolean updateAuthor(Author author, long id) {
        Optional<Author> authorData = authorRepository.findById(id);
        if (authorData.isPresent()) {
            Author _author = authorData.get();
            _author.setId(author.getId());
            _author.setName(author.getName());
            _author.setSurname(author.getSurname());
            authorRepository.save(_author);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteAuthor(long id) {
        Optional<Author> authorData = authorRepository.findById(id);
        if (authorData.isEmpty() ) {
            return false;//Заглушка
        } else {
            authorRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<Author>();
        authorRepository.findAll().forEach(authors::add);
        if (!authors.isEmpty()){
            return authors;
        }
        else {
            return new ArrayList<Author>(); //Заглушка
        }
    }
}
