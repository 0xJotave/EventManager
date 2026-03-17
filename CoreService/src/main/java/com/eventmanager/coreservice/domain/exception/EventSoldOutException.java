package com.eventmanager.coreservice.domain.exception;

public class EventSoldOutException extends RuntimeException {
    public EventSoldOutException(String message) {
        super(message);
    }
}
