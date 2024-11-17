package com.example.sports_perf.controller;

import com.example.sports_perf.dto.SignupRequest;
import com.example.sports_perf.model.Athlete;
import com.example.sports_perf.model.Coach;
import com.example.sports_perf.model.Admin;
import com.example.sports_perf.repository.AthleteRepository;
import com.example.sports_perf.repository.CoachRepository;
import com.example.sports_perf.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SignupController {

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        if (signupRequest.getUserType().equalsIgnoreCase("athlete")) {
            Athlete athlete = new Athlete();
            athlete.setFirstname(signupRequest.getFirstname());
            athlete.setLastname(signupRequest.getLastname());
            athlete.setMiddlename(signupRequest.getMiddlename());
            athlete.setUsername(signupRequest.getUsername());
            athlete.setPassword(signupRequest.getPassword());
            athlete.setEmail(signupRequest.getEmail());
            athleteRepository.save(athlete);

            return ResponseEntity.ok(Map.of("message", "Signup successful!").toString());

        } else if (signupRequest.getUserType().equalsIgnoreCase("coach")) {
            Coach coach = new Coach();
            coach.setFirstname(signupRequest.getFirstname());
            coach.setLastname(signupRequest.getLastname());
            coach.setMiddlename(signupRequest.getMiddlename());
            coach.setUsername(signupRequest.getUsername());
            coach.setPassword(signupRequest.getPassword());
            coach.setEmail(signupRequest.getEmail());
            coachRepository.save(coach);
            return ResponseEntity.ok(Map.of("message", "Signup successful!").toString());

        } else if (signupRequest.getUserType().equalsIgnoreCase("admin")) {
            Admin admin = new Admin();
            admin.setFirstname(signupRequest.getFirstname());
            admin.setLastname(signupRequest.getLastname());
            admin.setMiddlename(signupRequest.getMiddlename());
            admin.setUsername(signupRequest.getUsername());
            admin.setPassword(signupRequest.getPassword());
            admin.setEmail(signupRequest.getEmail());
            adminRepository.save(admin);
            return ResponseEntity.ok(Map.of("message", "Signup successful!").toString());

        } else {
            return ResponseEntity.badRequest().body("Invalid user type!");
        }
    }
}