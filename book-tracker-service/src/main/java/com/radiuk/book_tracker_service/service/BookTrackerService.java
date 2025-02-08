package com.radiuk.book_tracker_service.service;

import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_tracker_service.model.BookTracker;
import com.radiuk.book_tracker_service.repository.BookTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BookTrackerService {

    private final BookTrackerRepository bookTrackerRepository;

    @Autowired
    public BookTrackerService(BookTrackerRepository bookTrackerRepository) {
        this.bookTrackerRepository = bookTrackerRepository;
    }


    @Transactional(readOnly = true)
    public List<Book> findAvailableBooks() {
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
        BookTracker bookToBorow = bookTrackerRepository.findById(id).get();

        bookTracker.setId(id);
        bookTracker.setBookId(bookToBorow.getBookId());

        bookTracker.setStatus("Borrowed");
        bookTracker.setBorrowedAt(LocalDateTime.now());
        bookTracker.setReturnAt(LocalDateTime.now().plusDays(30));

        bookTrackerRepository.save(bookTracker);
    }

    public void returnBook(int id) {
        BookTracker bookTracker = new BookTracker();
        BookTracker bookToReturn = bookTrackerRepository.findById(id).get();

        bookTracker.setId(id);
        bookTracker.setBookId(bookToReturn.getBookId());

        bookTracker.setStatus("Available");
        bookTracker.setBorrowedAt(null);
        bookTracker.setReturnAt(null);

        bookTrackerRepository.save(bookTracker);
    }

    public void delete(int id) {
        //bookTrackerRepository.deleteById(id); // hard delete

        BookTracker bookTrackerToDelete = bookTrackerRepository.findByBookId(id).get();

        bookTrackerToDelete.setStatus("Deleted"); // soft delete
        bookTrackerRepository.save(bookTrackerToDelete);
    }
}
