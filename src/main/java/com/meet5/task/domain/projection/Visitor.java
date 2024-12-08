package com.meet5.task.domain.projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meet5.task.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Visitor extends User {
    private Integer visitCount;
    private LocalDateTime lastVisitedAt;

    public static final RowMapper<Visitor> visitorRowMapper = (rs, rowNum) -> {
        Visitor visitor = new Visitor();
        visitor.setUserId(rs.getLong("user_id"));
        visitor.setUsername(rs.getString("username"));
        visitor.setFirstName(rs.getString("first_name"));
        visitor.setLastName(rs.getString("last_name"));
        visitor.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        visitor.setLastVisitedAt(rs.getTimestamp("last_visited_at").toLocalDateTime());
        visitor.setVisitCount(rs.getInt("visit_count"));
        return visitor;
    };
}