package com.radiuk.book_storage_service.service.impl;

import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_storage_service.repository.BookRepository;
import com.radiuk.book_storage_service.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(int id) {
        return bookRepository.findById(id).get();
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).get();
    }

    @Transactional
    public void create(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(Book updatedBook, int id) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }
}
