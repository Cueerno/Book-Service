package com.radiuk.book_tracker_service.repository;

import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_storage_service.repository.BookRepository;
import com.radiuk.book_tracker_service.model.BookTracker;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
public class BookTrackerRepositoryTest {

    @Autowired
    private BookTrackerRepository bookTrackerRepository;

    private Book book;
    private BookTracker bookTracker;

    @BeforeEach
    public void createBookTracker() {
        book = new Book(1, "11113311", "Book 1", "Fiction", "Description 1", "Author 1");

        bookTracker = new BookTracker();
        bookTracker.setBookId(book.getId());
        bookTracker.setStatus("Available");
    }


    @Test
    public void testFindAvailableBooks() {
        List<Book> availableBooks = bookTrackerRepository.findAvailableBooks();
        assertThat(availableBooks).hasSize(1);
        assertThat(availableBooks.get(0).getId()).isEqualTo(book.getId());
    }

    @Test
    public void BookRepository_FindByBookId_ReturnBook() {
        BookTracker bookTracker1 = bookTrackerRepository.findById(bookTracker.getBookId()).get();

        Assertions.assertThat(bookTracker1).isNotNull();
    }
}
