package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<User> userArrayList = new ArrayList<>();
        TreeSet<EventType> eventTypes = new TreeSet<>();
        ArrayList<EventLocation> eventLocations = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<StandardTicket> standardTickets = new ArrayList<>();
        ArrayList<PremiumTicket> premiumTickets = new ArrayList<>();
        User currentUser = null;
        EventsService eventsService = new EventsService();
        Scanner in = new Scanner(System.in);
        Boolean running = true;

        userArrayList.add(new User(0L, "jackJones", "Jack", "Jones", "jack@jones.com", new Date(), 33342L));
        userArrayList.add(new User(1L, "mikeApple", "Mike", "Apple", "mike@apple.com", new Date(), 83341L));
        userArrayList.add(new User(2L, "tomGarron", "Tom", "Garron", "tom@garron.com", new Date(), 93222L));
        userArrayList.add(new User(3L, "juliaTomato", "Julia", "Tomato", "julia@tomato.com", new Date(), 77342L));

        eventTypes.add(new EventType(1L, "Art Exposition"));
        eventTypes.add(new EventType(0L, "Concert"));

        eventLocations.add(new EventLocation(0L, "somewhere", "Arena Nationala", "Romania", "Bucuresti"));
        eventLocations.add(new EventLocation(1L, "nowhere", "Romexpo", "Romania", "Bucuresti"));

        events.add(new Event(0L, eventLocations.get(0), "Burning Skies Tour", new Date(), eventTypes.last()));
        events.add(new Event(1L, eventLocations.get(1), "Blue Wave Expo", new Date(), eventTypes.first()));

        standardTickets.add(new StandardTicket(0L, userArrayList.get(0), events.get(0), 99.99));
        standardTickets.add(new StandardTicket(1L, userArrayList.get(1), events.get(0), 99.99));
        standardTickets.add(new StandardTicket(2L, userArrayList.get(3), events.get(1), 150.59));

        premiumTickets.add(new PremiumTicket(0L, userArrayList.get(0), events.get(0), 139.99));
        premiumTickets.add(new PremiumTicket(1L, userArrayList.get(1), events.get(0), 139.99));
        premiumTickets.add(new PremiumTicket(2L, userArrayList.get(2), events.get(1), 299.45));
        premiumTickets.add(new PremiumTicket(3L, userArrayList.get(3), events.get(1), 299.45));

        do {
            String response;
            System.out.println("Choose your action (type the number):");

            if(currentUser == null) {
                System.out.println("0) Log In");
                System.out.println("1) List users");
                System.out.println("2) Stop actions");
                response = in.nextLine().trim();

                if(response.equals("0")) {
                    System.out.println("Type your username:");
                    response = in.nextLine().trim();
                    currentUser = eventsService.getUserByUsername(response, userArrayList);

                    if(currentUser == null) {
                        System.out.println("The user wasn't found!");
                    }
                } else if(response.equals("1")){
                    eventsService.displayUsernames(userArrayList);
                } else if(response.equals("2")) {
                    System.out.println("Bye Bye!");
                    running = false;
                } else {
                    System.out.println("The action wasn't found!");
                }
            } else {
                System.out.println("0) Display User Details");
                System.out.println("1) Display Tickets for User");
                System.out.println("2) Stop actions");
                System.out.println("3) Display Tickets For Event Name");
                System.out.println("4) Display Users");
                System.out.println("5) Display Events");
                System.out.println("6) Display Event Locations");
                System.out.println("7) Display Standard Tickets");
                System.out.println("8) Display Premium Tickets");
                response = in.nextLine().trim();

                if(response.equals("0")) {
                    eventsService.displayUserDetails(currentUser);
                } else if(response.equals("1")){
                    eventsService.displayTicketsForUser(currentUser, standardTickets, premiumTickets);
                } else if(response.equals("2")) {
                    System.out.println("Bye Bye!");
                    running = false;
                } else if(response.equals("3")) {
                    Boolean found;
                    System.out.println("Type the event name:");
                    response = in.nextLine().trim();

                    found = eventsService.displayTicketsForEventName(response, standardTickets, premiumTickets);
                    if(!found) {
                        System.out.println("The event wasn't found!");
                    }
                } else if(response.equals("4")) {
                    eventsService.displayUsers(userArrayList);
                } else if(response.equals("5")) {
                    eventsService.displayEvents(events);
                } else if(response.equals("6")) {
                    eventsService.displayEventLocations(eventLocations);
                } else if(response.equals("7")) {
                    eventsService.displayStandardTickets(standardTickets);
                } else if(response.equals("8")) {
                    eventsService.displayPremiumTickets(premiumTickets);
                } else {
                    System.out.println("The action wasn't found!");
                }
            }
        } while(running);
    }
}
