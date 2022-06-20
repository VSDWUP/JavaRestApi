package com.example.library.controller;

import com.example.library.converter.BookResourceModelConverter;
import com.example.library.model.Book;
import com.example.library.resource.BookResource;
import com.example.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.library.service.DefaultBookService.Book_Id_Holder;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@AllArgsConstructor
public class BookController {

    @Autowired
    private final BookService bookService;

    private final BookResourceModelConverter converter;

    @PostMapping(value = "/book")
    public BookResource create(@RequestBody BookResource bookResource){
        Book book = converter.convertFromSourceToModel(bookResource);
        bookService.createBook(book);
        return converter.convertFromModelToSource(bookService.getBookWoLog(Book_Id_Holder.get()));
    }

    @GetMapping(value = "book/{id}")
    public BookResource get(@PathVariable(name = "id") long id) {
        final Book book = bookService.getBook(id);
        return converter.convertFromModelToSource(book);
    }

    @PutMapping(value = "book/{id}")
    public BookResource update(@PathVariable(name = "id") long id, @RequestBody BookResource bookResource){
        Book book = converter.convertFromSourceToModel(bookResource);
        bookService.updateBook(book,id);
        return converter.convertFromModelToSource(bookService.getBookWoLog(id));

    }

    @DeleteMapping(value = "book/{id}")
    public ResponseEntity <?> delete (@PathVariable(name = "id") long id){
        bookService.deleteBook(id);
        String responseMessage = String.format("Successfully deleted author with id:%s", id);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping(value = "/books")
    public List <BookResource> getBooks(){
        return bookService.getAllBooks().stream()
                .map(converter::convertFromModelToSource)
                .collect(Collectors.toList());

    }
}
