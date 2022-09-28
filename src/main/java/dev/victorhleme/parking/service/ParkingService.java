package dev.victorhleme.parking.service;

import dev.victorhleme.parking.dto.ParkingCreationDto;
import dev.victorhleme.parking.dto.ParkingDto;
import dev.victorhleme.parking.exception.DataNotFoundException;
import dev.victorhleme.parking.exception.VehicleAlreadyLeftException;
import dev.victorhleme.parking.model.Parking;
import dev.victorhleme.parking.repository.ParkingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static dev.victorhleme.parking.utils.ParkingCheckout.calculatePrice;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    ModelMapper modelMapper = new ModelMapper();

    public List<ParkingDto> findAll() {
        return parkingRepository.findAll().stream()
            .map(parking -> modelMapper.map(parking, ParkingDto.class))
            .collect(Collectors.toList());
    }

    public ParkingDto findById(UUID id) {
        Parking parking = parkingRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Parking", id));
        return modelMapper.map(parking, ParkingDto.class);
    }

    @Transactional
    public ParkingDto create(ParkingCreationDto parkingDto) {
        Parking parking = convertToEntity(parkingDto)
            .withEntryDate(LocalDateTime.now());
        return convertToDto(parkingRepository.save(parking));
    }

    public ParkingDto update(UUID id, ParkingCreationDto parkingDto) {
        Parking parking = parkingRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Parking", id));
        parkingRepository.save(updateEntity(parking, parkingDto));
        return convertToDto(parkingRepository.findById(id).get());
    }

    @Transactional
    public ParkingDto exit(UUID id) {
        Parking parking = parkingRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Parking", id));
        if (parking.getExitDate() != null) {
            throw new VehicleAlreadyLeftException(id);
        }
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(calculatePrice(parking.getEntryDate(), parking.getExitDate()));

        return convertToDto(parkingRepository.findById(id).get());
    }

    public void delete(UUID id) {
        if (!parkingRepository.existsById(id)) {
            throw new DataNotFoundException("Parking", id);
        }
        parkingRepository.deleteById(id);
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
