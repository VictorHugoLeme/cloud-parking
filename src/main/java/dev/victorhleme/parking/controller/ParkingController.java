package dev.victorhleme.parking.controller;

import dev.victorhleme.parking.dto.ParkingDto;
import dev.victorhleme.parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/parking")
public class ParkingController {

    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ParkingDto>> findAll() {
        return ResponseEntity.ok(parkingService.findAll());
    }
}
