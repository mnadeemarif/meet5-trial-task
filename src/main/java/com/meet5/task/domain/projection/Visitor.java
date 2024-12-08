package com.meet5.task.domain.projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record Visitor (
        Integer userId,
        String username,
        String firstName,
        String lastName,
        Integer visitCount,
        LocalDateTime lastVisitedAt
) {
    public static final RowMapper<Visitor> visitorRowMapper = (rs, rowNum) -> Visitor.builder()
            .userId(rs.getInt("user_id"))
            .username(rs.getString("username"))
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .visitCount(rs.getInt("visit_count"))
            .lastVisitedAt(rs.getTimestamp("last_visited_at").toLocalDateTime())
            .build();
}