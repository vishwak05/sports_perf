package com.example.sports_perf.controller;

import com.example.sports_perf.dto.LoginRequest;
import com.example.sports_perf.model.Athlete;
import com.example.sports_perf.model.Coach;
import com.example.sports_perf.model.Admin;
import com.example.sports_perf.repository.AthleteRepository;
import com.example.sports_perf.repository.CoachRepository;
import com.example.sports_perf.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest loginRequest) {
        String userType = loginRequest.getUserType();
        String username = loginRequest.getUsername();
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Map<String, String> response = new HashMap<>();

        Object user = null;
        String redirectUrl = null;

        if ("athlete".equalsIgnoreCase(userType)) {
            user = athleteRepository.findByUsernameAndEmailAndPassword(username, email, password);
            redirectUrl = "/home";
        } else if ("coach".equalsIgnoreCase(userType)) {
            user = coachRepository.findByUsernameAndEmailAndPassword(username, email, password);
            redirectUrl = "/dashboard";
        } else if ("admin".equalsIgnoreCase(userType)) {
            user = adminRepository.findByUsernameAndEmailAndPassword(username, email, password);
            redirectUrl = "/admin";
        }

        if (user != null) {
            response.put("message", userType.substring(0, 1).toUpperCase() + userType.substring(1) + " logged in successfully");
            response.put("redirectUrl", redirectUrl);

            // Redirect to AuthController for JWT token generation
            // This will call the AuthController's login endpoint (if necessary)
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Invalid credentials or user type");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}