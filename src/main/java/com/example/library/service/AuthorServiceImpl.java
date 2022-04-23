package com.example.library.service;

import com.example.library.model.Author;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AuthorServiceImpl implements AuthorService{

    private static final Map <Integer,Author> Authors = new HashMap<>();
    private static final AtomicInteger Author_Id_Holder = new AtomicInteger();

    @Override
    public void createAuthor(Author author) {
        final int id = Author_Id_Holder.incrementAndGet();
        author.setId(id);
        Authors.put(id,author);
    }

    @Override
    public Author getAuthor(int id) {
        return Authors.get(id);
    }

    @Override
    public boolean updateAuthor(Author author, int id) {
        if (Authors.containsKey(id)){
            author.setId(id);
            Authors.put(id,author);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteAuthor(int id) {
        if (Authors.containsKey(id)){
            Authors.remove(id);
            return true;
        }
        else {

            return false;
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        return new ArrayList<>(Authors.values());
    }
}
