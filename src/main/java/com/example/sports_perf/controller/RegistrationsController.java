package com.example.sports_perf.controller;

import com.example.sports_perf.model.Registration;
import com.example.sports_perf.service.RegistrationService;
import com.example.sports_perf.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RegistrationsController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private JwtUtil jwtUtil; // Inject JwtUtil to validate the token

    // Register an athlete for an event
    @PostMapping("/registrations")
    @PreAuthorize("hasRole('USER')") // Only authenticated users can register
    public ResponseEntity<String> registerAthlete(@RequestBody Registration registrationRequest) {
        // Ensure the user is authenticated via JWT
        if (!isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized"); // Unauthorized
        }

        Registration registration = registrationService.registerAthlete(
                registrationRequest.getEventId(),
                registrationRequest.getAthleteId()
        );
        return ResponseEntity.ok("Athlete registered successfully with ID: " + registration.getRegistrationId());
    }

    // Get registrations by athlete ID
    @GetMapping("/registrations/athlete/{athleteId}")
    public ResponseEntity<List<Registration>> getRegistrationsByAthleteId(@PathVariable Long athleteId) {
        // Ensure the user is authenticated via JWT
        if (!isAuthenticated()) {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }

        List<Registration> registrations = registrationService.findByAthleteId(athleteId);
        if (registrations.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if no registrations found
        }
        return ResponseEntity.ok(registrations);
    }

    // Get registrations by event ID
    @GetMapping("/registrations/event/{eventId}")
    public ResponseEntity<List<Registration>> getRegistrationsByEventId(@PathVariable Long eventId) {
        // Ensure the user is authenticated via JWT
        if (!isAuthenticated()) {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }

        List<Registration> registrations = registrationService.findByEventId(eventId);
        if (registrations.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if no registrations found
        }
        return ResponseEntity.ok(registrations);
    }

    // Update registration status (Only admins can update the status)
    @PatchMapping("/registrations/{registrationId}/status")
    @PreAuthorize("hasRole('ADMIN')") // Only admins can update the registration status
    public ResponseEntity<String> updateRegistrationStatus(
            @PathVariable Long registrationId,
            @RequestParam String status) {
        // Ensure the user is authenticated via JWT
        if (!isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized"); // Unauthorized
        }

        try {
            registrationService.updateRegistrationStatus(registrationId, status);
            return ResponseEntity.ok("Registration status updated successfully to: " + status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating registration status.");
        }
    }

    // Delete a registration (Only admins can delete)
    @DeleteMapping("/registrations/{registrationId}")
    @PreAuthorize("hasRole('ADMIN')") // Only admins can delete a registration
    public ResponseEntity<String> deleteRegistration(@PathVariable Long registrationId) {
        // Ensure the user is authenticated via JWT
        if (!isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized"); // Unauthorized
        }

        if (registrationService.deleteRegistration(registrationId)) {
            return ResponseEntity.ok("Registration deleted successfully.");
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    // Utility method to check if the request has a valid JWT token
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
