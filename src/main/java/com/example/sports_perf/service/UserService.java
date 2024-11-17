package com.example.sports_perf.service;

import com.example.sports_perf.model.Athlete;
import com.example.sports_perf.model.Coach;

import java.util.Map;

public interface UserService {
    Athlete findAthleteByUsername(String username);
    Coach findCoachByUsername(String username);
    boolean updateAthleteProfile(String username, Map<String, String> profileData);
    boolean updateCoachProfile(String username, Map<String, String> profileData);
}
