package com.radiuk.book_storage_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_storage")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    private String genre;

    private String description;

    @Column(nullable = false)
    private String author;
}
