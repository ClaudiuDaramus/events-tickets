package com.company;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataService {
    private final String filePath = "C:\\Users\\duduc\\Documents\\GitHub\\events-tickets\\Data\\";

    public ArrayList<User> readDataForUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + "users.csv"))) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                String[] recordData = record.split(",");
                users.add(new User(Long.parseLong(recordData[0]), recordData[1], recordData[2], recordData[3], recordData[4], new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse(recordData[5]), Long.parseLong(recordData[6])));
            }
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public ArrayList<EventType> readDataForEventTypes() {
        ArrayList<EventType> eventTypes = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + "event-type.csv"))) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                String[] recordData = record.split(",");
                eventTypes.add(new EventType(Long.valueOf(recordData[0]), recordData[1]));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return eventTypes;
    }

    public ArrayList<EventLocation> readDataForEventLocations() {
        ArrayList<EventLocation> eventLocations = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + "event-location.csv"))) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                String[] recordData = record.split(",");
                eventLocations.add(new EventLocation(Long.parseLong(recordData[0]), recordData[1], recordData[2], recordData[3], recordData[4]));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return eventLocations;
    }

    public ArrayList<Event> readDataForEvents(ArrayList<EventLocation> eventLocations, ArrayList<EventType> eventTypes) {
        ArrayList<Event> events = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + "events.csv"))) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                String[] recordData = record.split(",");
                events.add(new Event(Long.valueOf(recordData[0]), eventLocations.get(Integer.parseInt(recordData[1])), recordData[2], new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse(recordData[3]), eventTypes.get(Integer.parseInt(recordData[4]))));
            }
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
        return events;
    }

    public ArrayList<StandardTicket> readDataForStandardTickets(ArrayList<User> users, ArrayList<Event> events) {
        ArrayList<StandardTicket> standardTickets = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + "tickets.csv"))) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                String[] recordData = record.split(",");
                if(recordData[0].equals("0")) {
                    standardTickets.add(new StandardTicket(Long.valueOf(recordData[1]), users.get(Integer.parseInt(recordData[2])), events.get(Integer.parseInt(recordData[3])), Double.parseDouble(recordData[4])));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return standardTickets;
    }

    public ArrayList<PremiumTicket> readDataForPremiumTickets(ArrayList<User> users, ArrayList<Event> events) {
        ArrayList<PremiumTicket> premiumTickets = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + "tickets.csv"))) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                String[] recordData = record.split(",");
                if(recordData[0].equals("1")) {
                    premiumTickets.add(new PremiumTicket(Long.valueOf(recordData[1]), users.get(Integer.parseInt(recordData[2])), events.get(Integer.parseInt(recordData[3])), Double.parseDouble(recordData[4])));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return premiumTickets;
    }

    public void writeActionToAudit(String action){
        Date date = new Date();

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath + "audit.csv",true))){
            bufferedWriter.append(action);
            bufferedWriter.append(",");
            bufferedWriter.append(date.toString());
            bufferedWriter.append("\n");
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
