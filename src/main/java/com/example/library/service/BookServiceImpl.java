package com.example.library.service;

import com.example.library.controller.AuthorController;
import com.example.library.exceptions.BookNotDeletedException;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.exceptions.BookNotUpdatedException;
import com.example.library.exceptions.NoBooksFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BookServiceImpl implements BookService {
    private static final Map<Long, Book> Books = new HashMap<>();
    public static final AtomicInteger Book_Id_Holder = new AtomicInteger();
    private static final Logger log = Logger.getLogger(AuthorController.class);

    @Autowired
    BookRepository bookRepository;

    @Override
    public void createBook(Book book) {
        bookRepository.save(new Book(Book_Id_Holder.incrementAndGet(),book.getTitle(),book.getAuthor()));
        log.info("Created book: {id:" + Book_Id_Holder.get() + ", title:" + book.getTitle() + ", author:" + book.getAuthor() + "}");
    }

    @Override
    public Book getBook(long id) {
        Optional<Book> bookData = bookRepository.findById(id);
        if (bookData.isEmpty() ) {
            log.error("Book with id: {" + id + "} not found");
            throw new BookNotFoundException("Book not found");
        } else {
            Book book = bookData.get();
            log.info("Get book: {id:" + book.getId() + ", title:" + book.getTitle() + ", author:" + book.getAuthor() + "}");
            return book;
        }
    }

    @Override
    public void updateBook(Book book, long id) {
        Optional<Book> bookData = bookRepository.findById(id);
        if (bookData.isPresent()) {
            Book _book = bookData.get();
            _book.setTitle(book.getTitle());
            _book.setAuthor(book.getAuthor());
            bookRepository.save(_book);
            log.info("Updated book: {id:" + _book.getId() + ", title:" + _book.getTitle() + ", author:" + _book.getAuthor() + "}");
        }
        else {
            log.error("Error updating Book with id: {" + id + "}");
            throw new BookNotUpdatedException("Error finding book for updating");
        }
    }

    @Override
    public void deleteBook(long id) {
        Optional<Book> bookData = bookRepository.findById(id);
        if (bookData.isEmpty() ) {
            log.error("Error deleting Book with id: {" + id + "}");
            throw new BookNotDeletedException("Error deleting book");
        } else {
            log.info("Deleted book with : {id:" + id + "}");
            bookRepository.deleteById(id);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        bookRepository.findAll().forEach(books::add);
        if (!books.isEmpty()){
            log.info("Requested all books: ");
            return books;
        }
        else {
            log.error("There are no Books found");
            throw new NoBooksFoundException("There are no books");
        }
    }
}
