package com.radiuk.book_storage_service.controller;

import com.radiuk.book_storage_service.client.BookTrackerClient;
import com.radiuk.book_storage_service.client.BookTrackerRequest;
import com.radiuk.book_storage_service.dto.BookDTO;
import com.radiuk.book_storage_service.exception.BookErrorResponse;
import com.radiuk.book_storage_service.exception.BookNotCreatedException;
import com.radiuk.book_storage_service.exception.BookNotFoundException;
import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_storage_service.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final ModelMapper modelMapper;
    private final BookService bookService;
    private final BookTrackerClient bookTrackerClient;

    @Autowired
    public BookController(ModelMapper modelMapper, BookService bookService, BookTrackerClient bookTrackerClient) {
        this.modelMapper = modelMapper;
        this.bookService = bookService;
        this.bookTrackerClient = bookTrackerClient;
    }


    @GetMapping()
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findAll().stream().map(this::convertToBookDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id) {
        return ResponseEntity.ok(convertToBookDTO(bookService.findById(id)));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(convertToBookDTO(bookService.findByIsbn(isbn)));
    }

    @PostMapping()
    public ResponseEntity<Void> createBook(@RequestBody BookDTO bookDTO) {

        Book bookToSave = convertToBook(bookDTO);

        bookService.create(bookToSave);
        bookTrackerClient.createBookTracker(new BookTrackerRequest(bookToSave.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateBook(@RequestBody BookDTO bookDTO, @PathVariable int id) {

        bookService.update(convertToBook(bookDTO), id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {

        bookTrackerClient.deleteBookTracker(bookService.findById(id).getId());

        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(BookNotFoundException.class)
    private ResponseEntity<BookErrorResponse> handleException(BookNotFoundException exception) {
        BookErrorResponse response = new BookErrorResponse("Book with this data not found", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<BookErrorResponse> handleException(BookNotCreatedException exception) {
        BookErrorResponse response = new BookErrorResponse(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    public Book convertToBook(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }

    public BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }
}
