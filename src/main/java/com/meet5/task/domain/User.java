package com.meet5.task.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Pattern;

public class User implements Serializable {
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Integer age;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Date lastActiveAt;

    private static final int MIN_AGE = 13;
    private static final int MAX_AGE = 120;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );
    private static final Pattern USERNAME_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_.]{3,20}$"
    );

    public User() {
        this.createdAt = LocalDateTime.now();
    }

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

    public Long getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Integer getAge() {
        return this.age;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return this.modifiedAt;
    }

    public Date getLastActiveAt() {
        return this.lastActiveAt;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setLastActiveAt(Date lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }
}
