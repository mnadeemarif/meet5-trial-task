package com.meet5.task.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
//@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String ddl = new String(Files.readAllBytes(Paths.get("src/main/resources/schema/schema.sql")));
        jdbcTemplate.execute(ddl);


        String createNadeemUser = "INSERT INTO USERS (USERNAME, EMAIL, FIRST_NAME, LAST_NAME, AGE, LAST_ACTIVE_AT) VALUES ('nadeem.arif', 'nadeem.arif@yopmail.com', 'nadeem', 'arif', 25, CURRENT_TIMESTAMP())";
        jdbcTemplate.execute(createNadeemUser);
    }

}
