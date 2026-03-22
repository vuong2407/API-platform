package com.vuong.api_platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuong.api_platform.domain.Apis;
import com.vuong.api_platform.dto.ItemResult;
import com.vuong.api_platform.dto.response.BulkResponse;
import com.vuong.api_platform.enums.Status;
import com.vuong.api_platform.service.ApisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ApisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ApisService apisService;

    private Apis apisRequest;
    private Apis apisResponse;

    @BeforeEach
    void initData() {
        apisRequest = Apis.builder()
                .name("dat")
                .version("1")
                .status(Status.ACTIVE)
                .build();

        apisResponse = Apis.builder()
                .id("08e00e63-5553-416d-98f7-5941a5d7f25a")
                .name("dat")
                .version("1")
                .status(Status.ACTIVE)
                .build();
    }

    @Test
    void createApis_validTest() throws Exception {
        String content = objectMapper.writeValueAsString(List.of(apisRequest));

        // Mock response
        BulkResponse<Apis> response = new BulkResponse<>();
        response.setTotal(1);
        response.setSuccessCount(1);
        response.setFailCount(0);
        response.setResults(Collections.singletonList(ItemResult.success(apisResponse)));

        Mockito.when(apisService.saveAll(ArgumentMatchers.any()))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/apis")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.successCount").value(1))
                .andExpect(jsonPath("$.failCount").value(0))
                .andExpect(jsonPath("$.results[0].status").value("SUCCESS"))
                .andExpect(jsonPath("$.results[0].item.name").value("dat"));

        Mockito.when(apisService.saveAll(ArgumentMatchers.any()))
                .thenReturn(response);
    }

    @Test
    void createApis_emptyList() throws Exception {

        String content = objectMapper.writeValueAsString(List.of());

        BulkResponse<Apis> response = new BulkResponse<>();
        response.setTotal(0);

        response.setSuccessCount(0);
        response.setFailCount(0);
        response.setResults(List.of());

        Mockito.when(apisService.saveAll(ArgumentMatchers.any()))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/apis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(0))
                .andExpect(jsonPath("$.successCount").value(0))
                .andExpect(jsonPath("$.failCount").value(0))
                .andExpect(jsonPath("$.results").isEmpty());

        Mockito.verify(apisService).saveAll(ArgumentMatchers.any());
    }

    @Test
    void createApis_partialSuccess() throws Exception {

        String content = objectMapper.writeValueAsString(List.of(apisRequest, apisRequest));

        // Mock response
        BulkResponse<Apis> response = new BulkResponse<>();
        response.setTotal(2);
        response.setSuccessCount(1);
        response.setFailCount(1);
        response.setResults(List.of(
                ItemResult.success(apisResponse),
                ItemResult.fail(apisRequest, "Duplicate name-version")
        ));

        Mockito.when(apisService.saveAll(ArgumentMatchers.any()))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/apis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(2))
                .andExpect(jsonPath("$.successCount").value(1))
                .andExpect(jsonPath("$.failCount").value(1))
                .andExpect(jsonPath("$.results[0].status").value("SUCCESS"))
                .andExpect(jsonPath("$.results[1].status").value("FAILED"))
                .andExpect(jsonPath("$.results[1].error").value("Duplicate name-version"));

        Mockito.verify(apisService).saveAll(ArgumentMatchers.any());
    }

    @Test
    void getAllApis_success() throws Exception {

        List<Apis> apisList = List.of(apisResponse);

        Mockito.when(apisService.getAllApis()).thenReturn(apisList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/apis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(apisResponse.getId()))
                .andExpect(jsonPath("$[0].name").value("dat"))
                .andExpect(jsonPath("$[0].version").value("1"))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));

        Mockito.verify(apisService).getAllApis();
    }

    @Test
    void getApiById_success() throws Exception {

        Mockito.when(apisService.getApiById(apisResponse.getId()))
                .thenReturn(apisResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/apis/{id}", apisResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(apisResponse.getId()))
                .andExpect(jsonPath("$.name").value("dat"))
                .andExpect(jsonPath("$.version").value("1"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        Mockito.verify(apisService).getApiById(apisResponse.getId());
    }
}
