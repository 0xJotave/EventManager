package com.eventmanager.gatewayservice.exception;

public class InvalidGatewayRequestException extends RuntimeException {
    public InvalidGatewayRequestException(String message) {
        super(message);
    }
}
