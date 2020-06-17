import module.*;
import repository.MainRepository;
import service.EventsService;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main{
    private final static String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    private static String dataSource = "empty";
    private static User currentUser = null;
    private static JFrame frame;
    private static JPanel dataPickerPanel;
    private static JPanel userPickerPanel;
    private static JPanel menuPanel;

    //local Storage variables
    private static final EventsService eventsService = new EventsService();;

    //database variables
    private static final MainRepository mainRepository = new MainRepository();

    private static JPanel getDataPickerPanel() {
        JPanel dataPickerPanel = new JPanel();
        dataPickerPanel.setLayout(null);

        JLabel label = new JLabel("Choose your data source:");
        label.setBounds(10, 10, 150, 25);
        dataPickerPanel.add(label);

        JButton localButton = new JButton("Local Storage");
        localButton.setBounds(10, 50, 150,25);
        dataPickerActionListener(dataPickerPanel, localButton);

        JButton dbButton = new JButton("Oracle Database");
        dbButton.setBounds(10, 90, 150,25);
        dataPickerActionListener(dataPickerPanel, dbButton);

        return dataPickerPanel;
    }

    private static void dataPickerActionListener(JPanel dataPickerPanel, JButton button) {
        button.addActionListener(actionEvent -> {
            dataSource = ((JButton) actionEvent.getSource()).getText();
            try {
                userPickerPanel = getUserPickerPanel();
            } catch (SQLException | ParseException throwable) {
                throwable.printStackTrace();
            }
            frame.remove(dataPickerPanel);
            frame.add(userPickerPanel);
            frame.revalidate();
            frame.repaint();
            frame.setVisible(true);
        });
        dataPickerPanel.add(button);
    }

    private static JPanel getUserPickerPanel() throws SQLException, ParseException {
        JPanel userPickerPanel = new JPanel();
        userPickerPanel.setLayout(null);

        JLabel label = new JLabel("Choose your user:");
        label.setBounds(10, 10, 150, 25);
        userPickerPanel.add(label);

        List<JButton> userButtons = new ArrayList<>();
        List<User> userList = null;
        var bounds = new Object() {
            int x = 10;
            int y = 0;
            int width = 1100;
            int height = 25;
        };
        if(dataSource.equals("Local Storage")) {
            userList = eventsService.getUsers();
        } else if(dataSource.equals("Oracle Database")) {
            userList = mainRepository.getUsers();
        }

        List<User> finalUserList = userList;
        userList.forEach(user -> {
            bounds.y += 40;
            JButton userButton = new JButton(user.toString());
            userButton.setBounds(bounds.x, bounds.y, bounds.width,bounds.height);
            userPickerActionListener(userPickerPanel, userButton, finalUserList);
        });

        return userPickerPanel;
    }

    private static void userPickerActionListener(JPanel userPickerPanel, JButton button, List<User> userList) {
        button.addActionListener(actionEvent -> {
            int userIndex = Integer.parseInt(String.valueOf(((JButton) actionEvent.getSource()).getText().charAt(8)));
            currentUser = userList.get(userIndex);
            if(dataSource.equals("Local Storage")) {
                eventsService.setCurrentUser(currentUser);
            }
            menuPanel = getMenuPanel();
            frame.remove(userPickerPanel);
            frame.add(menuPanel);
            frame.revalidate();
            frame.repaint();
            frame.setVisible(true);
        });
        userPickerPanel.add(button);
    }

    private static JPanel getMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);

        JLabel label = new JLabel("Choose an action:");
        label.setBounds(10, 10, 150, 25);
        menuPanel.add(label);

        List<JButton> menuButtons = new ArrayList<>();
        JButton menuButton;
        var bounds = new Object() {
            int x = 10;
            int y = 0;
            int width = 180;
            int height = 25;
        };
        DefaultListModel listModel = new DefaultListModel();

        bounds.y += 40;
        menuButton = new JButton("Display User Details");
        menuButton.setBounds(bounds.x, bounds.y, bounds.width,bounds.height);
        menuButton.addActionListener(actionEvent -> {
            listModel.clear();
            listModel.addElement(currentUser);
        });
        menuButtons.add(menuButton);

        bounds.y += 40;
        menuButton = new JButton("Display Tickets for User");
        menuButton.setBounds(bounds.x, bounds.y, bounds.width,bounds.height);
        menuButton.addActionListener(actionEvent -> {
            Map<List<StandardTicket>, List<PremiumTicket>> listMap;
            List<StandardTicket> standardTickets = null;
            List<PremiumTicket> premiumTickets = null;
            if(dataSource.equals("Local Storage")) {
                listMap = eventsService.getTicketsForUser();
                Map.Entry<List<StandardTicket>, List<PremiumTicket>> entry = listMap.entrySet().iterator().next();
                standardTickets = entry.getKey();
                premiumTickets = entry.getValue();
            } else if(dataSource.equals("Oracle Database")) {
                try {
                    listMap = mainRepository.getUserTickets(currentUser.getId());
                    Map.Entry<List<StandardTicket>, List<PremiumTicket>> entry = listMap.entrySet().iterator().next();
                    standardTickets = entry.getKey();
                    premiumTickets = entry.getValue();
                } catch (SQLException | ParseException throwable) {
                    throwable.printStackTrace();
                }
            }
            listModel.clear();
            standardTickets.forEach(listModel::addElement);
            premiumTickets.forEach(listModel::addElement);
        });
        menuButtons.add(menuButton);

        bounds.y += 40;
        menuButton = new JButton("Display Events");
        menuButton.setBounds(bounds.x, bounds.y, bounds.width,bounds.height);
        menuButton.addActionListener(actionEvent -> {
            List<Event> events = null;
            if(dataSource.equals("Local Storage")) {
                events = eventsService.getEvents();
            } else if(dataSource.equals("Oracle Database")) {
                try {
                    events = mainRepository.getEvents();
                } catch (SQLException | ParseException throwable) {
                    throwable.printStackTrace();
                }
            }
            listModel.clear();
            events.forEach(listModel::addElement);
        });
        menuButtons.add(menuButton);

        bounds.y += 40;
        menuButton = new JButton("Display Event Locations");
        menuButton.setBounds(bounds.x, bounds.y, bounds.width,bounds.height);
        menuButton.addActionListener(actionEvent -> {
            List<EventLocation> eventLocations = null;
            if(dataSource.equals("Local Storage")) {
                eventLocations = eventsService.getEventLocations();
            } else if(dataSource.equals("Oracle Database")) {
                try {
                    eventLocations = mainRepository.getEventLocations();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
            listModel.clear();
            eventLocations.forEach(listModel::addElement);
        });
        menuButtons.add(menuButton);

        bounds.y += 40;
        menuButton = new JButton("Display Standard Tickets");
        menuButton.setBounds(bounds.x, bounds.y, bounds.width,bounds.height);
        menuButton.addActionListener(actionEvent -> {
            List<StandardTicket> standardTickets = null;
            if(dataSource.equals("Local Storage")) {
                standardTickets = eventsService.getStandardTickets();
            } else if(dataSource.equals("Oracle Database")) {
                try {
                    standardTickets = mainRepository.getStandardTickets(true, currentUser.getId());
                } catch (SQLException | ParseException throwable) {
                    throwable.printStackTrace();
                }
            }
            listModel.clear();
            standardTickets.forEach(listModel::addElement);
        });
        menuButtons.add(menuButton);

        bounds.y += 40;
        menuButton = new JButton("Display Premium Tickets");
        menuButton.setBounds(bounds.x, bounds.y, bounds.width,bounds.height);
        menuButton.addActionListener(actionEvent -> {
            List<PremiumTicket> premiumTickets = null;
            if(dataSource.equals("Local Storage")) {
                premiumTickets = eventsService.getPremiumTickets();
            } else if(dataSource.equals("Oracle Database")) {
                try {
                    premiumTickets = mainRepository.getPremiumTickets(true, currentUser.getId());
                } catch (SQLException | ParseException throwable) {
                    throwable.printStackTrace();
                }
            }
            listModel.clear();
            premiumTickets.forEach(listModel::addElement);
        });
        menuButtons.add(menuButton);

        bounds.y += 40;
        menuButton = new JButton("Display Audit Records");
        menuButton.setBounds(bounds.x, bounds.y, bounds.width,bounds.height);
        menuButton.addActionListener(actionEvent -> {
            List<AuditRecord> auditRecords = null;
            if(dataSource.equals("Local Storage")) {
                auditRecords = eventsService.getAuditRecords();
            } else if(dataSource.equals("Oracle Database")) {
                try {
                    auditRecords = mainRepository.getAuditRecords();
                } catch (SQLException | ParseException throwable) {
                    throwable.printStackTrace();
                }
            }
            listModel.clear();
            auditRecords.forEach(listModel::addElement);
        });
        menuButtons.add(menuButton);

        JList<?> jList = new JList(listModel);
        jList.setBounds(200,10,1130,600);
        menuPanel.add(jList);
        menuButtons.forEach(menuPanel::add);

        return menuPanel;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
        Class.forName(JDBC_DRIVER);
        //swing variables
        frame = new JFrame("Event Tickets");
        frame.setSize(1360, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dataPickerPanel = getDataPickerPanel();
        frame.add(dataPickerPanel);
        frame.setVisible(true);
    }
}
