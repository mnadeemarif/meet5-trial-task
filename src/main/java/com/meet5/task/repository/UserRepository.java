package com.meet5.task.repository;

import com.meet5.task.domain.User;
import com.meet5.task.domain.projection.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meet5.task.domain.User.userRowMapper;
import static com.meet5.task.domain.projection.Visitor.visitorRowMapper;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public User findUserById(Integer userId) {
        return jdbcTemplate.queryForObject("select * from users where user_id = ?", userRowMapper, userId);
    }

    public List<Visitor> findVisitorsByUserId (Integer userId) {
        String query = """
                SELECT up.user_id,up.username,up.first_name,up.last_name,up.created_at,
                       COUNT(ui.interaction_id) AS visit_count,
                       MAX(ui.created_at) AS last_visited_at
                FROM
                user_interactions ui
                    JOIN
                    users up ON ui.source_user_id = up.user_id
                WHERE
                    ui.target_user_id = ? AND ui.interaction_type = 'VISIT'
                GROUP BY
                    up.user_id,
                    up.username,
                    up.first_name,
                    up.last_name
                ORDER BY
                    last_visited_at DESC,
                    visit_count DESC
                """;

        return jdbcTemplate.query(query, visitorRowMapper,userId);
    }
}
