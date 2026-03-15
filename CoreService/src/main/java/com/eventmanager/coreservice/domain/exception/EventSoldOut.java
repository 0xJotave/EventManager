package com.eventmanager.coreservice.domain.exception;

public class EventSoldOut extends RuntimeException {
    public EventSoldOut(String message) {
        super(message);
    }
}
