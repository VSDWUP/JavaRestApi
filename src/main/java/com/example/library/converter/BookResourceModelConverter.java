package com.example.library.converter;

import com.example.library.model.Book;
import com.example.library.resource.BookResource;
import org.springframework.stereotype.Component;

@Component
public class BookResourceModelConverter implements Converter <BookResource,Book> {
    @Override
    public Book convertFromSourceToModel(BookResource resource) {
        return Book.builder()
                .id(resource.getId())
                .title(resource.getTitle())
                .author(resource.getAuthor())
                .build();
    }

    @Override
    public BookResource convertFromModelToSource(Book model) {
        return BookResource.builder()
                .id(model.getId())
                .title(model.getTitle())
                .author(model.getAuthor())
                .build();
    }

}
