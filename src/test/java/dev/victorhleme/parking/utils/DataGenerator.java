package dev.victorhleme.parking.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.victorhleme.parking.dto.ParkingCreationDto;
import dev.victorhleme.parking.model.Parking;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

public class DataGenerator {

    public static Parking buildParking(UUID id) {
        return new Parking()
            .withId(id)
            .withLicense("ABC-1234")
            .withModel("Gol")
            .withColor("Black")
            .withState("SP")
            .withEntryDate(LocalDateTime.now());


    }

    public static ParkingCreationDto buildParkingCreationDto() {
        return new ParkingCreationDto()
            .withLicense("ABC-1234")
            .withModel("Gol")
            .withColor("Black")
            .withState("SP");
    }

    public static RequestBuilder buildGetRequest(String url) {
        return MockMvcRequestBuilders
            .get(url);
    }

    public static RequestBuilder buildPostRequest(String url) {
        return MockMvcRequestBuilders
            .post(url);
    }

    public static RequestBuilder buildPutRequest(String url) {
        return MockMvcRequestBuilders
            .put(url);
    }

    public static RequestBuilder buildDeleteRequest(String url) {
        return MockMvcRequestBuilders
            .delete(url);
    }

    public static RequestBuilder buildPostRequestWithObject(String url, Object object) {
        return MockMvcRequestBuilders
            .post(url)
            .accept("application/json")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .characterEncoding("UTF-8")
            .content(asJsonString(object));
    }

    public static RequestBuilder buildPutRequestWithObject(String url, Object object) {
        return MockMvcRequestBuilders
            .put(url)
            .accept("application/json")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .characterEncoding("UTF-8")
            .content(asJsonString(object));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
