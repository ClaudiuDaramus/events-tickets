package com.company;

import java.util.ArrayList;

public class PremiumTicket extends Ticket{
    private ArrayList<EventBonus> eventBonuses;

    protected PremiumTicket(Long id, User user, Event event, Double price) {
        super(id, user, event, price);
        ArrayList<EventBonus> eventBonuses = new ArrayList<>();

        if(event.getEventType().getName().equals("Art Exposition")) {
            eventBonuses.add(EventBonus.FREEDRINKS);
            eventBonuses.add(EventBonus.FREEFOOD);
        } else {
            eventBonuses.add(EventBonus.BACKSTAGEACCESS);
            eventBonuses.add(EventBonus.EXTRAMERCH);
            eventBonuses.add(EventBonus.REPETITIONSACCESS);
        }

        this.eventBonuses = eventBonuses;
    }

    public PremiumTicket() {
        super();
    }

    public ArrayList<EventBonus> getEventBonuses() {
        return eventBonuses;
    }

    public void setEventBonuses(ArrayList<EventBonus> eventBonuses) {
        this.eventBonuses = eventBonuses;
    }

    @Override
    public String toString() {
        return "PremiumTicket{" +
                "eventBonuses=" + eventBonuses +
                ", id=" + id +
                ", user=" + user +
                ", event=" + event +
                ", eventDate=" + eventDate +
                ", price=" + price +
                '}';
    }
}
