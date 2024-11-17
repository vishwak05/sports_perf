package com.example.sports_perf.service;

import com.example.sports_perf.dto.SignupRequest;
import com.example.sports_perf.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, String> userStore = new HashMap<>(); // In-memory user store (replace with DB)

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userStore.containsKey(username)) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(username, userStore.get(username), new ArrayList<>());
    }

    public void saveUser(SignupRequest signupRequest) {
        userStore.put(signupRequest.getUsername(), signupRequest.getPassword());
    }

    public String authenticateAndGenerateToken(SignupRequest authRequest) {
        UserDetails userDetails = loadUserByUsername(authRequest.getUsername());
        if (!userDetails.getPassword().equals(authRequest.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }
}
