package com.radiuk.book_storage_service.exception;

import java.time.LocalDateTime;

public class BookErrorResponse {
    private String message;

    private LocalDateTime messageTime;

    public BookErrorResponse(String message, LocalDateTime messageTime) {
        this.message = message;
        this.messageTime = messageTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }
}
