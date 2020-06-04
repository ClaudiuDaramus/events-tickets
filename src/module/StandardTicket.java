package module;

import java.util.List;

public class StandardTicket extends Ticket {
    public StandardTicket(Long id, Event event, List<User> userList, Double price) {
        super(id, event,userList, price);
    }

    @Override
    public String toString() {
        return "StandardTicket{" +
                "id=" + id +
                ", event=" + event +
                ", userList=" + userList +
                ", price=" + price +
                '}';
    }
}
