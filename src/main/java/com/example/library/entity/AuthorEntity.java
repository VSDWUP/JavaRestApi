package com.example.library.entity;

import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;


@Entity
@Table(name = "AUTHORS")
@EnableAutoConfiguration
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorEntity {
    @Id
    @Column(name = "id",nullable = false)
    private long id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "surname", length = 64, nullable = false)
    private String surname;

}
