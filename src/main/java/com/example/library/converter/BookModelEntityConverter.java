package com.example.library.converter;

import com.example.library.entity.BookEntity;
import com.example.library.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookModelEntityConverter implements Converter <BookEntity, Book>  {
    @Override
    public Book convertFromSourceToModel(BookEntity resource) {
        return Book.builder()
                .id(resource.getId())
                .title(resource.getTitle())
                .author_id(resource.getAuthor_id())
                .build();
    }

    @Override
    public BookEntity convertFromModelToSource(Book model) {
        return BookEntity.builder()
                .id(model.getId())
                .title(model.getTitle())
                .author_id(model.getAuthor_id())
                .build();
    }
}
