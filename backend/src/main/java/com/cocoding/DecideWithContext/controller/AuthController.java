package com.cocoding.DecideWithContext.controller;

import com.cocoding.DecideWithContext.model.AppUser;
import com.cocoding.DecideWithContext.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request, HttpSession session) {
        try {
            AppUser user = authService.register(request.email(), request.password());
            session.setAttribute(AuthService.USER_ID_SESSION_KEY, user.getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("id", user.getId(), "email", user.getEmail()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpSession session) {
        try {
            AppUser user = authService.login(request.email(), request.password());
            session.setAttribute(AuthService.USER_ID_SESSION_KEY, user.getId());
            return ResponseEntity.ok(Map.of("id", user.getId(), "email", user.getEmail()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }

    public record AuthRequest(String email, String password) {
    }
}
