package module;

import java.util.List;

public abstract class Ticket {
    protected Long id;
    protected Event event;
    protected List<User> userList;
    protected Double price;

    protected Ticket(Long id, Event event, List<User> userList, Double price) {
        this.id = id;
        this.event = event;
        this.userList = userList;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
