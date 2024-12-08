package com.meet5.task.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        String ddl = new String(Files.readAllBytes(Paths.get("src/main/resources/schema/schema.sql")));
        jdbcTemplate.execute(ddl);

        String dml = new String(Files.readAllBytes(Paths.get("src/main/resources/schema/data.sql")));
        jdbcTemplate.execute(dml);
    }

}
