package com.example.sports_perf.service;

import com.example.sports_perf.model.EventResult;
import com.example.sports_perf.repository.EventResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventResultService {

    @Autowired
    private EventResultRepository eventResultRepository;

    // Fetch published results for an event
    public List<EventResult> getPublishedResultsByEventId(Long eventId) {
        return eventResultRepository.findByEventIdAndPublished(eventId, true);
    }

    // Fetch all results for an event (both published and unpublished)
    public List<EventResult> getAllResultsByEventId(Long eventId) {
        return eventResultRepository.findByEventId(eventId);
    }

    // Publish a new event result
    public EventResult publishEventResult(EventResult eventResult) {
        eventResult.setPublished(true); // Mark as published
        return eventResultRepository.save(eventResult);
    }

    // Update an existing event result
    public EventResult updateEventResult(Long resultId, EventResult updatedResult) {
        Optional<EventResult> optionalResult = eventResultRepository.findById(resultId);
        if (optionalResult.isPresent()) {
            EventResult existingResult = optionalResult.get();
            existingResult.setEventId(updatedResult.getEventId());
            existingResult.setAthleteId(updatedResult.getAthleteId());
            existingResult.setResult(updatedResult.getResult());
            existingResult.setPublished(updatedResult.isPublished());
            return eventResultRepository.save(existingResult);
        }
        return null; // Result not found
    }

    // Delete an event result
    public boolean deleteEventResult(Long resultId) {
        if (eventResultRepository.existsById(resultId)) {
            eventResultRepository.deleteById(resultId);
            return true;
        }
        return false;
    }
}
