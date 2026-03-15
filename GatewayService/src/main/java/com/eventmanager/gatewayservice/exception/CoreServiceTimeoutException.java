package com.eventmanager.gatewayservice.exception;

public class CoreServiceTimeoutException extends RuntimeException {
    public CoreServiceTimeoutException(String message) {
        super(message);
    }
}
