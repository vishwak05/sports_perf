package com.example.sports_perf.service;

import com.example.sports_perf.model.Athlete;
import com.example.sports_perf.repository.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AthleteService {

    @Autowired
    private AthleteRepository athleteRepository;

    // Find an athlete by username
    public Athlete findByUsername(String username) {
        Optional<Athlete> athlete = athleteRepository.findByUsername(username);
        return athlete.orElse(null);
    }

    // Create or save an athlete
    public Athlete saveAthlete(Athlete athlete) {
        return athleteRepository.save(athlete);
    }

    // Find athlete by ID
    public Optional<Athlete> findById(Long id) {
        return athleteRepository.findById(id);
    }

    // Delete an athlete by ID
    public void deleteById(Long id) {
        athleteRepository.deleteById(id);
    }
}
