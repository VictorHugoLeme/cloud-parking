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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<ParkingDto> findById(@PathVariable UUID id) {
            return ResponseEntity.ok(parkingService.findById(id));
    }

    @ApiOperation(value = "Create parking")
    @PostMapping("/")
    public ResponseEntity<ParkingDto> create(@Valid @RequestBody ParkingCreationDto newParking) {
        return new ResponseEntity<>(parkingService.create(newParking), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update parking")
    @PutMapping("/{id}")
    public ResponseEntity<ParkingDto> update(@PathVariable UUID id, @RequestBody ParkingCreationDto newParking) {
        return ResponseEntity.ok(parkingService.update(id, newParking));
    }

    @ApiOperation(value = "Delete parking by id")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable UUID id) {
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Exit the parking lot")
    @PostMapping("/{id}/exit")
    public ResponseEntity<ParkingDto> exit(@PathVariable UUID id) {
        return ResponseEntity.ok(parkingService.exit(id));
    }

}
