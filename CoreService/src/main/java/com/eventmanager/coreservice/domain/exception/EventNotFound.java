package com.eventmanager.coreservice.domain.exception;

public class EventNotFound extends RuntimeException {
    public EventNotFound(String message) {
        super("[ERROR] " + message);
    }
}
