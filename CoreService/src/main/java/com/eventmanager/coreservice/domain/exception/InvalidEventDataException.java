package com.eventmanager.coreservice.domain.exception;

public class InvalidEventDataException extends RuntimeException {
    public InvalidEventDataException(String message) {
        super(message);
    }
}
