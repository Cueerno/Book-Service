package com.radiuk.book_tracker_service.exception;

public class BookAlreadyTakenException extends RuntimeException {
    public BookAlreadyTakenException(String message) {
        super(message);
    }
}
