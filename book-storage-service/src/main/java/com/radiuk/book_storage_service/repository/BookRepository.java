package com.radiuk.book_storage_service.repository;

import com.radiuk.book_storage_service.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByIsbn(String isbn);
}
