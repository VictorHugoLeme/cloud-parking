package dev.victorhleme.parking.service;

import dev.victorhleme.parking.dto.ParkingCreationDto;
import dev.victorhleme.parking.dto.ParkingDto;
import dev.victorhleme.parking.exception.DataNotFoundException;
import dev.victorhleme.parking.model.Parking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@Service
public class ParkingService {

    ModelMapper modelMapper = new ModelMapper();
    private static final HashMap<String, Parking> parkingMap = new HashMap<>();

    static {
        String id = randomUUID().toString();
        parkingMap.put(id, new Parking()
            .withId(id)
            .withColor("white")
            .withLicense("ABC-1234")
            .withModel("Ford Ka")
            .withState("SP")
            .withEntryDate(LocalDateTime.now()));

        id = randomUUID().toString();
        parkingMap.put(id, new Parking()
            .withId(id)
            .withColor("red")
            .withLicense("ABC-1234")
            .withModel("Fiat Argo")
            .withState("SP")
            .withEntryDate(LocalDateTime.now()));
    }

    public List<ParkingDto> findAll() {

        return Arrays.asList(modelMapper.map(parkingMap.values().toArray(), ParkingDto[].class));
    }

    public ParkingDto findById(String id) {
        Parking parking = parkingMap.get(id);
        if (parking == null) {
            throw new DataNotFoundException("Parking", id);
        }
        return modelMapper.map(parking, ParkingDto.class);
    }

    public ParkingDto create(ParkingCreationDto parkingDto) {
        String id = randomUUID().toString();
        Parking parking = convertToEntity(parkingDto)
            .withId(id)
            .withEntryDate(LocalDateTime.now());
        parkingMap.put(id, parking);
        return convertToDto(parking);
    }

    public ParkingDto update(String id, ParkingCreationDto parkingDto) {
        Parking parking = parkingMap.get(id);
        if (parking == null) {
            throw new DataNotFoundException("Parking", id);
        }
        parkingMap.put(id, updateEntity(parking, parkingDto));
        return convertToDto(parkingMap.get(id));
    }

    public void delete(String id) {
        if (parkingMap.get(id) == null) {
            throw new DataNotFoundException("Parking", id);
        }
        parkingMap.remove(id);
    }

    private ParkingDto convertToDto(Parking parking) {
        return modelMapper.map(parking, ParkingDto.class);
    }

    private Parking convertToEntity(ParkingCreationDto parkingDto) {
        return modelMapper.map(parkingDto, Parking.class);
    }

    private Parking updateEntity(Parking parking, ParkingCreationDto parkingDto) {
        return parking
            .withColor(parkingDto.getColor())
            .withLicense(parkingDto.getLicense())
            .withModel(parkingDto.getModel())
            .withState(parkingDto.getState());
    }
}
