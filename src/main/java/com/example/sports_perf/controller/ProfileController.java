package com.example.sports_perf.controller;

import com.example.sports_perf.service.UserService;
import com.example.sports_perf.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Get user profile based on userType (athlete or coach)
     */
    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getUserProfile(
            @PathVariable String username,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String userType) {

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        String tokenUsername = jwtUtil.getUsernameFromToken(token);
        if (!username.equals(tokenUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        Object userProfile;

        try {
            switch (userType.toLowerCase()) {
                case "athlete":
                    userProfile = userService.findAthleteByUsername(username);
                    break;
                case "coach":
                    userProfile = userService.findCoachByUsername(username);
                    break;
                default:
                    return ResponseEntity.badRequest().body("Invalid user type. Please specify 'athlete' or 'coach'.");
            }

            if (userProfile != null) {
                return ResponseEntity.ok(userProfile);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the profile.");
        }
    }

    // Update Profile Example
    @PutMapping("/profile/update/{username}")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable String username,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String userType,
            @RequestBody Map<String, String> profileData) {

        String token = authorizationHeader.substring(7);
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        String tokenUsername = jwtUtil.getUsernameFromToken(token);
        if (!username.equals(tokenUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        try {
            boolean isUpdated;

            switch (userType.toLowerCase()) {
                case "athlete":
                    isUpdated = userService.updateAthleteProfile(username, profileData);
                    break;
                case "coach":
                    isUpdated = userService.updateCoachProfile(username, profileData);
                    break;
                default:
                    return ResponseEntity.badRequest().body("Invalid user type. Please specify 'athlete' or 'coach'.");
            }

            if (isUpdated) {
                return ResponseEntity.ok("Profile updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the profile.");
        }
    }
}
