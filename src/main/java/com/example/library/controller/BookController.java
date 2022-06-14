package com.example.library.controller;

import com.example.library.converter.BookResourceModelConverter;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.resource.BookResource;
import com.example.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.library.service.BookServiceImpl.Book_Id_Holder;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@AllArgsConstructor
public class BookController {

    @Autowired
    BookRepository bookRepository;
    private final BookService bookService;
    private final BookResourceModelConverter converter;

    @PostMapping(value = "/book")
    public BookResource create(@RequestBody BookResource bookResource){
        Book book = converter.convertFromSourceToModel(bookResource);
        bookService.createBook(book);
        BookResource return_bookResource = converter.convertFromModelToSource(bookService.getBookWoLog(Book_Id_Holder.get()));
        return return_bookResource;
    }

    @GetMapping(value = "book/{id}")
    public BookResource read(@PathVariable(name = "id") long id) {
        final Book book = bookService.getBook(id);
        BookResource return_bookResource = converter.convertFromModelToSource(book);
        return return_bookResource;
    }

    @PutMapping(value = "book/{id}")
    public BookResource update(@PathVariable(name = "id") long id, @RequestBody BookResource bookResource){
        Book book = converter.convertFromSourceToModel(bookResource);
        bookService.updateBook(book,id);
        Book book_return = bookService.getBookWoLog(id);
        BookResource return_bookResource = converter.convertFromModelToSource(book_return);
        return return_bookResource;

    }

    @DeleteMapping(value = "book/{id}")
    public ResponseEntity delete (@PathVariable(name = "id") int id){
        Book book = bookService.getBookWoLog(id);
        bookService.deleteBook(id);
        String str = "Deleted Book:\n id: " + book.getId() + ",\n title: " + book.getTitle() + ",\n author: " + book.getAuthor();
        return new ResponseEntity<>(str,HttpStatus.OK);
    }

    @GetMapping(value = "/books")
    public List <BookResource> readAll(){
        return bookService.getAllBooks().stream()
                .map(converter::convertFromModelToSource)
                .collect(Collectors.toList());

    }
}
