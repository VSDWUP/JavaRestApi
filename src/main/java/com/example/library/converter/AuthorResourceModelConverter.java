package com.example.library.converter;

import com.example.library.model.Author;
import com.example.library.resource.AuthorResource;
import org.springframework.stereotype.Component;

@Component
public class AuthorResourceModelConverter implements Converter <AuthorResource,Author>{

    @Override
    public Author convertFromSourceToModel(AuthorResource resource) {
        return Author.builder()
                .id(resource.getId())
                .name(resource.getName())
                .surname(resource.getSurname())
                .build();
    }

    @Override
    public AuthorResource convertFromModelToSource(Author model) {
        return AuthorResource.builder()
                .id(model.getId())
                .name(model.getName())
                .surname(model.getSurname())
                .build();
    }
}
