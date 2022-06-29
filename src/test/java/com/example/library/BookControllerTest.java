package com.example.library;

import com.example.library.controller.BookController;
import com.example.library.converter.BookResourceModelConverter;
import com.example.library.exceptions.*;
import com.example.library.model.Book;
import com.example.library.resource.BookResource;
import com.example.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class BookControllerTest {
    private BookController bookController;
    private BookService bookService;
    private BookResourceModelConverter bookConverter;


    @BeforeEach
    public void init(){
        this.bookService = mock(BookService.class);
        this.bookConverter = mock(BookResourceModelConverter.class);
        this.bookController = new BookController(bookService, bookConverter);
    }

    @Test
    public void createBook(){
        BookResource bookResource = BookResource.builder()
                .title("War and Peace")
                .author_id(1)
                .build();

        Book book = Book.builder()
                .title("War and Peace")
                .author_id(1)
                .build();

        when(bookConverter.convertFromSourceToModel(any())).thenReturn(book);
        when(bookConverter.convertFromModelToSource(any())).thenReturn(bookResource);

        BookResource response = bookController.create(bookResource);
        assertEquals(response, bookResource);
        verify(bookConverter, times(1)).convertFromModelToSource(any());
        verify(bookConverter, times(1)).convertFromSourceToModel(any());
        verify(bookService, times(1)).createBook(any());

    }

    @Test
    public void getBook(){
        BookResource bookResource = BookResource.builder()
                .title("War and Peace")
                .author_id(1)
                .build();

        Book book = Book.builder()
                .title("War and Peace")
                .author_id(1)
                .build();

        when(bookConverter.convertFromSourceToModel(any())).thenReturn(book);
        when(bookConverter.convertFromModelToSource(any())).thenReturn(bookResource);
        when(bookService.getBook(anyLong())).thenReturn(book);

        BookResource created = bookController.create(bookResource);

        BookResource response = bookController.get(created.getId());

        assertEquals(created,response);
        verify(bookConverter, times(2)).convertFromModelToSource(any());
        verify(bookConverter, times(1)).convertFromSourceToModel(any());
        verify(bookService, times(1)).getBook(anyLong());
    }

    @Test
    public void getBookWithException(){
        try {
            bookController.get(9323);
        }
        catch (Exception e){
            String actualMessage = e.getMessage();
            String exceptionMessage = "Book not found";
            assertEquals(exceptionMessage,actualMessage);
            assertTrue(e instanceof BookNotFoundException);
            verify(bookConverter, times(2)).convertFromModelToSource(any());
            verify(bookConverter, times(1)).convertFromSourceToModel(any());
            verify(bookService, times(1)).getBookWoLog(anyLong());
        }
    }

    @Test
    public void updateBook() {
        BookResource bookResource = BookResource.builder()
                .title("War and Peace")
                .author_id(1)
                .build();

        Book book = Book.builder()
                .title("War and Peace")
                .author_id(1)
                .build();

        when(bookConverter.convertFromSourceToModel(any())).thenReturn(book);
        when(bookConverter.convertFromModelToSource(any())).thenReturn(bookResource);

        BookResource response = bookController.update(book.getId(),bookResource);

        assertEquals(0,response.getId());
        verify(bookConverter, times(1)).convertFromModelToSource(any());
        verify(bookConverter, times(1)).convertFromSourceToModel(any());
        verify(bookService, times(1)).updateBook(any(),anyLong());
    }

    @Test
    public void updateBookWithException(){
        BookResource bookResource = BookResource.builder()
                .title("War and Peace")
                .author_id(1)
                .build();

        try {

            bookController.update(97819, bookResource);
        }
        catch (Exception e){
            String actualMessage = e.getMessage();
            String exceptionMessage = "Error finding book for updating";
            assertEquals(exceptionMessage,actualMessage);
            assertTrue(e instanceof BookNotUpdatedException);
            verify(bookConverter, times(2)).convertFromModelToSource(any());
            verify(bookConverter, times(1)).convertFromSourceToModel(any());
            verify(bookService, times(1)).updateBook(any(),anyLong());
        }
    }

    @Test
    public void deleteBook(){
        BookResource bookResource = BookResource.builder()
                .title("War and Peace")
                .author_id(1)
                .build();

        Book book = Book.builder()
                .title("War and Peace")
                .author_id(1)
                .build();

        when(bookConverter.convertFromSourceToModel(any())).thenReturn(book);
        when(bookConverter.convertFromModelToSource(any())).thenReturn(bookResource);

        BookResource created = bookController.create(bookResource);

        ResponseEntity<?> response = bookController.delete(book.getId());

        assertEquals(String.format("Successfully deleted author with id:%d",created.getId()),response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookConverter, times(1)).convertFromModelToSource(any());
        verify(bookConverter, times(1)).convertFromSourceToModel(any());
        verify(bookService,times(1)).deleteBook(anyLong());
    }

    @Test
    public void deleteBookWithException(){
        long id = 8678L;
        try {
            bookController.delete(id);
        }
        catch (Exception e){
            String actualMessage = e.getMessage();
            String exceptionMessage = "Error deleting book";
            assertEquals(exceptionMessage,actualMessage);
            assertTrue(e instanceof BookNotDeletedException);
            verify(bookConverter, times(2)).convertFromModelToSource(any());
            verify(bookConverter, times(1)).convertFromSourceToModel(any());
            verify(bookService, times(1)).updateBook(any(),anyLong());
        }
    }

    @Test
    public void getBooks(){
        BookResource bookResource = BookResource.builder()
                .title("War and Peace")
                .author_id(1)
                .build();

        Book book = Book.builder()
                .title("War and Peace")
                .author_id(1)
                .build();

        when(bookConverter.convertFromSourceToModel(any())).thenReturn(book);
        when(bookConverter.convertFromModelToSource(any())).thenReturn(bookResource);
        when(bookService.getAllBooks()).thenReturn(Collections.singletonList(book));

        bookController.create(bookResource);

        List<BookResource> authorResourceList = new ArrayList<>();
        authorResourceList.add(bookResource);

        List<BookResource> result = bookController.getBooks();

        assertEquals(authorResourceList,result);
        verify(bookConverter, times(2)).convertFromModelToSource(any());
        verify(bookConverter, times(1)).convertFromSourceToModel(any());
        verify(bookService, times(1)).getAllBooks();

    }


    @Test
    public void getBooksWithException(){
        List<BookResource> authorResourceList = new ArrayList<>();
        List<BookResource> result = bookController.getBooks();
        try {
            bookController.getBooks();
        }
        catch (Exception e){
            String actualMessage = e.getMessage();
            String exceptionMessage = "No books found";
            assertEquals(authorResourceList,result);
            assertEquals(exceptionMessage,actualMessage);
            assertTrue(e instanceof NoBooksFoundException);
            verify(bookConverter, times(2)).convertFromModelToSource(any());
            verify(bookConverter, times(1)).convertFromSourceToModel(any());
            verify(bookService, times(1)).getAllBooks();
        }
    }
}
