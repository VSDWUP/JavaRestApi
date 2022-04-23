package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    @Autowired //Прочитать про Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping(value = "/author")
    public ResponseEntity <?> create(@RequestBody Author author){
        authorService.createAuthor(author);
        return new ResponseEntity<>(author,HttpStatus.CREATED);
    }

    @GetMapping(value = "/author/{id}")
    public ResponseEntity <?> get(@PathVariable(value = "id") int id){
        final Author author = authorService.getAuthor(id);
        if (author != null){
            return new ResponseEntity<>(author, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Author not found",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/author/{id}")
    public ResponseEntity <?> update(@PathVariable(value = "id") int id, @RequestBody Author author){
        final boolean updated = authorService.updateAuthor(author,id);
        return updated
                ? new ResponseEntity<>(author, HttpStatus.OK)
                : new ResponseEntity<>("Author not modified",HttpStatus.NOT_MODIFIED);


    }

    @DeleteMapping(value = "/author/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") int id){
        final boolean deleted = authorService.deleteAuthor(id);
        return deleted
                ? new ResponseEntity("Author deleted",HttpStatus.OK)
                : new ResponseEntity("Author not modified",HttpStatus.NOT_MODIFIED);

    }

    @GetMapping(value = "/authors")
    public ResponseEntity <?> getAuthors(){
        final List<Author> authorList = authorService.getAllAuthors();
        if (authorList != null && !authorList.isEmpty()){
            return new ResponseEntity<>(authorList, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Authors not found",HttpStatus.NOT_FOUND);
        }
    }


}
