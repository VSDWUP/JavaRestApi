package com.example.library;

import com.example.library.controller.AuthorController;
import com.example.library.converter.AuthorResourceModelConverter;
import com.example.library.exceptions.AuthorNotFoundException;
import com.example.library.exceptions.NoAuthorsFoundException;
import com.example.library.model.Author;
import com.example.library.resource.AuthorResource;
import com.example.library.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthorControllerTest {

    private AuthorController authorController;
    private AuthorService authorService;
    private AuthorResourceModelConverter authorConverter;

    @BeforeEach
    public void init(){
        this.authorService = mock(AuthorService.class);
        this.authorConverter = mock(AuthorResourceModelConverter.class);
        this.authorController = new AuthorController(authorService, authorConverter);
    }

    @Test
    public void createAuthor(){
        AuthorResource authorResource = AuthorResource.builder()
                .name("Sergey")
                .surname("Minaev")
                .build();

        Author author = Author.builder()
                .name("Sergey")
                .surname("Minaev")
                .build();

        when(authorConverter.convertFromSourceToModel(any())).thenReturn(author);
        when(authorConverter.convertFromModelToSource(any())).thenReturn(authorResource);

        AuthorResource response = authorController.create(authorResource);
        assertEquals(response, authorResource);
        verify(authorConverter, times(1)).convertFromModelToSource(any());
        verify(authorConverter, times(1)).convertFromSourceToModel(any());
        verify(authorService, times(1)).createAuthor(any());

    }

    @Test
    public void getAuthor(){
        AuthorResource authorResource = AuthorResource.builder()
                .name("Sergey")
                .surname("Minaev")
                .build();

        Author author = Author.builder()
                .name("Sergey")
                .surname("Minaev")
                .build();

        when(authorConverter.convertFromSourceToModel(any())).thenReturn(author);
        when(authorConverter.convertFromModelToSource(any())).thenReturn(authorResource);
        when(authorService.getAuthor(anyLong())).thenReturn(author);

        AuthorResource created = authorController.create(authorResource);

        AuthorResource response = authorController.get(created.getId());

        assertEquals(created,response);
        verify(authorConverter, times(2)).convertFromModelToSource(any());
        verify(authorConverter, times(1)).convertFromSourceToModel(any());
        verify(authorService, times(1)).getAuthor(anyLong());
    }

    @Test
    public void getAuthorWithException(){
        try {
            authorController.get(9323);
        }
        catch (Exception e){
            String actualMessage = e.getMessage();
            String exceptionMessage = "Author not found";
            assertEquals(exceptionMessage,actualMessage);
            assertTrue(e instanceof NoAuthorsFoundException);
            verify(authorConverter, times(2)).convertFromModelToSource(any());
            verify(authorConverter, times(1)).convertFromSourceToModel(any());
            verify(authorService, times(1)).getAuthorWoLog(anyLong());
        }
    }

    @Test
    public void updateAuthor() {
        AuthorResource authorResource = AuthorResource.builder()
                .name("Victor")
                .surname("Pelevin")
                .build();

        Author author = Author.builder()
                .name("Victor")
                .surname("Pelevin")
                .build();


        when(authorConverter.convertFromSourceToModel(any())).thenReturn(author);
        when(authorConverter.convertFromModelToSource(any())).thenReturn(authorResource);

        AuthorResource response = authorController.update(author.getId(),authorResource);

        assertEquals(0,response.getId());
        verify(authorConverter, times(1)).convertFromModelToSource(any());
        verify(authorConverter, times(1)).convertFromSourceToModel(any());
        verify(authorService, times(1)).updateAuthor(any(),anyLong());
    }

    @Test
    public void updateAuthorWithException(){
        Author author = Author.builder()
                .name("Victor")
                .surname("Pelevin")
                .build();
        try {
            authorService.updateAuthor(author,97819);
        }
        catch (Exception e){
            String actualMessage = e.getMessage();
            String exceptionMessage = "Author not found";
            assertEquals(exceptionMessage,actualMessage);
            assertTrue(e instanceof AuthorNotFoundException);
            verify(authorConverter, times(2)).convertFromModelToSource(any());
            verify(authorConverter, times(1)).convertFromSourceToModel(any());
            verify(authorService, times(1)).updateAuthor(any(),anyLong());
        }
    }

    @Test
    public void deleteAuthor(){
        AuthorResource authorResource = AuthorResource.builder()
                .name("Sergey")
                .surname("Minaev")
                .build();

        Author author = Author.builder()
                .name("Sergey")
                .surname("Minaev")
                .build();

        when(authorConverter.convertFromSourceToModel(any())).thenReturn(author);
        when(authorConverter.convertFromModelToSource(any())).thenReturn(authorResource);

        AuthorResource created = authorController.create(authorResource);

        ResponseEntity<?> response = authorController.delete(created.getId());

        assertEquals(String.format("Successfully deleted author with id:%d",created.getId()),response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authorConverter, times(1)).convertFromModelToSource(any());
        verify(authorConverter, times(1)).convertFromSourceToModel(any());
        verify(authorService,times(1)).deleteAuthor(anyLong());
    }

    @Test
    public void deleteAuthorWithException(){
        Long id = 8678l;
        ResponseEntity<?> response = authorController.delete(id);

        assertEquals(String.format("Successfully deleted author with id:%d",id),response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authorService, times(1)).deleteAuthor(anyLong());
    }

    @Test
    public void getAuthors(){
        Author author = Author.builder()
                .name("Alexander")
                .surname("Tsipkin")
                .build();

        AuthorResource authorResource = AuthorResource.builder()
                .name("Alexander")
                .surname("Tsipkin")
                .build();

        when(authorConverter.convertFromSourceToModel(any())).thenReturn(author);
        when(authorConverter.convertFromModelToSource(any())).thenReturn(authorResource);
        when(authorService.getAllAuthors()).thenReturn(Collections.singletonList(author));

        authorController.create(authorResource);

        List<AuthorResource> authorResourceList = new ArrayList<>();
        authorResourceList.add(authorResource);

        List<AuthorResource> result = authorController.getAuthors();

        assertEquals(authorResourceList,result);
        verify(authorConverter, times(2)).convertFromModelToSource(any());
        verify(authorConverter, times(1)).convertFromSourceToModel(any());
        verify(authorService, times(1)).getAllAuthors();

    }


    @Test
    public void getAuthorsWithException(){
        List<AuthorResource> authorResourceList = new ArrayList<>();
        List<AuthorResource> result = authorController.getAuthors();
        try {
           authorController.getAuthors();
        }
        catch (Exception e){
            String actualMessage = e.getMessage();
            String exceptionMessage = "No authors found";
            assertEquals(authorResourceList,result);
            assertEquals(exceptionMessage,actualMessage);
            assertTrue(e instanceof NoAuthorsFoundException);
            verify(authorConverter, times(2)).convertFromModelToSource(any());
            verify(authorConverter, times(1)).convertFromSourceToModel(any());
            verify(authorService, times(1)).getAllAuthors();
        }
    }
}
