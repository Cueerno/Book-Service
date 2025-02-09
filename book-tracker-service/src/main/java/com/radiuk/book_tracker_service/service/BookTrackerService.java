package com.radiuk.book_tracker_service.service;

import com.radiuk.book_storage_service.model.Book;

import java.util.List;

public interface BookTrackerService {

    List<Book> findAvailableBooks();
    void create(int id);
    void borrow(int id);
    void returnBook(int id);
    void delete(int id);
}
