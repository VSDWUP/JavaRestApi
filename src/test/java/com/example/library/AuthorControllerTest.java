/*
package com.example.library;

import com.example.library.controller.AuthorController;
import com.example.library.model.Author;
import com.example.library.service.AuthorService;
import com.example.library.service.AuthorServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest
public class AuthorControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AuthorService authorService = new AuthorServiceImpl();
    @Autowired
    AuthorController authorController = new AuthorController(authorService);

    @Test
    public void AuthorPostWhenCreated_thenStatus201andAuthorReturned () throws Exception {
        Author author = new Author(1,"Sergey","Minaev");
        Mockito.when(authorController.create(Mockito.any())).thenReturn;


        mockMvc.perform(post("/author").content(objectMapper.writeValueAsString(author))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Sergey"))
                .andExpect(jsonPath("$.surname").value("Minaev"));

    }


}
*/
