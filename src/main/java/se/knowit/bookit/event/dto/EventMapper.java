package se.knowit.bookit.event.dto;

import se.knowit.bookit.event.model.Event;

import java.time.Instant;
import java.util.UUID;

public class EventMapper {

    public Event fromDto(EventDTO dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setOrganizer(dto.getOrganizer());
        if (notNullOrBlank(dto.getEventId())) {
            event.setEventId(UUID.fromString(dto.getEventId()));
        }
        event.setEventStart(Instant.ofEpochMilli(dto.getEventStart()));
        event.setEventEnd(Instant.ofEpochMilli(dto.getEventEnd()));
        event.setDeadlineRVSP(Instant.ofEpochMilli(dto.getDeadlineRVSP()));
        return event;
    }

    public EventDTO toDto(Event event) {
        EventDTO dto = new EventDTO();
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        dto.setOrganizer(event.getOrganizer());
        if (event.getEventId() != null) {
            dto.setEventId(event.getEventId().toString());
        }
        dto.setEventStart(event.getEventStart().toEpochMilli());
        dto.setEventEnd(event.getEventEnd().toEpochMilli());
        dto.setDeadlineRVSP(event.getDeadlineRVSP().toEpochMilli());
        return dto;
    }


    private boolean notNullOrBlank(String test) {
        return test != null && !test.isBlank();
    }
}
