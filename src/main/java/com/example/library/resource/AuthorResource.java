package com.example.library.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



@AllArgsConstructor
@Builder
@Getter
@Setter
public class AuthorResource {

    @JsonProperty (value = "id")
    private long id;

    @JsonProperty (value = "name")
    private String name;

    @JsonProperty (value = "surname")
    private String surname;

}
