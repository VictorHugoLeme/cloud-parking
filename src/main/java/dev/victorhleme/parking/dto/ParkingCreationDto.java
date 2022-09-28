package dev.victorhleme.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.validation.constraints.NotBlank;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class ParkingCreationDto {

    @NotBlank(message = "License plate is required")
    private String license;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Color is required")
    private String color;
}
