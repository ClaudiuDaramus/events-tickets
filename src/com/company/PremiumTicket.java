package com.company;

import java.util.ArrayList;

public class PremiumTicket extends Ticket{
    private ArrayList<EventBonus2> eventBonus2s;

    protected PremiumTicket(Long id, User user, Event event, Double price) {
        super(id, user, event, price);
        ArrayList<EventBonus2> eventBonus2s = new ArrayList<>();

        if(event.getEventType().getName().equals("Art Exposition")) {
            eventBonus2s.add(EventBonus2.FREEDRINKS);
            eventBonus2s.add(EventBonus2.FREEFOOD);
        } else {
            eventBonus2s.add(EventBonus2.BACKSTAGEACCESS);
            eventBonus2s.add(EventBonus2.EXTRAMERCH);
            eventBonus2s.add(EventBonus2.REPETITIONSACCESS);
        }

        this.eventBonus2s = eventBonus2s;
    }

    public PremiumTicket() {
        super();
    }

    public ArrayList<EventBonus2> getEventBonus2s() {
        return eventBonus2s;
    }

    public void setEventBonus2s(ArrayList<EventBonus2> eventBonus2s) {
        this.eventBonus2s = eventBonus2s;
    }

    @Override
    public String toString() {
        return "PremiumTicket{" +
                "eventBonuses=" + eventBonus2s +
                ", id=" + id +
                ", user=" + user +
                ", event=" + event +
                ", eventDate=" + eventDate +
                ", price=" + price +
                '}';
    }
}
