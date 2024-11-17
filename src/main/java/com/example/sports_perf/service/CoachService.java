package com.example.sports_perf.service;

import com.example.sports_perf.model.Coach;
import com.example.sports_perf.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;

    // Find a coach by username
    public Coach findByUsername(String username) {
        return coachRepository.findByUsername(username);
    }

    // Create or save a coach
    public Coach saveCoach(Coach coach) {
        return coachRepository.save(coach);
    }

    // Find coach by ID
    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
    }

    // Delete a coach by ID
    public void deleteById(Long id) {
        coachRepository.deleteById(id);
    }
}
