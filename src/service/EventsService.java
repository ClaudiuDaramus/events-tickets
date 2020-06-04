package service;

import module.*;
import oracle.ucp.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class EventsService {
    private final DataService dataService = new DataService();
    private User currentUser;
    private ArrayList<User> userArrayList;
    private ArrayList<EventType> eventTypes;
    private ArrayList<EventLocation> eventLocations;
    private ArrayList<Event> events;
    private ArrayList<StandardTicket> standardTickets;
    private ArrayList<PremiumTicket> premiumTickets;
    private ArrayList<EventBonus> eventBonuses;
    private ArrayList<AuditRecord> auditRecords;

    public EventsService() {
        currentUser = new User();
        getData();
    }
    
    private void getData() {
        eventBonuses = dataService.readDataForEventBonuses();
        userArrayList = dataService.readDataForUsers();
        eventTypes = dataService.readDataForEventTypes();
        eventLocations = dataService.readDataForEventLocations();
        events = dataService.readDataForEvents(eventLocations, eventTypes);
        standardTickets = dataService.readDataForStandardTickets(userArrayList, events);
        premiumTickets = dataService.readDataForPremiumTickets(userArrayList, events, eventBonuses);
        auditRecords = dataService.readDataForAudit(userArrayList);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<User> getUsers() {
        return userArrayList;
    }

    public User getUserById(Long userId) {
        for(User user: userArrayList) {
            if(userId.equals(user.getId()))
                return user;
        }
        return null;
    }

    public Pair<List<StandardTicket>, List<PremiumTicket>> getTicketsForUser() {
        List<StandardTicket> standardTicketsForUser = new ArrayList<>();
        List<PremiumTicket> premiumTicketsForUser = new ArrayList<>();
        
        for(StandardTicket standardTicket: standardTickets) {
            standardTicket.getUserList().forEach(ticketUser -> {
                if(ticketUser.equals(currentUser))
                    standardTicketsForUser.add(standardTicket);
            });
        }
        for(PremiumTicket premiumTicket: premiumTickets) {
            premiumTicket.getUserList().forEach(ticketUser -> {
                if(ticketUser.equals(currentUser))
                    premiumTicketsForUser.add(premiumTicket);
            });
        }
        dataService.writeActionToAudit("getTicketsForUser", currentUser);
        
        return new Pair<>(standardTicketsForUser, premiumTicketsForUser);
    }

    public ArrayList<Event> getEvents() {
        dataService.writeActionToAudit("getEvents", currentUser);
        return events;
    }

    public ArrayList<EventType> getEventType() {
        dataService.writeActionToAudit("getEventType", currentUser);
        return eventTypes;
    }

    public ArrayList<EventBonus> getEventBonus() {
        dataService.writeActionToAudit("getEventBonus", currentUser);
        return eventBonuses;
    }

    public ArrayList<EventLocation> getEventLocations() {
        dataService.writeActionToAudit("getEventLocations", currentUser);
        return eventLocations;
    }

    public ArrayList<StandardTicket> getStandardTickets() {
        dataService.writeActionToAudit("getStandardTickets", currentUser);
        return standardTickets;
    }

    public ArrayList<PremiumTicket> getPremiumTickets() {
        dataService.writeActionToAudit("getPremiumTickets", currentUser);
        return premiumTickets;
    }

    public ArrayList<AuditRecord> getAuditRecords() {
        dataService.writeActionToAudit("getAuditRecords", currentUser);
        return auditRecords;
    }
}