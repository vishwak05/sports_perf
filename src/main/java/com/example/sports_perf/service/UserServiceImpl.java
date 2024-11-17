package com.example.sports_perf.service;

import com.example.sports_perf.model.Athlete;
import com.example.sports_perf.model.Coach;
import com.example.sports_perf.repository.AthleteRepository;
import com.example.sports_perf.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Override
    public Athlete findAthleteByUsername(String username) {
        return athleteRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Coach findCoachByUsername(String username) {
        return coachRepository.findByUsername(username);
    }

    @Override
    public boolean updateAthleteProfile(String username, Map<String, String> profileData) {
        return athleteRepository.findByUsername(username).map(athlete -> {
            if (profileData.containsKey("firstname")) athlete.setFirstname(profileData.get("firstname"));
            if (profileData.containsKey("lastname")) athlete.setLastname(profileData.get("lastname"));
            if (profileData.containsKey("email")) athlete.setEmail(profileData.get("email"));
            if (profileData.containsKey("password")) athlete.setPassword(profileData.get("password"));
            athleteRepository.save(athlete);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean updateCoachProfile(String username, Map<String, String> profileData) {
        Coach coach = coachRepository.findByUsername(username);
        if (coach != null) {
            if (profileData.containsKey("firstname")) coach.setFirstname(profileData.get("firstname"));
            if (profileData.containsKey("lastname")) coach.setLastname(profileData.get("lastname"));
            if (profileData.containsKey("email")) coach.setEmail(profileData.get("email"));
            if (profileData.containsKey("password")) coach.setPassword(profileData.get("password"));
            coachRepository.save(coach);
            return true;
        }
        return false;
    }
}
