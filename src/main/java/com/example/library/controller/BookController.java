package com.example.library.controller;

import com.example.library.exceptions.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;
    private final BookService bookService;
    private static final Logger log = Logger.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "/book")
    public ResponseEntity <?> create(@RequestBody Book book){
        bookService.createBook(book);
        return new ResponseEntity<>(book,HttpStatus.CREATED);
    }

    @GetMapping(value = "book/{id}")
    public ResponseEntity <?> read(@PathVariable(name = "id") long id) throws BookNotFoundException {
        final Book book = bookService.getBook(id);
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @PutMapping(value = "book/{id}")
    public ResponseEntity <?> update(@PathVariable(name = "id") long id, @RequestBody Book book){
        bookService.updateBook(book,id);
        return new ResponseEntity<>(book,HttpStatus.OK);

    }

    @DeleteMapping(value = "book/{id}")
    public ResponseEntity <?> delete (@PathVariable(name = "id") int id){
        Book book = bookService.getBook(id);
        bookService.deleteBook(id);
        String str = "Deleted Book:\n id: " + book.getId() + ",\n title: " + book.getTitle() + ",\n author: " + book.getAuthor();
        return new ResponseEntity<>(str,HttpStatus.OK);
    }

    @GetMapping(value = "/books")
    public List <Book> readAll(){
        List <Book> bookList = bookService.getAllBooks();
        return bookList;

    }
}
