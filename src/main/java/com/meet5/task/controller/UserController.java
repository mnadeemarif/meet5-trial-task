package com.meet5.task.controller;

import com.meet5.task.dto.UserDto;
import com.meet5.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.meet5.task.validator.UserValidator.validateUserDto;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Add new user.
     * @param userDto user details.
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        validateUserDto(userDto);
        userService.saveUser(userDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Bulk add users.
     * @param userDtoList Users list.
     * @return ResponseEntity
     */
    @PostMapping("/bulk")
    public ResponseEntity<?> bulkAddUser(@RequestBody List<UserDto> userDtoList) {
        userService.bulkSaveUsers(userDtoList);
        return ResponseEntity.ok().build();
    }

    /**
     * Gets user by id.
     * @param userId id of the user.
     * @return ResponseEntity
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById (@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    /**
     * Records when user visits another user profile.
     * @param sourceUserId id of visiting user.
     * @param targetUserId id of visited user.
     * @return ResponseEntity
     */
    @PostMapping("/visit")
    public ResponseEntity<?> recordVisit (@RequestParam Integer sourceUserId, @RequestParam Integer targetUserId) {
        return ResponseEntity.ok(Map.of("success", userService.saveVisit(sourceUserId, targetUserId)));
    }

    /**
     * Records when user likes another user profile.
     * @param sourceUserId id of liker user.
     * @param targetUserId id of liked user.
     * @return ResponseEntity
     */
    @PostMapping("/like")
    public ResponseEntity<?> recordLike (@RequestParam Integer sourceUserId, @RequestParam Integer targetUserId) {
        return ResponseEntity.ok(Map.of("success", userService.saveLike(sourceUserId, targetUserId)));
    }

    /**
     * Gets user profile visitors.
     * @param userId id of user fetch visitors for.
     * @return ResponseEntity
     */
    @GetMapping("/{userId}/visitors")
    public ResponseEntity<?> getVisitors (@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getVisitors(userId));
    }

}
