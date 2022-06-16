package com.example.library.converter;

import com.example.library.entity.AuthorEntity;
import com.example.library.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorModelEntityConverter implements Converter <AuthorEntity,Author> {

    @Override
    public Author convertFromSourceToModel(AuthorEntity resource) {
        return Author.builder()
                .id(resource.getId())
                .name(resource.getName())
                .surname(resource.getSurname())
                .build();
    }

    @Override
    public AuthorEntity convertFromModelToSource(Author model) {
        return AuthorEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .surname(model.getSurname())
                .build();
    }
}
