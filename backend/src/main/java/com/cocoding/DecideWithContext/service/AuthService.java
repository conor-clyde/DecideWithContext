package com.cocoding.DecideWithContext.service;

import com.cocoding.DecideWithContext.model.AppUser;
import com.cocoding.DecideWithContext.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public static final String USER_ID_SESSION_KEY = "AUTH_USER_ID";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser register(String email, String rawPassword) {
        String normalizedEmail = normalizeEmail(email);
        if (normalizedEmail.isBlank() || rawPassword == null || rawPassword.length() < 6) {
            throw new IllegalArgumentException("Email and password are required (password min 6 chars).");
        }
        if (appUserRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new IllegalArgumentException("Email already registered.");
        }
        AppUser user = new AppUser(normalizedEmail, passwordEncoder.encode(rawPassword));
        return appUserRepository.save(user);
    }

    public AppUser login(String email, String rawPassword) {
        String normalizedEmail = normalizeEmail(email);
        AppUser user = appUserRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
        return user;
    }

    public AppUser requireUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Not authenticated.");
        }
        return appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    private String normalizeEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }
}
