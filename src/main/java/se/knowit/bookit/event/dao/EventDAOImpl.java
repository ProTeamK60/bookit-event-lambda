package se.knowit.bookit.event.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import se.knowit.bookit.event.model.Event;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EventDAOImpl implements EventDAO {

    private final static String EVENT_TABLE = "bookit_event";
    private final AmazonDynamoDB ddb;
    private final IdentityHandler identityHandler;

    public EventDAOImpl() {
        ddb = AmazonDynamoDBClientBuilder.defaultClient();
        identityHandler = new IdentityHandler();
    }

    @Override
    public Optional<Event> getEvent(String eventId) {
        Map<String, AttributeValue> item = ddb.getItem(EVENT_TABLE, queryMap("eventId", eventId)).getItem();
        return Optional.ofNullable(toEvent(item));
    }

    @Override
    public Event saveEvent(Event event) {
        assignRequiredIds(event);
        ddb.putItem(EVENT_TABLE, toItem(event));
        return event;
    }

    private void assignRequiredIds(Event event) {
        identityHandler.assignEventIdIfNotSet(event);
    }

    private Map<String, AttributeValue> toItem(Event event) {
        if (event == null) return null;
        Map<String, AttributeValue> itemMap = new HashMap<>();
        itemMap.put("eventId", new AttributeValue(event.getEventId().toString()));
        itemMap.put("name", new AttributeValue(event.getName()));
        itemMap.put("description", new AttributeValue(event.getDescription()));
        itemMap.put("organizer", new AttributeValue(event.getOrganizer()));
        itemMap.put("location", new AttributeValue(event.getLocation()));
        itemMap.put("eventStart", new AttributeValue(String.valueOf(event.getEventStart().toEpochMilli())));
        itemMap.put("eventEnd", new AttributeValue(String.valueOf(event.getEventEnd().toEpochMilli())));
        itemMap.put("deadlineRVSP", new AttributeValue(String.valueOf(event.getDeadlineRVSP().toEpochMilli())));
        return itemMap;
    }

    private Map<String, AttributeValue> queryMap(String key, String value) {
        Map<String, AttributeValue> queryMap = new HashMap<>();
        queryMap.put(key, new AttributeValue(value));
        return queryMap;
    }

    private Event toEvent(Map<String, AttributeValue> item) {
        if(item == null) return null;
        Event event = new Event();
        event.setEventId(UUID.fromString(item.get("eventId").getS()));
        event.setName(item.get("name").getS());
        event.setDescription(item.get("description").getS());
        event.setOrganizer(item.get("organizer").getS());
        event.setLocation(item.get("location").getS());
        event.setEventStart(Instant.ofEpochMilli(Long.valueOf(item.get("eventStart").getS())));
        event.setEventEnd(Instant.ofEpochMilli(Long.valueOf(item.get("eventEnd").getS())));
        event.setDeadlineRVSP(Instant.ofEpochMilli(Long.valueOf(item.get("deadlineRVSP").getS())));
        return event;
    }

    private class IdentityHandler {

        void assignEventIdIfNotSet(Event event) {
            if(event.getEventId() == null) {
                event.setEventId(UUID.randomUUID());
            }
        }
    }
}
