package com.example.sports_perf.controller;

import com.example.sports_perf.model.EventResult;
import com.example.sports_perf.service.EventResultService;
import com.example.sports_perf.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventResultController {

    @Autowired
    private EventResultService eventResultService;

    @Autowired
    private JwtUtil jwtUtil; // Inject JwtUtil to validate the token

    // Get published results by event ID
    @GetMapping("/event-results/{eventId}/published")
    public ResponseEntity<List<EventResult>> getPublishedResults(@PathVariable Long eventId) {

        List<EventResult> results = eventResultService.getPublishedResultsByEventId(eventId);
        if (!results.isEmpty()) {
            return ResponseEntity.ok(results);
        }
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Publish a new event result
    @PostMapping("/event-results/publish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResult> publishEventResult(@RequestBody EventResult eventResult) {
        // Ensure the user is authenticated via JWT
        if (!isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        EventResult savedResult = eventResultService.publishEventResult(eventResult);
        return ResponseEntity.ok(savedResult);
    }

    // Get all results for an event (published and unpublished)
    @GetMapping("/event-results/{eventId}")
    public ResponseEntity<List<EventResult>> getAllResultsByEventId(@PathVariable Long eventId) {
        List<EventResult> results = eventResultService.getAllResultsByEventId(eventId);
        if (!results.isEmpty()) {
            return ResponseEntity.ok(results);
        }
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Update the result (e.g., to publish or modify it)
    @PutMapping("/event-results/{resultId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResult> updateEventResult(@PathVariable Long resultId, @RequestBody EventResult updatedResult) {
        // Ensure the user is authenticated via JWT
        if (!isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        EventResult result = eventResultService.updateEventResult(resultId, updatedResult);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    // Delete an event result
    @DeleteMapping("/event-results/{resultId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEventResult(@PathVariable Long resultId) {
        // Ensure the user is authenticated via JWT
        if (!isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        if (eventResultService.deleteEventResult(resultId)) {
            return ResponseEntity.ok().build(); // 200 OK
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    // Utility method to check if the request has a valid JWT token
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
