package com.radiuk.book_tracker_service.repository;

import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_tracker_service.model.BookTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookTrackerRepository extends JpaRepository<BookTracker, Integer> {

    @Query(value = "SELECT bs.* FROM book_storage bs JOIN book_tracker bt ON bs.id = bt.book_id WHERE bt.status = 'Available'", nativeQuery = true)
    List<Book> findAvailableBooks();

    Optional<BookTracker> findByBookId(int id);
}
