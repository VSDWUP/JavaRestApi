package com.example.library.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class BookResource {
    @JsonProperty(value = "id")
    private long id;

    @JsonProperty (value = "title")
    private String title;

    @JsonProperty (value = "author")
    private String author;
}
