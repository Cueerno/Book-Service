package com.radiuk.book_tracker_service.controller;

import com.radiuk.book_storage_service.client.BookTrackerRequest;
import com.radiuk.book_storage_service.dto.BookDTO;
import com.radiuk.book_storage_service.exception.BookErrorResponse;
import com.radiuk.book_storage_service.exception.BookNotCreatedException;
import com.radiuk.book_storage_service.exception.BookNotFoundException;
import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_tracker_service.exception.BookAlreadyTakenException;
import com.radiuk.book_tracker_service.exception.BookNotAvailableException;
import com.radiuk.book_tracker_service.exception.NoAvailableBooksException;
import com.radiuk.book_tracker_service.service.BookTrackerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tracker/books")
public class BookTrackerController {

    private final ModelMapper modelMapper;
    private final BookTrackerService bookTrackerService;

    @Autowired
    public BookTrackerController(ModelMapper modelMapper, BookTrackerService bookTrackerService) {
        this.modelMapper = modelMapper;
        this.bookTrackerService = bookTrackerService;
    }


    @GetMapping()
    public ResponseEntity<List<BookDTO>> getAvailableBooks() {
        return ResponseEntity.ok(bookTrackerService.findAvailableBooks().stream().map(this::convertToBookDTO).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity<Void> newEntryFromBookStorage(@RequestBody BookTrackerRequest bookTrackerRequest) {

        bookTrackerService.create(bookTrackerRequest.getBookId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/borrow")
    public ResponseEntity<Void> borrowBook(@PathVariable int id) {

        bookTrackerService.borrow(id);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<Void> returnBook(@PathVariable int id) {

        bookTrackerService.returnBook(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        bookTrackerService.delete(id);

        return ResponseEntity.ok().build();
    }


    @ExceptionHandler(Exception.class)
    private ResponseEntity<BookErrorResponse> handleException(BookNotCreatedException exception) {
        BookErrorResponse response = new BookErrorResponse(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAvailableBooksException.class)
    private ResponseEntity<BookErrorResponse> handleException(NoAvailableBooksException exception) {
        BookErrorResponse response = new BookErrorResponse(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(BookNotFoundException.class)
    private ResponseEntity<BookErrorResponse> handleException(BookNotFoundException exception) {
        BookErrorResponse response = new BookErrorResponse("Book with data not found", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyTakenException.class)
    private ResponseEntity<BookErrorResponse> handleException(BookAlreadyTakenException exception) {
        BookErrorResponse response = new BookErrorResponse(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookNotAvailableException.class)
    private ResponseEntity<BookErrorResponse> handleException(BookNotAvailableException exception) {
        BookErrorResponse response = new BookErrorResponse(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    public BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }
}
