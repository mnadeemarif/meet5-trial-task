package com.meet5.task.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Pattern;

@Getter
public class User implements Serializable {
    @Setter
    protected Long userId;
    protected String username;
    private String email;
    @Setter
    protected String firstName;
    @Setter
    protected String lastName;
    private Integer age;
    @Setter
    protected LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @Setter
    private Date lastActiveAt;
    private static final int MIN_AGE = 13;

    private static final int MAX_AGE = 120;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );
    private static final Pattern USERNAME_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_.]{3,20}$"
    );
    private void validateUsername(String username) {
        if (username == null || !USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("Invalid username");
        }
    }

    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void validateAge(Integer age) {
        if (age == null || age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException("Invalid age");
        }
    }

    public void setUsername(String username) {
        validateUsername(username);
        this.username = username;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public void setAge(Integer age) {
        validateAge(age);
        this.age = age;
    }

    public boolean hasValidData() {
        return username != null && email != null &&
                firstName != null && lastName != null &&
                age != null;
    }

    public boolean isRecentProfile() {
        return createdAt.isAfter(LocalDateTime.now().minusDays(7));
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public static final RowMapper<User> userRowMapper = (rs, rowNum) -> {
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
