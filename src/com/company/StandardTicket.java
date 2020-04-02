package com.company;

import java.util.Date;

public class StandardTicket extends Ticket {
    protected StandardTicket(Long id, User user, Event event, Double price) {
        super(id, user, event, price);
    }

    @Override
    public String toString() {
        return "StandardTicket{" +
                "id=" + id +
                ", user=" + user +
                ", event=" + event +
                ", eventDate=" + eventDate +
                ", price=" + price +
                '}';
    }
}
