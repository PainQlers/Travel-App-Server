package com.techup.travel_app.controller;

import com.techup.travel_app.security.JwtService;
import com.techup.travel_app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> req) {
        return authService.register(req.get("email"), req.get("password"));
    }

    @PostMapping("/login")
    public Map <String, String> login(@RequestBody Map<String, String> req) {
        String token = authService.login(req.get("email"), req.get("password"));
        return Map.of("token", token);
    }

    @GetMapping("/me")
    public Map<String, String> me(@RequestHeader("Authorization") String header) {
        String token = header.replace("Bearer ", "");
        String email = jwtService.extractEmail(token);
        return Map.of("email", email);
    }
    
}
