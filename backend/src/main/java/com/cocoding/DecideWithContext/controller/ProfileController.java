package com.cocoding.DecideWithContext.controller;

import com.cocoding.DecideWithContext.model.AppUser;
import com.cocoding.DecideWithContext.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final AuthService authService;

    public ProfileController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<?> currentProfile(HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute(AuthService.USER_ID_SESSION_KEY);
            AppUser user = authService.requireUser(userId);
            List<String> interests = user.getInterests().stream()
                    .map(interest -> interest.getName())
                    .sorted()
                    .collect(Collectors.toList());
            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "email", user.getEmail(),
                    "interests", interests
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }
}
