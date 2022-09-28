package dev.victorhleme.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class ParkingCreationDto {
    private String license;
    private String state;
    private String model;
    private String color;
}
