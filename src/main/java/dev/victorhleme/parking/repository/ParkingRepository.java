package dev.victorhleme.parking.repository;

import dev.victorhleme.parking.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {

}
