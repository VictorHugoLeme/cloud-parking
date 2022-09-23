package dev.victorhleme.parking.dto;

import lombok.Data;

@Data
public class ParkingCreationDto {
    private String license;
    private String state;
    private String model;
    private String color;
}
