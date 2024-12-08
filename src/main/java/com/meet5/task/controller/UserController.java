package com.meet5.task.controller;

import com.meet5.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById (@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping("/visit")
    public ResponseEntity<?> recordVisit (@RequestParam Integer sourceUserId, @RequestParam Integer targetUserId) {
        return ResponseEntity.ok(Map.of("success", userService.saveVisit(sourceUserId, targetUserId)));
    }

    @PostMapping("/like")
    public ResponseEntity<?> recordLike (@RequestParam Integer sourceUserId, @RequestParam Integer targetUserId) {
        return ResponseEntity.ok(Map.of("success", userService.saveLike(sourceUserId, targetUserId)));
    }

    @GetMapping("/{userId}/visitors")
    public ResponseEntity<?> getVisitors (@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getVisitors(userId));
    }

}
