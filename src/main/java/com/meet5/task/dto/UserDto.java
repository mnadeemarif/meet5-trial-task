package com.meet5.task.dto;

import lombok.Builder;

@Builder
public record UserDto(
        Integer userId,
        String firstName,
        String lastName,
        String username,
        String email,
        Integer age
        ) {
}
