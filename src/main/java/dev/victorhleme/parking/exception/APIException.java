package dev.victorhleme.parking.exception;

import lombok.Getter;

public class APIException extends RuntimeException {
    @Getter
    private final int code;
    @Getter
    private final String message;

    public APIException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

