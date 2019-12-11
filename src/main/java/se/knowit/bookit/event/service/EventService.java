package se.knowit.bookit.event.service;

import se.knowit.bookit.event.model.Event;

import java.util.Optional;
import java.util.UUID;

public interface EventService {
    Optional<Event> findByEventId(UUID eventId);
    Event saveEvent(Event event);
}
