package com.radiuk.book_storage_service.controller;

import com.radiuk.book_storage_service.client.BookTrackerClient;
import com.radiuk.book_storage_service.client.BookTrackerRequest;
import com.radiuk.book_storage_service.dto.BookDTO;
import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_storage_service.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<BookDTO> getAllBooks() {
        return bookService.findAll().stream().map(this::convertToBookDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable int id) {
        return convertToBookDTO(bookService.findById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public BookDTO getBookByIsbn(@PathVariable String isbn) {
        return convertToBookDTO(bookService.findByIsbn(isbn));
    }

    @PostMapping()
    public HttpStatus createBook(@RequestBody BookDTO bookDTO) {

        Book bookToSave = convertToBook(bookDTO);

        bookService.create(bookToSave);
        bookTrackerClient.createBookTracker(new BookTrackerRequest(bookToSave.getId()));

        return HttpStatus.OK;
    }

    @PatchMapping("/{id}")
    public HttpStatus updateBook(@RequestBody BookDTO bookDTO, @PathVariable int id) {

        bookService.update(convertToBook(bookDTO), id);

        return HttpStatus.OK;
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteBook(@PathVariable int id) {

        bookTrackerClient.deleteBookTracker(bookService.findById(id).getId());

        return HttpStatus.OK;
    }


    public Book convertToBook(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }

    public BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }
}
