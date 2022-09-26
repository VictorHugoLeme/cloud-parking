package dev.victorhleme.parking.exception;

public class DataNotFoundException extends APIException {
    public DataNotFoundException(String message) {
        super(404, message);
    }
}

