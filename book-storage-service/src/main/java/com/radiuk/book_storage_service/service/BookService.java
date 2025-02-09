package com.radiuk.book_storage_service.service;


import com.radiuk.book_storage_service.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();
    Book findById(int id);
    Book findByIsbn(String isbn);
    void create(Book book);
    void update(Book updatedBook, int id);
    // void delete(int id);
}
