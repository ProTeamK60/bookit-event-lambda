package se.knowit.bookit.event.model;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class Event {
    private UUID eventId;
    private String name;
    private String description;
    private Instant eventStart;
    private Instant eventEnd;
    private Instant deadlineRVSP;
    private String location;
    private String organizer;
}
