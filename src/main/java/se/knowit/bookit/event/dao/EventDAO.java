package se.knowit.bookit.event.dao;

import se.knowit.bookit.event.model.Event;

import java.util.Optional;

public interface EventDAO {
    Optional<Event> getEvent(String eventId);
    Event saveEvent(Event event);
}
