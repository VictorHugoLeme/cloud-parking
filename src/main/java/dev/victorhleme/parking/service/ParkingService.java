package dev.victorhleme.parking.service;

import dev.victorhleme.parking.dto.ParkingCreationDto;
import dev.victorhleme.parking.dto.ParkingDto;
import dev.victorhleme.parking.exception.DataNotFoundException;
import dev.victorhleme.parking.model.Parking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@Service
public class ParkingService {

    ModelMapper modelMapper = new ModelMapper();

    private static final List<Parking> parkingMap = new ArrayList<>();

    static {
        parkingMap.add(new Parking()
            .withId(randomUUID().toString())
            .withColor("white")
            .withLicense("ABC-1234")
            .withModel("Ford Ka")
            .withState("SP")
            .withEntryDate(LocalDateTime.now()));
        parkingMap.add(new Parking()
            .withId(randomUUID().toString())
            .withColor("red")
            .withLicense("ABC-1234")
            .withModel("Fiat Argo")
            .withState("SP")
            .withEntryDate(LocalDateTime.now()));
    }

    public List<ParkingDto> findAll() {
        return parkingMap.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ParkingDto findById(String id) {
        return parkingMap
            .stream()
            .filter(parking -> parking.getId().equals(id))
            .findFirst()
            .map(this::convertToDto)
            .orElseThrow(() -> new DataNotFoundException("Parking not found"));
    }

    public ParkingDto create(ParkingCreationDto parkingDto) {
        Parking parking = convertToEntity(parkingDto)
            .withId(randomUUID().toString())
            .withEntryDate(LocalDateTime.now());
        parkingMap.add(parking);
        return convertToDto(parking);
    }

    private ParkingDto convertToDto(Parking parking) {
        return modelMapper.map(parking, ParkingDto.class);
    }

    private Parking convertToEntity(ParkingCreationDto parkingDto) {
        return modelMapper.map(parkingDto, Parking.class);
    }
}
