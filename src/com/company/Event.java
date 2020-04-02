package com.company;

import java.util.Date;

public class Event {
    private Long id;
    private EventLocation eventLocation;
    private String name;
    private Date eventDate;
    private EventType eventType;

    public Event(Long id, EventLocation eventLocation, String name, Date eventDate, EventType eventType) {
        this.id = id;
        this.eventLocation = eventLocation;
        this.name = name;
        this.eventDate = eventDate;
        this.eventType = eventType;
    }

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventLocation getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(EventLocation eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventLocation=" + eventLocation +
                ", name='" + name + '\'' +
                ", eventDate=" + eventDate +
                ", eventType=" + eventType +
                '}';
    }
}
