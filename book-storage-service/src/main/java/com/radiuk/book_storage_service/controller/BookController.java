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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/book/storage")
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


    @GetMapping("/all")
    public List<BookDTO> getBooks() {
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

    @PostMapping("/new")
    public HttpStatus createBook(@RequestBody BookDTO bookDTO) {

        Book bookToSave = convertToBook(bookDTO);

        bookService.create(bookToSave);
        bookTrackerClient.createBookTracker(new BookTrackerRequest(bookToSave.getId()));

        return HttpStatus.OK;
    }

//    @PutMapping("/{id}/full-update")
//    public HttpStatus fullUpdateBook(@RequestBody BookDTO bookDTO, @PathVariable int id) {
//
//        bookService.update(convertToBook(bookDTO), id);
//
//        return HttpStatus.OK;
//    }

    @PatchMapping("/{id}/update")
    public HttpStatus updateBook(@RequestBody BookDTO bookDTO, @PathVariable int id) {

        bookService.update(convertToBook(bookDTO), id);

        return HttpStatus.OK;
    }

    @DeleteMapping("/{id}/delete")
    public HttpStatus deleteBook(@PathVariable int id) {
        Book book = bookService.findById(id);

        bookTrackerClient.deleteBookTracker(book.getId());
        //bookService.delete(id); // hard delete

        return HttpStatus.OK;
    }


    public Book convertToBook(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }

    public BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }
}
