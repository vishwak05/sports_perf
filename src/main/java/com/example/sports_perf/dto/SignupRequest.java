package com.example.sports_perf.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {
    // Getters and Setters
    private String firstname;
    private String lastname;
    private String middlename; // optional
    private String username;
    private String password;
    private String email;
    private String userType;

}
