package com.meet5.task.validator;

import com.meet5.task.domain.User;
import com.meet5.task.dto.UserDto;

import java.util.regex.Pattern;

public class UserValidator {

    public static void validateUserDto(UserDto userDto) {
        if (userDto.firstName() == null || userDto.firstName().isBlank()) {
            throw new IllegalArgumentException("First Name is required.");
        }

        if (userDto.lastName() == null || userDto.lastName().isBlank()) {
            throw new IllegalArgumentException("Last Name is required.");
        }

        if (userDto.username() == null || userDto.username().isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (!Pattern.matches("^[a-zA-Z0-9_.]{3,20}$", userDto.username())) {
            throw new IllegalArgumentException("Please enter a valid username.");
        }

        // Validate email format
        if (userDto.email() == null || userDto.email().isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (!userDto.email().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Please enter a valid email.");
        }

        // Validate age (min 13, max 120)
        if (userDto.age() == null) {
            throw new IllegalArgumentException("Age is required.");
        }
        if (userDto.age() < 13) {
            throw new IllegalArgumentException("Age must be at least 13.");
        }
        if (userDto.age() > 120) {
            throw new IllegalArgumentException("Age must not exceed 120.");
        }
    }

    public static void validateUserEntity(User user) {
        // Validate username
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (!Pattern.matches("^[a-zA-Z0-9_.]{3,20}$", user.getUsername())) {
            throw new IllegalArgumentException("Please enter a valid username.");
        }

        // Validate email
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Please enter a valid email.");
        }

        // Validate first name
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First Name is required.");
        }

        // Validate last name
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last Name is required.");
        }

        // Validate age
        if (user.getAge() == null) {
            throw new IllegalArgumentException("Age is required.");
        }
        if (user.getAge() < 13) {
            throw new IllegalArgumentException("Age must be at least 13.");
        }
        if (user.getAge() > 120) {
            throw new IllegalArgumentException("Age must not exceed 120.");
        }
    }
}
