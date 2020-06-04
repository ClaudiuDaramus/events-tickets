package repository;

import module.*;
import oracle.ucp.util.Pair;

import java.sql.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainRepository extends Thread{
    private static final String DB_URL = "";
    private static final String USER = "";
    private static final String PASS = "";

    private static final String GET_ALL_EVENTS =
            "SELECT *\n" +
            "FROM EVENTS e\n" +
            "    INNER JOIN EVENT_TYPES t\n" +
            "        ON e.EVENT_TYPE_ID = t.ID\n" +
            "    INNER JOIN EVENT_LOCATIONS l\n" +
            "        ON e.EVENTS_LOCATION_ID = l.ID";
    private static final String GET_ALL_EVENT_LOCATIONS = "SELECT * FROM EVENT_LOCATIONS";
    private static final String GET_ALL_EVENT_TYPES = "SELECT * FROM EVENT_TYPES";
    private static final String GET_ALL_USERS = "SELECT * FROM USERS";
    private static final String GET_ALL_USERS_FOR_TICKET =
            "SELECT *\n" +
            "FROM USERS u\n" +
            "    INNER JOIN USERS_TICKETS ut\n" +
            "        on u.ID = ut.USERS_ID\n" +
            "WHERE ut.TICKET_ID = ?";
    private static final String GET_USER_BY_ID =
            "SELECT *\n" +
            "FROM USERS\n" +
            "WHERE ID = ?";
    private static final String GET_TICKETS =
            "SELECT *\n" +
            "FROM tickets t\n" +
            "         INNER JOIN EVENTS e\n" +
            "                    ON t.EVENT_ID = e.ID\n" +
            "         INNER JOIN EVENT_TYPES t\n" +
            "                    ON e.EVENT_TYPE_ID = t.ID\n" +
            "         INNER JOIN EVENT_LOCATIONS l\n" +
            "                    ON e.EVENTS_LOCATION_ID = l.ID\n" +
            "         INNER JOIN USERS_TICKETS ut\n" +
            "             on t.ID = ut.TICKET_ID\n" +
            "WHERE t.TYPE = ?";
    private static final String GET_USER_TICKETS =
            "SELECT *\n" +
            "FROM tickets t\n" +
            "         INNER JOIN EVENTS e\n" +
            "                    ON t.EVENT_ID = e.ID\n" +
            "         INNER JOIN EVENT_TYPES t\n" +
            "                    ON e.EVENT_TYPE_ID = t.ID\n" +
            "         INNER JOIN EVENT_LOCATIONS l\n" +
            "                    ON e.EVENTS_LOCATION_ID = l.ID\n" +
            "         INNER JOIN USERS_TICKETS ut\n" +
            "             on t.ID = ut.TICKET_ID\n" +
            "WHERE t.TYPE = ? AND ut.USERS_ID = ?";
    private static final String GET_ALL_CHANGES_AUDIT =
            "SELECT *\n" +
            "FROM CHANGES_AUDIT a\n" +
            "    INNER JOIN USERS u\n" +
            "        ON a.USERS_ID = u.ID";
    private static final String GET_BONUSES_FOR_TICKET =
            "SELECT *\n" +
            "FROM BONUSES b\n" +
            "         INNER JOIN TICKETS_BONUSES tb\n" +
            "                    on tb.BONUS_ID = b.ID\n" +
            "WHERE tb.TICKET_ID = ?";
    private static final String ADD_CHANGES_AUDIT =
            "INSERT INTO CHANGES_AUDIT\n" +
            "VALUES(?, ?, ?, ?, ?)";
    private static final String COMMIT = "commit";
    private static final String GET_MAX_ID_AUDIT =
            "SELECT max(ID)\n" +
                    "    FROM CHANGES_AUDIT";
    private static final String DELETE_CHANGES_AUDIT =
            "DELETE FROM CHANGES_AUDIT\n" +
            "WHERE ID = ?";
    private static long MAX_ID_AUDIT = 0;

    public static Connection getDbConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement preparedStatement = connection.prepareStatement(GET_MAX_ID_AUDIT);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            MAX_ID_AUDIT = rs.getLong(1);
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public List<Event> getEvents() throws SQLException, ParseException {
        List<Event> returnedList = new ArrayList<>();
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(GET_ALL_EVENTS);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            EventLocation eventLocation = new EventLocation(rs.getLong(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12));
            EventType eventType = new EventType(rs.getLong(6), rs.getString(7));
            Event event = new Event(rs.getLong(1), eventLocation, rs.getString(4), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString(5)), eventType);
            returnedList.add(event);
        }
        recordAudit("events");
        return returnedList;
    }

    public List<EventLocation> getEventLocations() throws SQLException{
        List<EventLocation> returnedList = new ArrayList<>();
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(GET_ALL_EVENT_LOCATIONS);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            EventLocation eventLocation = new EventLocation(rs.getLong("ID"), rs.getString("ADDRESS"), rs.getString("NAME"), rs.getString("COUNTRY"), rs.getString("CITY"));
            returnedList.add(eventLocation);
        }
        recordAudit("event_location");
        return returnedList;
    }



    public List<EventType> getEventTypes() throws SQLException{
        List<EventType> returnedList = new ArrayList<>();
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(GET_ALL_EVENT_TYPES);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            EventType eventType = new EventType(rs.getLong("ID"), rs.getString("NAME"));
            returnedList.add(eventType);
        }
        recordAudit("event_type");
        return returnedList;
    }

    public List<User> getUsers() throws SQLException, ParseException {
        List<User> returnedList = new ArrayList<>();
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(GET_ALL_USERS);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            User user = new User(rs.getLong("ID"), rs.getString("USERNAME"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("EMAILADDRESS"), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString("DATECREATED")), rs.getLong("PHONENUMBER"));
            returnedList.add(user);
        }
        recordAudit("users");
        return returnedList;
    }

    public User getUserData(Long userId) throws SQLException, ParseException {
        User user = null;
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(GET_USER_BY_ID);
        preparedStatement.setLong(1, userId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            user = new User(rs.getLong("ID"), rs.getString("USERNAME"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("EMAILADDRESS"), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString("DATECREATED")), rs.getLong("PHONENUMBER"));
        }
        recordAudit("users");
        return user;
    }
    public Pair<List<StandardTicket>, List<PremiumTicket>> getUserTickets(Long userId) throws SQLException, ParseException {
        List<PremiumTicket> premiumTickets;
        List<StandardTicket> standardTickets;
        premiumTickets = getPremiumTickets(false, userId);
        standardTickets = getStandardTickets(false, userId);
        Pair<List<StandardTicket>, List<PremiumTicket>> resultPair = new Pair<>(standardTickets, premiumTickets);
        return resultPair;
    }

    public List<PremiumTicket> getPremiumTickets(boolean all, Long userId) throws SQLException, ParseException {
        List<PremiumTicket> returnedList = new ArrayList<>();
        ArrayList<EventBonus> eventBonuses = new ArrayList<>();
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = all ? getDbConnection().prepareStatement(GET_TICKETS) : getDbConnection().prepareStatement(GET_USER_TICKETS);
        preparedStatement.setInt(1, 0);
        if (!all) {
            preparedStatement.setLong(2, userId);
        }
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            EventLocation eventLocation = new EventLocation(rs.getLong(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16));
            EventType eventType = new EventType(rs.getLong(10), rs.getString(11));
            Event event = new Event(rs.getLong(5), eventLocation, rs.getString(8), rs.getDate(9), eventType);
            PremiumTicket premiumTicket = new PremiumTicket(rs.getLong(1), event, users, rs.getDouble(4), eventBonuses);
            returnedList.add(premiumTicket);
        }
        returnedList.forEach(premiumTicket -> {
            try {
                PreparedStatement preparedStatementBonus = getDbConnection().prepareStatement(GET_BONUSES_FOR_TICKET);
                preparedStatementBonus.setLong(1,  premiumTicket.getId());
                ResultSet rsBonus = preparedStatementBonus.executeQuery();
                ArrayList<EventBonus> eventBonusArrayList = new ArrayList<>();
                while (rsBonus.next()){
                    EventBonus eventBonus = new EventBonus(rsBonus.getLong(1), rsBonus.getString(2));
                    eventBonusArrayList.add(eventBonus);
                }
                premiumTicket.setEventBonus(eventBonusArrayList);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        returnedList.forEach(premiumTicket -> {
            try {
                PreparedStatement preparedStatementBonus = getDbConnection().prepareStatement(GET_ALL_USERS_FOR_TICKET);
                preparedStatementBonus.setLong(1,  premiumTicket.getId());
                ResultSet rsUser = preparedStatementBonus.executeQuery();
                ArrayList<EventBonus> eventBonusArrayList = new ArrayList<>();
                while (rsUser.next()){
                    users.add(new User(rsUser.getLong("ID"), rsUser.getString("USERNAME"), rsUser.getString("FIRSTNAME"), rsUser.getString("LASTNAME"), rsUser.getString("EMAILADDRESS"), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rsUser.getString("DATECREATED")), rsUser.getLong("PHONENUMBER")));
                }
                premiumTicket.setUserList(users);
            } catch (SQLException | ParseException throwables) {
                throwables.printStackTrace();
            }
        });
        recordAudit("ticket");
        return returnedList;
    }

    public List<StandardTicket> getStandardTickets(boolean all, Long userId) throws SQLException, ParseException {
        List<StandardTicket> returnedList = new ArrayList<>();
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = all ? getDbConnection().prepareStatement(GET_TICKETS) : getDbConnection().prepareStatement(GET_USER_TICKETS);
        preparedStatement.setInt(1, 1);
        if (!all) {
            preparedStatement.setLong(2, userId);
        }
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            EventLocation eventLocation = new EventLocation(rs.getLong(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16));
            EventType eventType = new EventType(rs.getLong(10), rs.getString(11));
            Event event = new Event(rs.getLong(5), eventLocation, rs.getString(8), rs.getDate(9), eventType);
            StandardTicket standardTicket = new StandardTicket(rs.getLong(1), event, users, rs.getDouble(4));
            returnedList.add(standardTicket);
        }
        returnedList.forEach(standardTicket -> {
            try {
                PreparedStatement preparedStatementBonus = getDbConnection().prepareStatement(GET_ALL_USERS_FOR_TICKET);
                preparedStatementBonus.setLong(1,  standardTicket.getId());
                ResultSet rsUser = preparedStatementBonus.executeQuery();
                ArrayList<EventBonus> eventBonusArrayList = new ArrayList<>();
                while (rsUser.next()){
                    users.add(new User(rsUser.getLong("ID"), rsUser.getString("USERNAME"), rsUser.getString("FIRSTNAME"), rsUser.getString("LASTNAME"), rsUser.getString("EMAILADDRESS"), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rsUser.getString("DATECREATED")), rsUser.getLong("PHONENUMBER")));
                }
                standardTicket.setUserList(users);
            } catch (SQLException | ParseException throwables) {
                throwables.printStackTrace();
            }
        });
        recordAudit("ticket");
        return returnedList;
    }

    public List<AuditRecord> getAuditRecords() throws SQLException, ParseException {
        List<AuditRecord> returnedList = new ArrayList<>();
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(GET_ALL_CHANGES_AUDIT);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            User user = new User(rs.getLong(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString(11)), rs.getLong(12));
            AuditRecord auditRecord = new AuditRecord(rs.getLong(1), user, rs.getString(3), rs.getString(4), new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse(rs.getString(5)));
            returnedList.add(auditRecord);
        }
        return returnedList;
    }

    public boolean addAuditRecord(AuditRecord auditRecord) throws SQLException{
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(ADD_CHANGES_AUDIT);
        preparedStatement.setLong(1, auditRecord.getId());
        preparedStatement.setLong(2, auditRecord.getUser().getId());
        preparedStatement.setString(3, auditRecord.getTableName());
        preparedStatement.setString(4, auditRecord.getThreadName());
        preparedStatement.setDate(5, new java.sql.Date(auditRecord.getAuditDate().getTime()));

        boolean response = preparedStatement.executeUpdate() > 0;
        preparedStatement = getDbConnection().prepareStatement(COMMIT);
        preparedStatement.executeQuery();
        return response;
    }

    public void recordAudit(String tableName) throws SQLException {
        addAuditRecord(new AuditRecord(MAX_ID_AUDIT + 1, new User(1L,"","","","", new Date(),0L), tableName, Thread.currentThread().getName(), new Date()));
    }

    public boolean deleteLastAuditRecord() throws SQLException{
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(DELETE_CHANGES_AUDIT);
        preparedStatement.setInt(1, (int) MAX_ID_AUDIT);

        boolean response = preparedStatement.executeUpdate() > 0;
        preparedStatement = getDbConnection().prepareStatement(COMMIT);
        preparedStatement.executeQuery();
        return response;
    }

}
