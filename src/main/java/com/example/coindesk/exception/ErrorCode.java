package com.example.coindesk.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // Common errors (1000-1999)
    INTERNAL_SERVER_ERROR(1000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    INVALID_REQUEST_PARAMETER(1001, HttpStatus.BAD_REQUEST, "Invalid request parameter"),
    VALIDATION_ERROR(1002, HttpStatus.BAD_REQUEST, "Validation error: %s"),

    // Currency related errors (2000-2999)
    CURRENCY_NOT_FOUND(2000, HttpStatus.NOT_FOUND, "Currency not found with id: %s"),
    DUPLICATE_CURRENCY_CODE(2001, HttpStatus.CONFLICT, "Currency with code %s already exists"),

    // External API related errors (3000-3999)
    COINDESK_API_ERROR(3000, HttpStatus.SERVICE_UNAVAILABLE, "Failed to fetch data from Coindesk API"),
    EXTERNAL_API_TIMEOUT(3001, HttpStatus.GATEWAY_TIMEOUT, "External API request timeout");

    private final int code;
    private final HttpStatus status;
    private final String messageTemplate;

    ErrorCode(int code, HttpStatus status, String messageTemplate) {
        this.code = code;
        this.status = status;
        this.messageTemplate = messageTemplate;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public String formatMessage(Object... args) {
        return String.format(messageTemplate, args);
    }
} 