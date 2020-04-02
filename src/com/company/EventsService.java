package com.company;

import com.sun.tools.jconsole.JConsoleContext;

import java.util.ArrayList;

public class EventsService {
    public void displayUsernames(ArrayList<User> userArrayList) {
        for(User user: userArrayList) {
            System.out.print(user.getUsername() + " ");
        }
        System.out.println();
    }

    public User getUserByUsername(String username, ArrayList<User> users) {
        for(User user: users) {
            if(username.equals(user.getUsername()))
                return user;
        }

        return null;
    }

    public void displayUserDetails(User user) {
        System.out.println(user);
    }

    public void displayTicketsForUser(User user, ArrayList<StandardTicket> standardTickets, ArrayList<PremiumTicket> premiumTickets) {
        for(StandardTicket standardTicket: standardTickets) {
            if(standardTicket.getUser().equals(user))
                System.out.println(standardTicket);
        }

        for(PremiumTicket premiumTicket: premiumTickets) {
            if(premiumTicket.getUser().equals(user))
                System.out.println(premiumTicket);
        }
    }

    public Boolean displayTicketsForEventName(String eventName, ArrayList<StandardTicket> standardTickets, ArrayList<PremiumTicket> premiumTickets) {
        Boolean found = false;
        for(StandardTicket standardTicket: standardTickets) {
            if(standardTicket.getEvent().getName().equals(eventName)) {
                System.out.println(standardTicket);
                found = true;
            }
        }

        for(PremiumTicket premiumTicket: premiumTickets) {
            if(premiumTicket.getEvent().getName().equals(eventName)) {
                System.out.println(premiumTicket);
                found = true;
            }
        }

        return found;
    }

    public void displayUsers(ArrayList<User> userArrayList) {
        for(User user: userArrayList) {
            System.out.println(user);
        }
    }

    public void displayEvents(ArrayList<Event> events) {
        for(Event event: events) {
            System.out.println(event);
        }
    }

    public void displayEventLocations(ArrayList<EventLocation> eventLocations) {
        for(EventLocation eventLocation: eventLocations) {
            System.out.println(eventLocation);
        }
    }

    public void displayStandardTickets(ArrayList<StandardTicket> standardTickets) {
        for(StandardTicket standardTicket: standardTickets) {
            System.out.println(standardTicket);
        }
    }

    public void displayPremiumTickets(ArrayList<PremiumTicket> premiumTickets) {
        for(PremiumTicket premiumTicket: premiumTickets) {
            System.out.println(premiumTicket);
        }
    }
}
