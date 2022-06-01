package com.example.library.service;

import com.example.library.exceptions.BookNotDeletedException;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.exceptions.BookNotUpdatedException;
import com.example.library.exceptions.NoBooksFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BookServiceImpl implements BookService {
    private static final Map<Long, Book> Books = new HashMap<>();
    private static final AtomicInteger Book_Id_Holder = new AtomicInteger();
    @Autowired
    BookRepository bookRepository;

    @Override
    public void createBook(Book book) {
        bookRepository.save(new Book(book.getId(),book.getTitle(),book.getAuthor()));
    }

    @Override
    public Book getBook(long id) {
        Optional<Book> bookData = bookRepository.findById(id);
        if (bookData.isEmpty() ) {
            throw new BookNotFoundException("Book not found");
        } else {
            Book book = bookData.get();
            return book;
        }
    }

    @Override
    public boolean updateBook(Book book, long id) {
        Optional<Book> bookData = bookRepository.findById(id);
        if (bookData.isPresent()) {
            Book _book = bookData.get();
            _book.setId(book.getId());
            _book.setTitle(book.getTitle());
            _book.setAuthor(book.getAuthor());
            bookRepository.save(_book);
            return true;
        }
        else {
            throw new BookNotUpdatedException("Error finding book for updating");
        }
    }

    @Override
    public boolean deleteBook(long id) {
        Optional<Book> bookData = bookRepository.findById(id);
        if (bookData.isEmpty() ) {
            throw new BookNotDeletedException("Error finding book for deleteing");
        } else {
            bookRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        bookRepository.findAll().forEach(books::add);
        if (!books.isEmpty()){
            return books;
        }
        else {
            throw new NoBooksFoundException("There are no books");
        }
    }
}
