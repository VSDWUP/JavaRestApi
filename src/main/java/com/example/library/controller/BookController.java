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
        log.info("Created book: {id:" + book.getId() + ", title:" + book.getTitle() + ", author:" + book.getAuthor() + "}");
        return new ResponseEntity<>(book,HttpStatus.CREATED);
    }

    @GetMapping(value = "book/{id}")
    public ResponseEntity <?> read(@PathVariable(name = "id") long id) throws BookNotFoundException {
        final Book book = bookService.getBook(id);
        log.info("Get book: {id:" + book.getId() + ", title:" + book.getTitle() + ", author:" + book.getAuthor() + "}");
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @PutMapping(value = "book/{id}")
    public ResponseEntity <?> update(@PathVariable(name = "id") long id, @RequestBody Book book){
        boolean marker = bookService.updateBook(book,id);
        if (marker == true){
            log.info("Updated book: {id:" + book.getId() + ", title:" + book.getTitle() + ", author:" + book.getAuthor() + "}");
            return new ResponseEntity<>(book,HttpStatus.OK);
        }
        else {
            log.error("Error updating book: {id:" + id + "}");
            return new ResponseEntity<>(book,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "book/{id}")
    public void delete (@PathVariable(name = "id") int id){
        boolean marker = bookService.deleteBook(id);
        if (marker == true){
            log.info("Deleted book with : {id:" + id + "}");
        }
        else {
            log.error("Error deleting book: {id:" + id + "}");
        }
    }

    @GetMapping(value = "/books")
    public List <Book> readAll(){
        List <Book> bookList = bookService.getAllBooks();
        log.info("Requested all books");
        return bookList;

    }
}
