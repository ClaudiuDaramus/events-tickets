package module;

import java.util.ArrayList;
import java.util.List;

public class PremiumTicket extends Ticket{
    private ArrayList<EventBonus> eventBonus;

    public PremiumTicket(Long id, Event event, List<User> userList, Double price, ArrayList<EventBonus> eventBonus) {
        super(id, event,userList, price);

        this.eventBonus = eventBonus;
    }

    public PremiumTicket() {
        super();
    }

    public ArrayList<EventBonus> getEventBonus2s() {
        return eventBonus;
    }

    public void setEventBonus(ArrayList<EventBonus> eventBonus) {
        this.eventBonus = eventBonus;
    }

    @Override
    public String toString() {
        return "PremiumTicket{" +
                "eventBonus=" + eventBonus +
                ", id=" + id +
                ", event=" + event +
                ", userList=" + userList +
                ", price=" + price +
                '}';
    }
}
