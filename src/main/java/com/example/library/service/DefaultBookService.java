package com.example.library.service;

import com.example.library.converter.BookModelEntityConverter;
import com.example.library.entity.BookEntity;
import com.example.library.exceptions.BookNotDeletedException;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.exceptions.BookNotUpdatedException;
import com.example.library.exceptions.NoBooksFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class DefaultBookService implements BookService {

    public static final AtomicInteger Book_Id_Holder = new AtomicInteger();

    @Autowired
    private final BookRepository bookRepository;

    private final BookModelEntityConverter bookModelEntityConverter;

    @Override
    public void createBook(Book book) {
        BookEntity bookEntity = bookModelEntityConverter.convertFromModelToSource(book);
        bookEntity.setId(Book_Id_Holder.incrementAndGet());
        bookRepository.save(bookEntity);
        log.info("Created book: id: {}, title: {}, author: {}", bookEntity.getId(), bookEntity.getTitle(), bookEntity.getAuthor());
    }

    @Override
    public Book getBook(long id) {
        Optional<BookEntity> bookEntityData = bookRepository.findById(id);
        if (bookEntityData.isEmpty() ) {
            log.error("Book with id: {} not found", id);
            throw new BookNotFoundException("Book not found");
        } else {
            Book book = bookModelEntityConverter.convertFromSourceToModel(bookEntityData.get());
            log.info("Get book: id: {}, title: {}, author: {}", book.getId(),book.getTitle(),book.getAuthor());
            return book;
        }
    }

    @Override
    public Book getBookWoLog(long id) {
        Optional<BookEntity> bookEntityData = bookRepository.findById(id);
        if (bookEntityData.isEmpty() ) {
            throw new BookNotFoundException("Book not found");
        } else {
            return bookModelEntityConverter.convertFromSourceToModel(bookEntityData.get());
        }
    }

    @Override
    public void updateBook(Book book, long id) {
        Optional<BookEntity> bookEntityData = bookRepository.findById(id);
        if (bookEntityData.isPresent()) {
            Book _book = bookModelEntityConverter.convertFromSourceToModel(bookEntityData.get());
            _book.setTitle(book.getTitle());
            _book.setAuthor(book.getAuthor());
            BookEntity bookEntity = bookModelEntityConverter.convertFromModelToSource(_book);
            bookRepository.save(bookEntity);
            log.info("Updated book: id: {}, title: {}, author: {}", bookEntity.getId(), bookEntity.getTitle(), bookEntity.getAuthor());
        }
        else {
            log.error("Error updating Book with id: {}",id);
            throw new BookNotUpdatedException("Error finding book for updating");
        }
    }

    @Override
    public void deleteBook(long id) {
        Optional<BookEntity> bookEntityData = bookRepository.findById(id);
        if (bookEntityData.isEmpty() ) {
            log.error("Error deleting Book with id: {}",id);
            throw new BookNotDeletedException("Error deleting book");
        } else {
            log.info("Deleted book with id: {}",id);
            bookRepository.deleteById(id);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<BookEntity> bookEntities = new ArrayList<>(bookRepository.findAll());
        if (!bookEntities.isEmpty()){
            log.info("Requested all books");
            return bookEntities.stream()
                    .map(bookModelEntityConverter::convertFromSourceToModel)
                    .collect(Collectors.toList());
        }
        else {
            log.error("There are no Books found");
            throw new NoBooksFoundException("There are no books");
        }
    }
}
