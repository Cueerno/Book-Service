package com.radiuk.book_tracker_service.exception;

public class NoAvailableBooksException extends RuntimeException {
    public NoAvailableBooksException(String message) {
        super(message);
    }
}
