package com.company;

import java.util.Date;

public abstract class Ticket {
    protected Long id;
    protected User user;
    protected Event event;
    protected Date eventDate;
    protected Double price;

    protected Ticket(Long id, User user, Event event, Double price) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.eventDate = event.getEventDate();
        this.price = price;
    }

    protected Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
