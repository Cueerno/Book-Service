package com.radiuk.book_storage_service.service.impl;

import com.radiuk.book_storage_service.exception.BookNotFoundException;
import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_storage_service.repository.BookRepository;
import com.radiuk.book_storage_service.service.BookService;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book findById(int id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
    }

    public void create(Book book) {
        bookRepository.save(book);
    }

    public void update(Book updatedBook, int id) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }
}
