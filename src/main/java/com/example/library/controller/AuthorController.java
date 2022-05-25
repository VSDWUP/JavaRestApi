package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.List;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private static final Logger log = Logger.getLogger(AuthorController.class);

    @Autowired //Прочитать про Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping(value = "/author")
    public ResponseEntity <?> create(@RequestBody Author author){
        authorService.createAuthor(author);
        log.info("Created author: {id:" + author.getId() + ", name:" + author.getName() + ", surname:" + author.getSurname() + "}");
        return new ResponseEntity<>(author,HttpStatus.CREATED);
    }

    @GetMapping(value = "/author/{id}")
    //return Author 200 обработка статусов в Exception'ах Controller advice @ErrorHandler
    //Отдельный класс @ControllerAdvice, добавляются методы с @ExceptionHandler
    //Spring rest error mapping
    //SpringTest на эдпоинты Mock на storage Junit Mockito
    public Author get(@PathVariable(value = "id") int id){
        final Author author = authorService.getAuthor(id);
        if (author != null){
            return author;
        }
        else {
            return null;
        }
    }

    @PutMapping(value = "/author/{id}")
    public ResponseEntity <?> update(@PathVariable(value = "id") int id, @RequestBody Author author){
        final boolean updated = authorService.updateAuthor(author,id);
        if (updated == true){
            log.info("Updated author: {id: " + author.getId() + ", name:" + author.getName() + ", surname:" + author.getSurname() + "}");
            return new ResponseEntity<>(author,HttpStatus.OK);
        }
        else {
            log.error("Error updating author: {id:" + id + "}");
            return new ResponseEntity<>("AUTHOR NOT FOUND",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/author/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") int id){
        final Author author = authorService.getAuthor(id);
        final boolean deleted = authorService.deleteAuthor(id);
        if (deleted == true){
            log.info("Deleted author: {id:" + author.getId() + ", name:" + author.getName() + ", surname" + author.getSurname() + "}");
            return new ResponseEntity("AUTHOR DELETED",HttpStatus.OK);
        }
        else {
            log.error("Error deleting author: {id:" + id + "}");
            return new ResponseEntity("AUTHOR NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/authors")
    public ResponseEntity <?> getAuthors(){
        final List<Author> authorList = authorService.getAllAuthors();
        if (authorList != null && !authorList.isEmpty()){
            log.info("Requested all authors");
            return new ResponseEntity<>(authorList, HttpStatus.OK);
        }
        else {
            log.error("Authors not found");
            return new ResponseEntity<>("AUTHORS NOT FOUND",HttpStatus.NOT_FOUND);
        }
    }


}
