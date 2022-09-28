package dev.victorhleme.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class VehicleAlreadyLeftException extends RuntimeException {
    public VehicleAlreadyLeftException(UUID id) {
        super("Vehicle already left with id: " + id);
    }
}
