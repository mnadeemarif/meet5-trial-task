package com.meet5.task.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InteractionsRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveInteraction (Integer sourceUserId, Integer targetUserId, String interactionType) {
        String insertInteractionQuery = """
                INSERT INTO USER_INTERACTIONS (SOURCE_USER_ID, TARGET_USER_ID, INTERACTION_TYPE) VALUES (?, ?, ?) on conflict do nothing
                """;
        jdbcTemplate.update(insertInteractionQuery, sourceUserId, targetUserId, interactionType);
    }
}
