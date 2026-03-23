package com.vuong.api_platform.repository;

import com.vuong.api_platform.domain.Apis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApisRepository extends JpaRepository<Apis, String> {

    boolean existsByNameAndVersion(String name, String version);
}
