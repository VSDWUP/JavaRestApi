package com.example.library.controller;

import com.example.library.converter.AuthorResourceModelConverter;
import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import com.example.library.resource.AuthorResource;
import com.example.library.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.library.service.DefaultAuthorService.Author_Id_Holder;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@AllArgsConstructor
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;
    private final AuthorService authorService;
    private final AuthorResourceModelConverter converter;


    @PostMapping(value = "/author")
    public AuthorResource create(@RequestBody AuthorResource authorResource){
        Author author = converter.convertFromSourceToModel(authorResource);
        authorService.createAuthor(author);
        return converter.convertFromModelToSource(authorService.getAuthorWoLog(Author_Id_Holder.get()));
    }

    @GetMapping(value = "/author/{id}")
    public AuthorResource get(@PathVariable(value = "id") long id){
        final Author author = authorService.getAuthor(id);
        return converter.convertFromModelToSource(author);
    }

    @PutMapping(value = "/author/{id}")
    public AuthorResource update(@PathVariable(value = "id") int id, @RequestBody AuthorResource authorResource){
        Author author = converter.convertFromSourceToModel(authorResource);
        authorService.updateAuthor(author,id);
        return converter.convertFromModelToSource(authorService.getAuthorWoLog(id));
    }


    @DeleteMapping(value = "/author/{id}")
    public ResponseEntity <?> delete(@PathVariable(value = "id") int id){
        Author author =  authorService.getAuthorWoLog(id);
        authorService.deleteAuthor(id);
        String responseMessage = String.format("Deleted Author: id: %s, name: %s, surname: %s", author.getId(), author.getName(), author.getSurname());
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping(value = "/authors")
    public List<AuthorResource> getAuthors(){
        return authorService.getAllAuthors().stream()
                .map(converter::convertFromModelToSource)
                .collect(Collectors.toList());
    }


}
