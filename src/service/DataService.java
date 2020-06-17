package service;

import module.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataService extends Thread{
    private final String filePath = "";
    private Integer maxAudit;

    public ArrayList<EventBonus> readDataForEventBonuses() {
        ArrayList<EventBonus> eventBonuses = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + "bonus.csv"))) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                String[] recordData = record.split(",");
                eventBonuses.add(new EventBonus(Long.parseLong(recordData[0]), recordData[1]));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return eventBonuses;
    }

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
                String[] ticketUsers = recordData[5].split(" ");
                ArrayList<User> ticketUsersList = new ArrayList<>();
                for(String userIndex: ticketUsers) {
                    ticketUsersList.add(users.get(Integer.parseInt(userIndex)));
                }
                if(recordData[0].equals("0")) {
                    standardTickets.add(new StandardTicket(Long.valueOf(recordData[1]), events.get(Integer.parseInt(recordData[3])), ticketUsersList, Double.parseDouble(recordData[4])));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return standardTickets;
    }

    public ArrayList<PremiumTicket> readDataForPremiumTickets(ArrayList<User> users, ArrayList<Event> events, ArrayList<EventBonus> eventBonuses) {
        ArrayList<PremiumTicket> premiumTickets = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + "tickets.csv"))) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                String[] recordData = record.split(",");
                if(recordData[0].equals("1")) {
                    String[] ticketUsers = recordData[5].split(" ");
                    ArrayList<User> ticketUsersList = new ArrayList<>();
                    for(String userIndex: ticketUsers) {
                        ticketUsersList.add(users.get(Integer.parseInt(userIndex)));
                    }
                    String[] bonus = recordData[6].split(" ");
                    ArrayList<EventBonus> usedBonuses = new ArrayList<>();
                    for(String bonusIndex: bonus) {
                        usedBonuses.add(eventBonuses.get(Integer.parseInt(bonusIndex)));
                    }
                    premiumTickets.add(new PremiumTicket(Long.valueOf(recordData[1]), events.get(Integer.parseInt(recordData[3])), ticketUsersList, Double.parseDouble(recordData[4]), usedBonuses));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return premiumTickets;
    }

    private void getMaxAuditIndex() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + "max_audit.csv"))) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                maxAudit = Integer.valueOf(record);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int nextMaxAudit = maxAudit + 1;
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath + "max_audit.csv"))){
            bufferedWriter.append(Integer.toString(nextMaxAudit));
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<AuditRecord> readDataForAudit(ArrayList<User> users) {
        ArrayList<AuditRecord> auditRecords = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + "audit.csv"))) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                String[] recordData = record.split(",");
                auditRecords.add(new AuditRecord(Long.parseLong(recordData[0]), users.get(Integer.parseInt(recordData[1])), recordData[2], recordData[3], new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(recordData[4])));
            }
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
        return auditRecords;
    }

    public void writeActionToAudit(String action, User user){
        Date date = new Date();
        getMaxAuditIndex();

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath + "audit.csv",true))){
            bufferedWriter.append(maxAudit.toString());
            bufferedWriter.append(",");
            bufferedWriter.append(user.getId().toString());
            bufferedWriter.append(",");
            bufferedWriter.append(Thread.currentThread().getName());
            bufferedWriter.append(",");
            bufferedWriter.append(action);
            bufferedWriter.append(",");
            bufferedWriter.append(date.toString());
            bufferedWriter.append("\n");
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
