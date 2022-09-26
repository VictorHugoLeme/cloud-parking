package dev.victorhleme.parking.repository;

import dev.victorhleme.parking.model.Parking;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.UUID.randomUUID;

@Repository
public class ParkingRepository {
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

    public List<Parking> findAll() {
        return new ArrayList<>(parkingMap.values());
    }

    public Parking findById(String id) {
        return parkingMap.get(id);
    }

    public Parking save(Parking parking) {
        String id = parking.getId() == null ? randomUUID().toString() : parking.getId();
        parking.setId(id);
        parkingMap.put(id, parking.withId(id));
        return parkingMap.get(id);
    }

    public void delete(String id) {
        parkingMap.remove(id);
    }

    public boolean existsById(String id) {
        return parkingMap.containsKey(id);
    }
}
