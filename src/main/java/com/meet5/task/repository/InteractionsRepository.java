package com.meet5.task.repository;

import com.meet5.task.enums.InteractionType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

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

    public Integer userInteractionByUserIdAndTimeWindow (Integer userId, LocalDateTime windowStart) {
        String countQuery = """
                    select count(source_user_id) from user_interactions where source_user_id=? and created_at > ?
                    """;

        return jdbcTemplate.queryForObject(countQuery, Integer.class, userId, windowStart);
    }

}
