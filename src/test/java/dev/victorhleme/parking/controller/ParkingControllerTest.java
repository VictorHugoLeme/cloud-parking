package dev.victorhleme.parking.controller;

import dev.victorhleme.parking.repository.ParkingRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import testUtils.IntegrationTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static dev.victorhleme.parking.utils.DataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Slf4j
class ParkingControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ParkingRepository parkingRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        parkingRepository.deleteAll();
    }

    @Test
    void shouldSuccessWhenFindingAllParking() throws Exception {
        RequestBuilder request = buildGetRequest("/api/v1/parking/");
        MvcResult result = this.mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        assertThat(result).isNotNull();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("[]");
    }

    @Test
    void shouldSuccessWhenFindingByValidId() throws Exception {
        UUID id = UUID.randomUUID();
        parkingRepository.save(buildParking(id));

        RequestBuilder request = buildGetRequest("/api/v1/parking/" + id);
        MvcResult result = mockMvc.perform(request)
            .andExpect(
                status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();

        assertNotNull(content);
        assertTrue(content.contains(id.toString()));
    }

    @Test
    void shouldFailWhenFindingByInvalidId() throws Exception {
        UUID id = UUID.randomUUID();
        parkingRepository.save(buildParking(UUID.randomUUID()));

        RequestBuilder request = buildGetRequest("/api/v1/parking/" + id);
        MvcResult result = mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void shouldSuccessWhenDeletingByValidId() throws Exception {
        UUID id = UUID.randomUUID();
        parkingRepository.save(buildParking(id));

        RequestBuilder request = buildDeleteRequest("/api/v1/parking/" + id);
        mockMvc.perform(request)
            .andExpect(
                status().isNoContent());
        assertThat(parkingRepository.findById(id)).isEmpty();
    }

    @Test
    void shouldFailWhenDeletingByInvalidId() throws Exception {
        UUID id = UUID.randomUUID();
        parkingRepository.save(buildParking(id));

        RequestBuilder request = buildDeleteRequest("/api/v1/parking/" + UUID.randomUUID());
        mockMvc.perform(request)
            .andExpect(
                status().is4xxClientError());
        assertThat(parkingRepository.findById(id)).isPresent();
    }

    @Test
    void shouldSuccessWhenCreatingNewParking() throws Exception {
        RequestBuilder request = buildPostRequestWithObject("/api/v1/parking/", buildParkingCreationDto());

        MvcResult result = mockMvc.perform(request)
            .andExpect(
                status().isCreated()).andReturn();
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertThat(parkingRepository.findAll()).hasSize(1);
        assertTrue(content.contains("entryDate"));
    }

    @Test
    void shouldSuccessWhenUpdatingParking() throws Exception {
        UUID id = parkingRepository.save(buildParking(UUID.randomUUID())).getId();
        RequestBuilder request = buildPutRequestWithObject("/api/v1/parking/" + id, buildParkingCreationDto().withColor("Red"));

        MvcResult result = mockMvc.perform(request)
            .andExpect(
                status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertThat(parkingRepository.findAll()).hasSize(1);
        assertTrue(content.contains("Red"));
    }

    @Test
    void shouldFailWhenUpdatingParkingWithInvalidId() throws Exception {
        RequestBuilder request = buildPutRequestWithObject("/api/v1/parking/" + UUID.randomUUID(), buildParkingCreationDto().withColor("Red"));

        MvcResult result = mockMvc.perform(request)
            .andExpect(
                status().is4xxClientError()).andReturn();
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void shouldSuccessWhenExitingAParkingWithValidIdAndNotAlreadyExited() throws Exception {
        UUID id = parkingRepository.save(buildParking(UUID.randomUUID())).getId();
        RequestBuilder request = buildPostRequest("/api/v1/parking/" + id + "/exit");
        MvcResult result = mockMvc.perform(request)
            .andExpect(
                status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("exitDate"));
        assertTrue(content.contains("bill"));
    }

    @Test
    void shouldFailWhenExitingAParkingWithInvalidId() throws Exception {
        RequestBuilder request = buildPostRequest("/api/v1/parking/" + UUID.randomUUID() + "/exit");
        MvcResult result = mockMvc.perform(request)
            .andExpect(
                status().is4xxClientError()).andReturn();
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void shouldFailWhenExitingAParkingWithValidIdButAlreadyExited() throws Exception {
        UUID id = parkingRepository.save(buildParking(UUID.randomUUID()).withExitDate(LocalDateTime.now())).getId();
        RequestBuilder request = buildPostRequest("/api/v1/parking/" + id + "/exit");
        MvcResult result = mockMvc.perform(request)
            .andExpect(
                status().is4xxClientError()).andReturn();
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
    }

}