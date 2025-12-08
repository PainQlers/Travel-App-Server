package com.techup.travel_app.service;

import com.techup.travel_app.entity.User;
import com.techup.travel_app.repository.UserRepository;
import com.techup.travel_app.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public String register(String email, String password, String displayName) {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
        .email(email)
        .passwordHash(passwordEncoder.encode(password))
        .displayName(displayName)
        .build();

        userRepository.save(user);
        return "Registered successfully";
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentails");
        }

        return jwtService.generateToken(user.getEmail());
    }

    public Map<String, Object> getCurrentUserInfo(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return Map.of(
            "id", user.getId(),
            "email", user.getEmail(),
            "displayName", user.getDisplayName() == null ? "" : user.getDisplayName()
        );
    }
}
