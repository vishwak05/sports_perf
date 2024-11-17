package com.example.sports_perf.controller;

import com.example.sports_perf.dto.LoginRequest;
import com.example.sports_perf.dto.SignupRequest;
import com.example.sports_perf.service.CustomUserDetailsService;
import com.example.sports_perf.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String usertype = loginRequest.getUserType();

        Map<String, String> response = new HashMap<>();

        // Generate JWT token if login credentials are correct
        String jwtToken = jwtUtil.generateToken(username, usertype);

        response.put("message", "Login successful");
        response.put("token", jwtToken);
        response.put("username", username); // Optionally include user details like username

        return ResponseEntity.ok(response);
    }
}
