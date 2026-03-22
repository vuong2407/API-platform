package com.vuong.api_platform.domain;

import com.vuong.api_platform.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uc_name_version",
                        columnNames = {"name", "version"}
                )
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Apis {

    @Id
    private String id;

    private String name;

    private String version;

    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
