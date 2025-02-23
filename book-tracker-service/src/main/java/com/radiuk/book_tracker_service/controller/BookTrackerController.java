package com.radiuk.book_tracker_service.controller;

import com.radiuk.book_storage_service.client.BookTrackerRequest;
import com.radiuk.book_storage_service.dto.BookDTO;
import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_tracker_service.service.BookTrackerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<BookDTO> getAvailableBooks() {
        return bookTrackerService.findAvailableBooks().stream().map(this::convertToBookDTO).collect(Collectors.toList());
    }

    @PostMapping()
    public HttpStatus newEntryFromBookStorage(@RequestBody BookTrackerRequest bookTrackerRequest) {

        bookTrackerService.create(bookTrackerRequest.getBookId());

        return HttpStatus.OK;
    }

    @PatchMapping("/{id}/borrow")
    public HttpStatus borrowBook(@PathVariable int id) {

        bookTrackerService.borrow(id);

        return HttpStatus.OK;
    }

    @PatchMapping("/{id}/return")
    public HttpStatus returnBook(@PathVariable int id) {

        bookTrackerService.returnBook(id);

        return HttpStatus.OK;
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable int id) {

        bookTrackerService.delete(id);

        return HttpStatus.OK;
    }


    public BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }
}
