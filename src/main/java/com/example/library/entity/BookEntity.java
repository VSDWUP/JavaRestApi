package com.example.library.entity;

import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;


@Entity
@Table(name = "BOOKS")
@EnableAutoConfiguration
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookEntity {
    @Id
    @Column(name = "id",nullable = false)
    private long id;

    @Column(name = "title", length = 64, nullable = false)
    private String title;

    @Column(name = "author_id", length = 64, nullable = false)
    private long author_id;

}
