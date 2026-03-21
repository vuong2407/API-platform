package com.vuong.api_platform.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseHealthIndicator implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Health health() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            log.debug("Database health check passed");
            return Health.up()
                    .withDetail("database", "MySQL")
                    .withDetail("status", "Reachable")
                    .build();
        } catch (Exception ex) {
            log.error("Database health check failed: {}", ex.getMessage());
            return Health.down()
                    .withDetail("database", "MySQL")
                    .withDetail("error", ex.getMessage())
                    .build();
        }
    }

}
