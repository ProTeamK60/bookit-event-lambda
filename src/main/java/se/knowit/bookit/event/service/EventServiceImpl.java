package se.knowit.bookit.event.service;

import se.knowit.bookit.event.dao.EventDAO;
import se.knowit.bookit.event.dao.EventDAOImpl;
import se.knowit.bookit.event.model.Event;

import java.util.Optional;
import java.util.UUID;

public class EventServiceImpl implements EventService{

    private final EventDAO eventDao;

    public EventServiceImpl() {
        eventDao = new EventDAOImpl();
    }

    @Override
    public Optional<Event> findByEventId(UUID eventId) {
        return eventDao.getEvent(eventId.toString());
    }

    @Override
    public Event saveEvent(Event event) {
        return eventDao.saveEvent(event);
    }
}
