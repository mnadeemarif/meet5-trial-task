package com.meet5.task.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FraudLogRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveFraudLog (Integer userId) {
        String fraudLogQuery = """
                insert into fraud_detection_log (user_id, is_flagged) values (?, true)
                """;

        jdbcTemplate.update(fraudLogQuery, userId);
    }
}
