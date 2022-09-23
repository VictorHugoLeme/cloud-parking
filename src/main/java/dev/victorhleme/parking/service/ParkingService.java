package dev.victorhleme.parking.service;

import dev.victorhleme.parking.dto.ParkingDto;
import dev.victorhleme.parking.model.Parking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@Service
public class ParkingService {

    ModelMapper modelMapper = new ModelMapper();

    private static List<Parking> parkingMap = new ArrayList<>();

    static {
        String id = randomUUID().toString();
        parkingMap.add(new Parking()
                .withId(id)
            .withColor("red")
            .withLicense("ABC-1234")
            .withModel("Ford Ka")
            .withState("SP"));
    }

    public List<ParkingDto> findAll() {
        return parkingMap.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ParkingDto convertToDto(Parking parking) {
        return modelMapper.map(parking, ParkingDto.class);
    }

}
