package dev.victorhleme.parking.service;

import dev.victorhleme.parking.dto.ParkingCreationDto;
import dev.victorhleme.parking.dto.ParkingDto;
import dev.victorhleme.parking.exception.DataNotFoundException;
import dev.victorhleme.parking.model.Parking;
import dev.victorhleme.parking.repository.ParkingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    ModelMapper modelMapper = new ModelMapper();

    public List<ParkingDto> findAll() {
        return parkingRepository.findAll().stream()
            .map(parking -> modelMapper.map(parking, ParkingDto.class))
            .collect(Collectors.toList());
    }

    public ParkingDto findById(String id) {
        Parking parking = parkingRepository.findById(id);
        if (parking == null) {
            throw new DataNotFoundException("Parking", id);
        }
        return modelMapper.map(parking, ParkingDto.class);
    }

    public ParkingDto create(ParkingCreationDto parkingDto) {
        Parking parking = convertToEntity(parkingDto)
            .withEntryDate(LocalDateTime.now());
        return convertToDto(parkingRepository.save(parking));
    }

    public ParkingDto update(String id, ParkingCreationDto parkingDto) {
        Parking parking = parkingRepository.findById(id);
        if (parking == null) {
            throw new DataNotFoundException("Parking", id);
        }
        parkingRepository.save(updateEntity(parking, parkingDto));
        return convertToDto(parkingRepository.findById(id));
    }

    public void delete(String id) {
        if (!parkingRepository.existsById(id)) {
            throw new DataNotFoundException("Parking", id);
        }
        parkingRepository.delete(id);
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
