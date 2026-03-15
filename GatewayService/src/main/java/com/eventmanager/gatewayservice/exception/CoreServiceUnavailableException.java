package com.eventmanager.gatewayservice.exception;

public class CoreServiceUnavailableException extends RuntimeException {
    public CoreServiceUnavailableException(String message) {
        super(message);
    }
}
