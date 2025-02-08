package com.radiuk.book_storage_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "book_storage")
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


    public Book() {}

    public Book(Integer id, String isbn, String title, String genre, String description, String author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.author = author;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
