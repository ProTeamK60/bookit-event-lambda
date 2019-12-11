package se.knowit.bookit.event.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class EventDTO {
    private String eventId;
    private String name;
    private String description;
    private String location;
    private String organizer;
    private Long eventStart;
    private Long eventEnd;
    private Long deadlineRVSP;

    public static EventDTO fromJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, EventDTO.class);
    }
}
