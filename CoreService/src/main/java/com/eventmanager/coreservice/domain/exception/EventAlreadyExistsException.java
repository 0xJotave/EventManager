package com.eventmanager.coreservice.domain.exception;

public class EventAlreadyExistsException extends RuntimeException {
    public EventAlreadyExistsException(String message) {
        super(message);
    }
}
