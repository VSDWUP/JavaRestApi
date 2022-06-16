package com.example.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;


@Entity
@Table(name = "BOOKS")
@EnableAutoConfiguration
@Builder
@AllArgsConstructor
@Getter
@Setter
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private long id;

    @Column(name = "title", length = 64, nullable = false)
    private String title;

    @Column(name = "author", length = 64, nullable = false)
    private String author;

    public BookEntity() {

    }
}
