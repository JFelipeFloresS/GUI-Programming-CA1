/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.views;

import barberapp.main.Controller;
import barberapp.main.Globals;
import barberapp.views.LoggedLeftPanel;
import static barberapp.main.View.standardiseChildren;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class CustomerMain extends JPanel {

    public static JTextField barberName = null;
    public static JComboBox allLocationsBox = null;

    /**
     * Creates home page for customer.
     *
     * @param controller controller for CustomerMain
     */
    public CustomerMain(Controller controller) {
        this.setLayout(new BorderLayout(10, 10));

        // **main panel**
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(Globals.RIGHT_PANEL_DIMENSION);
        mainPanel.setBackground(Globals.WHITE);
        mainPanel.setLayout(new BorderLayout(10, 10));

        // center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Globals.WHITE);
        centerPanel.setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p1.setPreferredSize(Globals.PADDING_Y1);
        p1.setBackground(Globals.WHITE);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Globals.WHITE);
        infoPanel.setLayout(new BorderLayout());

        // *** search panel ***
        JPanel searchPanel = new JPanel();
        searchPanel.setPreferredSize(Globals.LOGIN_PANEL_DIMENSION);
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBorder(Globals.border(Color.BLACK, 2));
        searchPanel.setBackground(Globals.WHITE);

        JPanel searchTopPanel = new JPanel();
        searchTopPanel.setBackground(Globals.BLUE);
        JLabel findALabel = new JLabel("FIND A BARBER");
        findALabel.setForeground(Globals.WHITE);
        searchTopPanel.add(findALabel);

        JPanel searchMainPanel = new JPanel();
        searchMainPanel.setBackground(Globals.WHITE);
        searchMainPanel.setLayout(new BorderLayout());

        JPanel searchName = new JPanel();
        searchName.setBackground(Globals.WHITE);
        searchName.setLayout(new BorderLayout());

        JPanel topSearchName = new JPanel();
        topSearchName.setBackground(Globals.WHITE);
        barberName = new JTextField(8);
        topSearchName.add(new JLabel("By name:"));
        topSearchName.add(barberName);

        JPanel centerSearchName = new JPanel();
        centerSearchName.setBackground(Globals.WHITE);
        JButton searchNameButton = new JButton("SEARCH");
        searchNameButton.setActionCommand("search barber name main");

        centerSearchName.add(searchNameButton);

        searchName.add(topSearchName, BorderLayout.CENTER);
        searchName.add(centerSearchName, BorderLayout.SOUTH);

        JPanel searchLocation = new JPanel();
        searchLocation.setBackground(Globals.WHITE);
        searchLocation.setLayout(new BorderLayout());

        JPanel topSearchLocation = new JPanel();
        topSearchLocation.setBackground(Globals.WHITE);
        allLocationsBox = new JComboBox(controller.getLocations());

        topSearchLocation.add(new JLabel("By location:"));
        topSearchLocation.add(allLocationsBox);

        JPanel centerSearchLocation = new JPanel();
        centerSearchLocation.setBackground(Globals.WHITE);
        JButton searchLocationButton = new JButton("SEARCH");
        searchLocationButton.setActionCommand("search barber location main");

        centerSearchLocation.add(searchLocationButton);

        searchLocation.add(topSearchLocation, BorderLayout.CENTER);
        searchLocation.add(centerSearchLocation, BorderLayout.SOUTH);

        searchMainPanel.add(searchName, BorderLayout.WEST);
        searchMainPanel.add(searchLocation, BorderLayout.EAST);

        searchPanel.add(searchTopPanel, BorderLayout.PAGE_START);
        searchPanel.add(searchMainPanel, BorderLayout.CENTER);

        // *** next booking panel ***
        JPanel nextBookingPanel = new JPanel();
        nextBookingPanel.setBackground(Globals.WHITE);
        nextBookingPanel.setPreferredSize(Globals.LOGIN_PANEL_DIMENSION);
        nextBookingPanel.setBorder(Globals.border(Color.BLACK, 2));
        nextBookingPanel.setLayout(new BorderLayout());

        JPanel leftNextBooking = new JPanel();
        leftNextBooking.setBackground(Globals.WHITE);
        leftNextBooking.setLayout(new BorderLayout());

        JPanel topNext = new JPanel();
        topNext.setBackground(Globals.WHITE);
        topNext.add(new JLabel("NEXT BOOKING:"));

        JPanel centerNext = new JPanel();
        centerNext.setBackground(Globals.WHITE);
        centerNext.setPreferredSize(Globals.PADDING_X5);
        centerNext.setLayout(new BorderLayout());
        JButton cancelBooking = new JButton("<html>CANCEL<br />BOOKING</html>");
        try {
            HashMap<String, String> nextBooking = controller.getNextCustomerBooking();
            centerNext.add(new JLabel("<html>Date: " + nextBooking.get("date") + "<br />Barber: " + nextBooking.get("name") + "</html>"), BorderLayout.NORTH);
            centerNext.add(new JLabel("<html>Time: " + nextBooking.get("time") + " | Phone: " + nextBooking.get("phone") + "<br />"
                    + nextBooking.get("address") + ", " + nextBooking.get("town") + " - " + nextBooking.get("location") + "<br />"
                    + "Status: " + nextBooking.get("status").toUpperCase() + "</html>"), BorderLayout.CENTER);
        } catch (Exception e) {
            centerNext.add(new JLabel("NO UPCOMING BOOKINGS FOUND"));
            cancelBooking.setVisible(false);
        }

        leftNextBooking.add(topNext, BorderLayout.NORTH);
        leftNextBooking.add(centerNext, BorderLayout.CENTER);

        JPanel rightNextBooking = new JPanel();
        rightNextBooking.setBackground(Globals.WHITE);
        rightNextBooking.setPreferredSize(Globals.PADDING_X1);
        rightNextBooking.setLayout(new BorderLayout());

        JButton viewBookings = new JButton("<html>VIEW<br /> BOOKINGS</html>");
        viewBookings.setActionCommand("view customer bookings");

        cancelBooking.setActionCommand("customer booking cancel");

        rightNextBooking.add(viewBookings, BorderLayout.NORTH);
        rightNextBooking.add(cancelBooking, BorderLayout.SOUTH);

        nextBookingPanel.add(leftNextBooking, BorderLayout.WEST);
        nextBookingPanel.add(rightNextBooking, BorderLayout.EAST);

        infoPanel.add(searchPanel, BorderLayout.NORTH);
        infoPanel.add(nextBookingPanel, BorderLayout.SOUTH);

        JPanel p2 = new JPanel();
        p2.setPreferredSize(Globals.PADDING_Y3);
        p2.setBackground(Globals.WHITE);

        centerPanel.add(p1, BorderLayout.NORTH);
        centerPanel.add(infoPanel, BorderLayout.CENTER);
        centerPanel.add(p2, BorderLayout.SOUTH);

        // padding/back button
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(Globals.PADDING_X3);
        rightPanel.setBackground(Globals.WHITE);
        rightPanel.setLayout(new BorderLayout());

        JButton logout = new JButton("LOG OUT");
        logout.setActionCommand("log out");
        logout.setPreferredSize(Globals.REGULAR_BUTTON_DIMENSION);

        rightPanel.add(logout, BorderLayout.NORTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        this.add(new LoggedLeftPanel(controller), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);

        standardiseChildren(centerPanel, false, controller);
        standardiseChildren(rightPanel, true, controller);
        viewBookings.setFont(Globals.SMALL_FONT);
        cancelBooking.setFont(Globals.SMALL_FONT);
    }

    /**
     * @return combo box with all locations there are barbers in
     */
    public static JComboBox getAllLocationsBox() {
        return allLocationsBox;
    }

    /**
     * @return barber name to be searched from the text field barberName
     */
    public static String getBarberName() {
        return barberName.getText();
    }

    /**
     * @return location selected by user
     */
    public static String getSelectedLocation() {
        return allLocationsBox.getSelectedItem().toString();
    }

    /**
     * Sets what location is selected.
     *
     * @param l location to be selected
     */
    public static void setSelectedLocation(String l) {
        allLocationsBox.setSelectedItem(l);
    }

}
