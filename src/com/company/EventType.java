package com.company;

public class EventType implements Comparable<EventType> {
    private Long id;
    private String name;

    public EventType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public EventType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(EventType eventType) {
        return  Long.compare(this.id, eventType.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventType)) return false;

        EventType eventType = (EventType) o;

        if (getId() != null ? !getId().equals(eventType.getId()) : eventType.getId() != null) return false;
        return getName() != null ? getName().equals(eventType.getName()) : eventType.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
