package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class AuthorController {
    @Autowired
    AuthorRepository authorRepository;
    private final AuthorService authorService;
    private static final Logger log = Logger.getLogger(AuthorController.class);

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping(value = "/author")
    public ResponseEntity <?> create(@RequestBody Author author){
        authorService.createAuthor(author);
        return new ResponseEntity<>(author,HttpStatus.CREATED);
    }

    @GetMapping(value = "/author/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") long id){
        final Author author = authorService.getAuthor(id);
        return new ResponseEntity<>(author,HttpStatus.CREATED);

    }

    @PutMapping(value = "/author/{id}")
    public ResponseEntity <?> update(@PathVariable(value = "id") int id, @RequestBody Author author){
        authorService.updateAuthor(author,id);
        return new ResponseEntity<>(author,HttpStatus.OK);

    }


    @DeleteMapping(value = "/author/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") int id){
        authorService.deleteAuthor(id);
        return new ResponseEntity<>("AUTHOR DELETED",HttpStatus.OK);

    }

    @GetMapping(value = "/authors")
    public ResponseEntity <?> getAuthors(){
        List <Author> authorList = authorService.getAllAuthors();
        return new ResponseEntity<>(authorList,HttpStatus.OK);
    }


}
