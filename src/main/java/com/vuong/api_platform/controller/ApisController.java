package com.vuong.api_platform.controller;

import com.vuong.api_platform.domain.Apis;
import com.vuong.api_platform.dto.request.ApisDTO;
import com.vuong.api_platform.dto.response.BulkResponse;
import com.vuong.api_platform.service.ApisService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/apis")
@Validated
public class ApisController {

    private final ApisService apisService;


    public ApisController(ApisService apisService) {
        this.apisService = apisService;
    }

    @GetMapping
    public List<Apis> getAllApis() {
        return apisService.getAllApis();
    }

    @GetMapping("/{id}")
    public Apis getApiById(@PathVariable String id) {
        return apisService.getApiById(id);
    }

    @PostMapping
    public BulkResponse<Apis> createApis(@RequestBody List<@Valid ApisDTO> apis) {
        return apisService.saveAll(apis);
    }
}
