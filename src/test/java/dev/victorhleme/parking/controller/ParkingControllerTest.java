package dev.victorhleme.parking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import testUtils.IntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Slf4j
class ParkingControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String baseUrl = "/api/v1/parking/";


//    @LocalServerPort
//    private int port;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void shouldSuccessWhenFindingAllParking() throws Exception {
        MvcResult result = mockMvc.perform(get(baseUrl)
                .contentType("application/json"))
            .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void findByIdTestSuccess() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/jobs/{id}", 1)
                    .contentType("application/json"))
            .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

//    @Test
//    void findByIdTestFail() throws Exception {
//        MvcResult result = mockMvc.perform(get("/jobs/{id}", 32)
//                .contentType("application/json"))
//            .andExpect(status().isNotFound()).andReturn();
//        int status = result.getResponse().getStatus();
//        Assertions.assertEquals(404, status);
//    }
//
//    @Test
//    void insertTestSuccess() throws Exception {
//        JobDTO insertedJob = new JobDTO(null, "Test insert title", "Test insert desc", 10.0, 20.0);
//        MvcResult result = mockMvc.perform(post("/jobs")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(insertedJob)))
//            .andExpect(status().isOk()).andReturn();
//        String content = result.getResponse().getContentAsString();
//        String expectedResponse = "{\"id\":31,\"title\":\"Test insert title\",\"description\":\"Test insert desc\",\"minSalary\":10.0,\"maxSalary\":20.0}";
//
//        Assertions.assertNotNull(content);
//        Assertions.assertEquals(expectedResponse, content);
//    }
//
//    @Test
//    void insertTestFail() throws Exception {
//
//        JobDTO invalidJobTitle = new JobDTO(null, "", "Test", 10.0, 20.0);
//        JobDTO invalidJobDescription = new JobDTO(null, "Test", "", 10.0, 20.0);
//        MvcResult result = mockMvc.perform(post("/jobs")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(invalidJobTitle)))
//            .andExpect(status().is4xxClientError()).andReturn();
//        int status = result.getResponse().getStatus();
//        Assertions.assertEquals(400, status);
//
//        result = mockMvc.perform(post("/jobs")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(invalidJobDescription)))
//            .andExpect(status().is4xxClientError()).andReturn();
//        status = result.getResponse().getStatus();
//        Assertions.assertEquals(400, status);
//    }



}