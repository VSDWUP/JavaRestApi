package com.example.library.controller;

import com.example.library.converter.AuthorResourceModelConverter;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.resource.AuthorResource;
import com.example.library.resource.BookResource;
import com.example.library.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.library.service.AuthorServiceImpl.Author_Id_Holder;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@AllArgsConstructor
public class AuthorController {
    @Autowired
    AuthorRepository authorRepository;
    private final AuthorService authorService;
    private static final Logger log = Logger.getLogger(AuthorController.class);
    private final AuthorResourceModelConverter converter;


    @PostMapping(value = "/author")
    public AuthorResource create(@RequestBody AuthorResource authorResource){
        Author author = converter.convertFromSourceToModel(authorResource);
        authorService.createAuthor(author);
        //Author author_return = authorService.getAuthor(Author_Id_Holder.get());
        AuthorResource return_AuthorResource = converter.convertFromModelToSource(authorService.getAuthor(Author_Id_Holder.get()));
        return return_AuthorResource;
    }

    @GetMapping(value = "/author/{id}")
    public AuthorResource get(@PathVariable(value = "id") long id){
        final Author author = authorService.getAuthor(id);
        AuthorResource return_AuthorResource = converter.convertFromModelToSource(author);
        return return_AuthorResource;

    }

    @PutMapping(value = "/author/{id}")
    public AuthorResource update(@PathVariable(value = "id") int id, @RequestBody AuthorResource authorResource){
        Author author = converter.convertFromSourceToModel(authorResource);
        authorService.updateAuthor(author,id);
        //Author author_return = authorService.getAuthor(id);
        AuthorResource return_AuthorResource = converter.convertFromModelToSource(authorService.getAuthor(id));
        return return_AuthorResource;

    }


    @DeleteMapping(value = "/author/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") int id){
        Author author =  authorService.getAuthor(id);
        authorService.deleteAuthor(id);
        String str = "Deleted Author:\n id: " + author.getId() + ",\n name: " + author.getName() + ",\n surname: " + author.getSurname();
        return new ResponseEntity<>(str,HttpStatus.OK);

    }

    @GetMapping(value = "/authors")
    public List<AuthorResource> getAuthors(){
        return authorService.getAllAuthors().stream().map(converter::convertFromModelToSource).collect(Collectors.toList());
    }


}
