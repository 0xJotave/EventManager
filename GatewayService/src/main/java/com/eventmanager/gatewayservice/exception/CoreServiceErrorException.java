package com.eventmanager.gatewayservice.exception;

public class CoreServiceErrorException extends RuntimeException {
    public CoreServiceErrorException(String message) {
        super(message);
    }
}
