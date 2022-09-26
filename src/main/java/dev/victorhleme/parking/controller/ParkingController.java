package dev.victorhleme.parking.controller;

import dev.victorhleme.parking.dto.ParkingCreationDto;
import dev.victorhleme.parking.dto.ParkingDto;
import dev.victorhleme.parking.service.ParkingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/parking")
@Api(tags = "Parking Controller")
public class ParkingController {

    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @ApiOperation(value = "Find all parking")
    @GetMapping("/")
    public ResponseEntity<List<ParkingDto>> findAll() {
        return ResponseEntity.ok(parkingService.findAll());
    }

    @ApiOperation(value = "Find parking by id")
    @GetMapping("/{id}")
    public ResponseEntity<ParkingDto> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(parkingService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Create parking")
    @PostMapping("/")
    public ResponseEntity<ParkingDto> create(@RequestBody ParkingCreationDto newParking) {
        return new ResponseEntity<>(parkingService.create(newParking), HttpStatus.CREATED);
    }
}
