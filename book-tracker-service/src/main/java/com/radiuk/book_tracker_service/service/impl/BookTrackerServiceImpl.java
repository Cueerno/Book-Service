package com.radiuk.book_tracker_service.service.impl;

import com.radiuk.book_storage_service.exception.BookNotFoundException;
import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_tracker_service.exception.BookAlreadyTakenException;
import com.radiuk.book_tracker_service.exception.BookNotAvailableException;
import com.radiuk.book_tracker_service.exception.NoAvailableBooksException;
import com.radiuk.book_tracker_service.model.BookTracker;
import com.radiuk.book_tracker_service.repository.BookTrackerRepository;
import com.radiuk.book_tracker_service.service.BookTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class BookTrackerServiceImpl implements BookTrackerService {

    private final BookTrackerRepository bookTrackerRepository;


    @Transactional(readOnly = true)
    public List<Book> findAvailableBooks() {
        List<Book> availableBooks = bookTrackerRepository.findAvailableBooks();

        if (availableBooks.isEmpty()) {
            System.out.println("No available books found");
            throw new NoAvailableBooksException("No available books found.");
        }

        return bookTrackerRepository.findAvailableBooks();
    }

    public void create(int id) {
        BookTracker bookTracker = new BookTracker();
        bookTracker.setBookId(id);
        bookTracker.setStatus("Available");

        bookTrackerRepository.save(bookTracker);
    }

    public void borrow(int id) {
        BookTracker bookTracker = new BookTracker();
        BookTracker bookToBorrow = bookTrackerRepository.findByBookId(id).orElseThrow(BookNotFoundException::new);

        if (Objects.equals(bookToBorrow.getStatus(), "Borrowed")) {
            throw new BookNotAvailableException("Book with id " + id + " is already borrowed.");
        }

        bookTracker.setId(bookToBorrow.getId());
        bookTracker.setBookId(bookToBorrow.getBookId());

        bookTracker.setStatus("Borrowed");
        bookTracker.setBorrowedAt(LocalDateTime.now());
        bookTracker.setReturnAt(LocalDateTime.now().plusDays(30));

        bookTrackerRepository.save(bookTracker);
    }

    public void returnBook(int id) {
        BookTracker bookTracker = new BookTracker();
        BookTracker bookToReturn = bookTrackerRepository.findByBookId(id).orElseThrow(BookNotFoundException::new);

        if (Objects.equals(bookToReturn.getStatus(), "Available")) {
            throw new BookAlreadyTakenException("Book with id " + id + " is already available.");
        }

        bookTracker.setId(bookToReturn.getId());
        bookTracker.setBookId(bookToReturn.getBookId());

        bookTracker.setStatus("Available");
        bookTracker.setBorrowedAt(null);
        bookTracker.setReturnAt(null);

        bookTrackerRepository.save(bookTracker);
    }

    public void delete(int id) {
        BookTracker bookTrackerToDelete = bookTrackerRepository.findByBookId(id).orElseThrow(BookNotFoundException::new);

        bookTrackerToDelete.setStatus("Deleted");
        bookTrackerRepository.save(bookTrackerToDelete);
    }
}
