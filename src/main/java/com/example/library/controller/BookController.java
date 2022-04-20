package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "/book")
    public ResponseEntity <?> create(@RequestBody Book book){
        bookService.createBook(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "book/{id}")
    public ResponseEntity <?> read(@PathVariable(name = "id") int id){
        final Book book = bookService.readBook(id);
        if (book != null) {
            return new ResponseEntity<>(book,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "book/id")
    public ResponseEntity <?> update(@PathVariable(name = "id") int id, @RequestBody Book book){
        final boolean updated = bookService.updateBook(book,id);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "book/{id}")
    public ResponseEntity<?> delete (@PathVariable(name = "id") int id){
        final boolean deleted = bookService.deleteBook(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/books")
    public ResponseEntity <?> readAll(){
        final List<Book> bookList = bookService.readAllBooks();
        if (bookList != null && !bookList.isEmpty()) {
            return new ResponseEntity<>(bookList,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
