package com.vuong.api_platform.service;

import com.vuong.api_platform.domain.Apis;
import com.vuong.api_platform.dto.ItemResult;
import com.vuong.api_platform.dto.request.ApisDTO;
import com.vuong.api_platform.dto.response.BulkResponse;
import com.vuong.api_platform.enums.Status;
import com.vuong.api_platform.exception.ResourceNotFoundException;
import com.vuong.api_platform.repository.ApisRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ApisServiceTest {

    @Mock
    private ApisRepository apisRepository;

    @InjectMocks
    private ApisService apisService;

    private Apis api;
    private ApisDTO apisDTO;

    @BeforeEach
    void initData() {
        api = Apis.builder()
                .name("dat")
                .version("1")
                .build();

        apisDTO = new ApisDTO("dat","1", Status.ACTIVE);
    }

    @Test
    void getAllApis_success() {
        when(apisRepository.findAll()).thenReturn(List.of(api));

        List<Apis> result = apisService.getAllApis();

        assertEquals(1, result.size());
        verify(apisRepository).findAll();
    }

    @Test
    void getApiById_found() {
        when(apisRepository.findById("1")).thenReturn(Optional.of(api));

        Apis result = apisService.getApiById("1");

        assertNotNull(result);
        assertEquals("dat", result.getName());
    }

    @Test
    void getApiById_notFound() {
        when(apisRepository.findById("1")).thenReturn(Optional.empty());

        // Vì service ném exception nên dùng assertThrows
        assertThrows(ResourceNotFoundException.class, () -> apisService.getApiById("1"));
    }

    @Test
    void saveOne_success() {
        // Không cần mock existsByNameAndVersion nữa
        when(apisRepository.save(any(Apis.class))).thenReturn(api);

        ItemResult<Apis> result = apisService.saveOne(apisDTO);

        assertEquals("SUCCESS", result.getStatus());
        assertNotNull(result.getItem());
        verify(apisRepository).save(any(Apis.class));
    }

    @Test
    void saveOne_duplicate_violation() {
        // Giả lập việc DB báo lỗi trùng lặp khi save
        when(apisRepository.save(any(Apis.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate"));

        ItemResult<Apis> result = apisService.saveOne(apisDTO);

        assertEquals("FAILED", result.getStatus());
        assertEquals("Duplicate name-version", result.getError());
    }

    @Test
    void saveOne_exception() {
        when(apisRepository.save(any(Apis.class)))
                .thenThrow(new RuntimeException("System error"));

        ItemResult<Apis> result = apisService.saveOne(apisDTO);

        assertEquals("FAILED", result.getStatus());
        assertEquals("System error", result.getError());
    }

    @Test
    void saveAll_allSuccess() {
        when(apisRepository.save(any(Apis.class))).thenReturn(api);

        BulkResponse<Apis> result = apisService.saveAll(List.of(apisDTO, apisDTO));

        assertEquals(2, result.getTotal());
        assertEquals(2, result.getSuccessCount());
        assertEquals(0, result.getFailCount());
    }

    @Test
    void saveAll_partialSuccess() {
        // Mock: Lần đầu thành công, lần hai ném lỗi trùng lặp
        when(apisRepository.save(any(Apis.class)))
                .thenReturn(api)
                .thenThrow(new DataIntegrityViolationException("Duplicate"));

        BulkResponse<Apis> result = apisService.saveAll(List.of(apisDTO, apisDTO));

        assertEquals(2, result.getTotal());
        assertEquals(1, result.getSuccessCount());
        assertEquals(1, result.getFailCount());
    }

    @Test
    void saveAll_emptyList() {
        BulkResponse<Apis> result = apisService.saveAll(List.of());

        assertEquals(0, result.getTotal());
        assertEquals(0, result.getSuccessCount());
    }
}
