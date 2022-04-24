package com.example.library.service;

import com.example.library.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BookServiceImpl implements BookService {
    private static final Map<Integer, Book> Books = new HashMap<>();
    private static final AtomicInteger Book_Id_Holder = new AtomicInteger();

    @Override
    public void createBook(Book book) {
        final int bookId = Book_Id_Holder.incrementAndGet();
        book.setId(bookId);
        Books.put(bookId,book);

    }

    @Override
    public Book getBook(int id) {
        return Books.get(id);
    }

    @Override
    public boolean updateBook(Book book, int id) {
        if (Books.containsKey(id)){
            book.setId(id);
            Books.put(id,book);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean deleteBook(int id) {
        return Books.remove(id) != null;
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(Books.values());
    }
}
