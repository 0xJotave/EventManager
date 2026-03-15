package com.eventmanager.coreservice.domain.exception;

public class InvalidEventData extends RuntimeException {
    public InvalidEventData(String message) {
        super(message);
    }
}
