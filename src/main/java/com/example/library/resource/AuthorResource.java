package com.example.library.resource;

import javax.persistence.Column;

public class AuthorResource {
    private long id;
    private String name;
    private String surname;

    public AuthorResource(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}
