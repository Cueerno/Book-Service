package com.radiuk.book_storage_service.repository;

import com.radiuk.book_storage_service.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

@DataJpaTest
@AutoConfigureTestDatabase
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeTestClass
    public void createBook() {
        book = new Book(1, "11113311", "Book 1", "Fiction", "Description 1", "Author 1");

        bookRepository.save(book);
    }

    @Test
    public void BookRepository_FindById_ReturnBook() {
        Book bookToCheck = bookRepository.findById(book.getId()).get();

        Assertions.assertThat(bookToCheck).isNotNull();
    }

    @Test
    public void BookRepository_FindByIsbn_ReturnBook() {
        Book bookToCheck = bookRepository.findByIsbn(book.getIsbn()).get();

        Assertions.assertThat(bookToCheck).isNotNull();
    }
}
