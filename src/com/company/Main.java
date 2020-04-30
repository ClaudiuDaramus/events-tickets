package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //declaring variables
        ArrayList<User> userArrayList;
        ArrayList<EventType> eventTypes;
        ArrayList<EventLocation> eventLocations;
        ArrayList<Event> events;
        ArrayList<StandardTicket> standardTickets;
        ArrayList<PremiumTicket> premiumTickets;
        User currentUser = null;
        EventsService eventsService = new EventsService();
        Scanner in = new Scanner(System.in);
        boolean running = true;
        DataService dataService = new DataService();

        //reading data from CSV
        userArrayList = dataService.readDataForUsers();
        eventTypes = dataService.readDataForEventTypes();
        eventLocations = dataService.readDataForEventLocations();
        events = dataService.readDataForEvents(eventLocations, eventTypes);
        standardTickets = dataService.readDataForStandardTickets(userArrayList, events);
        premiumTickets = dataService.readDataForPremiumTickets(userArrayList, events);

        //running menu
        do {
            String response;
            System.out.println("Choose your action (type the number):");

            if (currentUser == null) {
                System.out.println("0) Log In");
                System.out.println("1) List users");
                System.out.println("2) Stop actions");
                response = in.nextLine().trim();

                switch (response) {
                    case "0":
                        System.out.println("Type your username:");
                        response = in.nextLine().trim();
                        currentUser = eventsService.getUserByUsername(response, userArrayList);

                        if (currentUser == null) {
                            System.out.println("The user wasn't found!");
                        }
                        break;
                    case "1":
                        eventsService.displayUsernames(userArrayList);
                        break;
                    case "2":
                        System.out.println("Bye Bye!");
                        running = false;
                        break;
                    default:
                        System.out.println("The action wasn't found!");
                        break;
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

                switch (response) {
                    case "0":
                        eventsService.displayUserDetails(currentUser);
                        break;
                    case "1":
                        eventsService.displayTicketsForUser(currentUser, standardTickets, premiumTickets);
                        break;
                    case "2":
                        System.out.println("Bye Bye!");
                        running = false;
                        break;
                    case "3":
                        Boolean found;
                        System.out.println("Type the event name:");
                        response = in.nextLine().trim();

                        found = eventsService.displayTicketsForEventName(response, standardTickets, premiumTickets);
                        if (!found) {
                            System.out.println("The event wasn't found!");
                        }
                        break;
                    case "4":
                        eventsService.displayUsers(userArrayList);
                        break;
                    case "5":
                        eventsService.displayEvents(events);
                        break;
                    case "6":
                        eventsService.displayEventLocations(eventLocations);
                        break;
                    case "7":
                        eventsService.displayStandardTickets(standardTickets);
                        break;
                    case "8":
                        eventsService.displayPremiumTickets(premiumTickets);
                        break;
                    default:
                        System.out.println("The action wasn't found!");
                        break;
                }
            }
        } while (running);
    }
}
