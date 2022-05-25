package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        /*bookService.createBook(book);
        log.info("Created book: {id:" + book.getId() + ", title:" + book.getTitle() + ", author:" + book.getAuthor() + "}");
        return new ResponseEntity<>(book,HttpStatus.CREATED);*/
        bookService.createBook(book);
        log.info("Created book: {id:" + book.getId() + ", title:" + book.getTitle() + ", author:" + book.getAuthor() + "}");
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping(value = "book/{id}")
    public ResponseEntity <?> read(@PathVariable(name = "id") long id){
        final Book book = bookService.getBook(id);
        log.info("Get book: {id:" + book.getId() + ", title:" + book.getTitle() + ", author:" + book.getAuthor() + "}");
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @PutMapping(value = "book/{id}")
    public ResponseEntity <?> update(@PathVariable(name = "id") long id, @RequestBody Book book){
        //final boolean updated = bookService.updateBook(book,id);
        //if (updated == true){
        //    log.info("Updated book: {id:" + book.getId() + ", title:" + book.getTitle() + ", author:" + book.getAuthor() + "}");
        //    return new ResponseEntity<>(book,HttpStatus.OK);
        //}
        //else {
        //    log.error("Error updating book: {id:" + id + "}");
        //    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //}

        boolean marker = bookService.updateBook(book,id);
        if (marker == true){
            log.info("Updated book: {id:" + book.getId() + ", title:" + book.getTitle() + ", author:" + book.getAuthor() + "}");
            return new ResponseEntity<>(book,HttpStatus.OK);
        }
        else {
            log.error("Error updating book: {id:" + id + "}");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @DeleteMapping(value = "book/{id}")
    public ResponseEntity<?> delete (@PathVariable(name = "id") int id){
        /*final Book book = bookService.getBook(id);
        final boolean deleted = bookService.deleteBook(id);
        if (deleted == true){
            log.info("Deleted book: {id:" + book.getId() + ", title:" + book.getTitle() + ", author:" + book.getAuthor() + "}");
            return new ResponseEntity<>("BOOK DELETED",HttpStatus.OK);
        }
        else {
            log.error("Error deleting book: {id:" + id + "}");
            return new ResponseEntity<>("BOOK NOT FOUND", HttpStatus.NOT_FOUND);
        }*/
        boolean marker = bookService.deleteBook(id);
        if (marker == true){
            log.info("Deleted book with : {id:" + id + "}");
            return new ResponseEntity<>("BOOK DELETED",HttpStatus.OK);
        }
        else {
            log.error("Error deleting book: {id:" + id + "}");
            return new ResponseEntity<>("BOOK NOT FOUND", HttpStatus.NOT_FOUND);
        }


    }

    @GetMapping(value = "/books")
    public ResponseEntity <?> readAll(){
        List <Book> bookList = bookService.getAllBooks();
        log.info("Requested all books");
        return new ResponseEntity<>(bookList,HttpStatus.OK);

    }
}
