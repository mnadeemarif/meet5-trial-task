package com.meet5.task.repository;

import com.meet5.task.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findUserById (Integer userId) {
        return jdbcTemplate.queryForObject("select * from users where user_id = ?", userRowMapper, userId);
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setAge(rs.getInt("age"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setLastActiveAt(rs.getDate("last_active_at"));
        return user;
    };

}
