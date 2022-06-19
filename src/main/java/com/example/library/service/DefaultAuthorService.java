package com.example.library.service;

import com.example.library.converter.AuthorModelEntityConverter;
import com.example.library.entity.AuthorEntity;
import com.example.library.exceptions.*;
import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
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
public class DefaultAuthorService implements AuthorService{

    public static final AtomicInteger Author_Id_Holder = new AtomicInteger();

    @Autowired

    private final AuthorRepository authorRepository;
    private final AuthorModelEntityConverter authorModelEntityConverter;

    @Override
    public void createAuthor(Author author) {
        AuthorEntity authorEntity = authorModelEntityConverter.convertFromModelToSource(author);
        authorEntity.setId(Author_Id_Holder.incrementAndGet());
        authorRepository.save(authorEntity);
        log.info("Created author: id: {}, name: {}, surname: {}",authorEntity.getId(), authorEntity.getName(), authorEntity.getSurname());
    }

    @Override
    public Author getAuthor(long id) {
        Optional<AuthorEntity> authorEntityData = authorRepository.findById(id);
        if (authorEntityData.isEmpty() ) {
            log.error("Author with id: {} not found", id);
            throw new AuthorNotFoundException("Author not found");
        } else {
            Author author = authorModelEntityConverter.convertFromSourceToModel(authorEntityData.get());
            log.info("Get author: id: {}, name: {}, surname: {}",author.getId(),author.getName(),author.getSurname());
            return author;
        }
    }

    @Override
    public Author getAuthorWoLog(long id) {
        Optional<AuthorEntity> authorEntityData = authorRepository.findById(id);
        if (authorEntityData.isEmpty() ) {
            throw new AuthorNotFoundException("Author not found");
        } else {
            return authorModelEntityConverter.convertFromSourceToModel(authorEntityData.get());
        }
    }

    @Override
    public boolean checkAuthorPresence(long id) {
        Optional<AuthorEntity> authorEntityData = authorRepository.findById(id);
        if (authorEntityData.isEmpty() ) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void updateAuthor(Author author, long id) {
        Optional<AuthorEntity> authorEntityData = authorRepository.findById(id);
        if (authorEntityData.isPresent()) {
            Author _author = authorModelEntityConverter.convertFromSourceToModel(authorEntityData.get());
            _author.setName(author.getName());
            _author.setSurname(author.getSurname());
            AuthorEntity authorEntity = authorModelEntityConverter.convertFromModelToSource(_author);
            authorRepository.save(authorEntity);
            log.info("Updated author: id: {}, name: {}, surname: {}", authorEntity.getId(), authorEntity.getName(), authorEntity.getSurname());
        }
        else {
            log.error("Error updating Author with id: {}",id);
            throw new AuthorNotUpdatedException("Author not found");
        }
    }

    @Override
    public void deleteAuthor(long id) {
        Optional<AuthorEntity> authorEntityData = authorRepository.findById(id);
        if (authorEntityData.isEmpty() ) {
            log.error("Error deleting Author with id: {}",id);
            throw new AuthorNotDeletedException("Author not found");
        } else {
            authorRepository.deleteById(id);
            log.info("Deleted author with: id: {}",id);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        List<AuthorEntity> authorEntities = new ArrayList<>(authorRepository.findAll());
        if (!authorEntities.isEmpty()){
            log.info("Requested all authors");
            return authorEntities.stream()
                    .map(authorModelEntityConverter::convertFromSourceToModel)
                    .collect(Collectors.toList());
        }
        else {
            log.error("There are no Authors found");
            throw new NoAuthorsFoundException("There are no authors");
        }
    }
}
