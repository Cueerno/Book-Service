package com.radiuk.book_tracker_service.service;



import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_tracker_service.model.BookTracker;
import com.radiuk.book_tracker_service.repository.BookTrackerRepository;
import com.radiuk.book_tracker_service.service.impl.BookTrackerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookTrackerServiceTest {

    @Mock
    private BookTrackerRepository bookTrackerRepository;

    @InjectMocks
    private BookTrackerServiceImpl bookTrackerService;

    private Book book;
    private BookTracker bookTracker;

    @BeforeEach
    public void createBookAndTracker() {
        book = new Book(1, "11113311", "Book 1", "Fiction", "Description 1", "Author 1");
        bookTracker = new BookTracker();
        bookTracker.setBookId(1);
        bookTracker.setStatus("Available");
    }

    @Test
    public void findAvailableBooksTest() {
        List<Book> books = Arrays.asList(book);
        when(bookTrackerRepository.findAvailableBooks()).thenReturn(books);

        List<Book> availableBooks = bookTrackerService.findAvailableBooks();

        assertNotNull(availableBooks);
        assertEquals(1, availableBooks.size());
        verify(bookTrackerRepository, times(1)).findAvailableBooks();
    }

    @Test
    public void createTest() {
        bookTrackerService.create(1);

        ArgumentCaptor<BookTracker> bookTrackerCaptor = ArgumentCaptor.forClass(BookTracker.class);
        verify(bookTrackerRepository, times(1)).save(bookTrackerCaptor.capture());

        BookTracker savedBookTracker = bookTrackerCaptor.getValue();
        assertEquals(1, savedBookTracker.getBookId());
        assertEquals("Available", savedBookTracker.getStatus());
    }

    @Test
    public void borrowTest() {
        when(bookTrackerRepository.findById(1)).thenReturn(Optional.of(bookTracker));

        bookTrackerService.borrow(1);

        ArgumentCaptor<BookTracker> bookTrackerCaptor = ArgumentCaptor.forClass(BookTracker.class);
        verify(bookTrackerRepository, times(1)).save(bookTrackerCaptor.capture());

        BookTracker borrowedBookTracker = bookTrackerCaptor.getValue();
        assertEquals(1, borrowedBookTracker.getId());
        assertEquals("Borrowed", borrowedBookTracker.getStatus());
        assertNotNull(borrowedBookTracker.getBorrowedAt());
        assertNotNull(borrowedBookTracker.getReturnAt());
    }

    @Test
    public void returnBookTest() {
        when(bookTrackerRepository.findById(1)).thenReturn(Optional.of(bookTracker));

        bookTrackerService.returnBook(1);

        ArgumentCaptor<BookTracker> bookTrackerCaptor = ArgumentCaptor.forClass(BookTracker.class);
        verify(bookTrackerRepository, times(1)).save(bookTrackerCaptor.capture());

        BookTracker returnedBookTracker = bookTrackerCaptor.getValue();
        assertEquals(1, returnedBookTracker.getId());
        assertEquals("Available", returnedBookTracker.getStatus());
        assertNull(returnedBookTracker.getBorrowedAt());
        assertNull(returnedBookTracker.getReturnAt());
    }

    @Test
    public void deleteTest() {
        when(bookTrackerRepository.findByBookId(1)).thenReturn(Optional.of(bookTracker));

        bookTrackerService.delete(1);

        verify(bookTrackerRepository, times(1)).save(bookTracker);

        assertEquals(1, bookTracker.getBookId());
        assertEquals("Deleted", bookTracker.getStatus());
    }
}
