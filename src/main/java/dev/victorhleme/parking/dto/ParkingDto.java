package dev.victorhleme.parking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingDto {
    private String id;
    private String license;
    private String state;
    private String model;
    private String color;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy / HH:mm")
    private LocalDateTime entryDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy / HH:mm")
    private LocalDateTime exitDate;
    private Double bill;
}
