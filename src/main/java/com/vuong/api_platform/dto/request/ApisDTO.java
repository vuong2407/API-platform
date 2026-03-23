package com.vuong.api_platform.dto.request;

import com.vuong.api_platform.domain.Apis;
import com.vuong.api_platform.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApisDTO {

    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @NotBlank(message = "Version cannot be null or empty")
    private String version;

    private Status status;

    public Apis toApis() {
        return Apis.builder()
                .name(name)
                .version(version)
                .status(status)
                .build();
    }
}
