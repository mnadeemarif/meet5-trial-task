package com.meet5.task.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class User implements Serializable {
    protected Long userId;
    protected String username;
    private String email;
    protected String firstName;
    protected String lastName;
    private Integer age;
    protected LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static final RowMapper<User> userRowMapper = (rs, rowNum) -> User.builder()
            .userId(rs.getLong("user_id"))
            .age(rs.getInt("age"))
            .email(rs.getString("email"))
            .username(rs.getString("username"))
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .build();
}
