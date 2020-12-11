/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.views;

import barberapp.main.Globals;
import barberapp.main.View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class BarberMain extends JPanel {

    /**
     * Creates home page for barber.
     *
     * @param controller controller for BarberMain
     */
    public BarberMain(barberapp.main.Controller controller) {
        this.setLayout(new BorderLayout(10, 0));
        this.setBackground(Globals.WHITE);

        // **main panel**
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(Globals.RIGHT_PANEL_DIMENSION);
        mainPanel.setBackground(Globals.WHITE);
        mainPanel.setLayout(new BorderLayout(20, 20));

        // center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Globals.WHITE);
        centerPanel.setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p1.setPreferredSize(Globals.PADDING_Y1);
        p1.setBackground(Globals.WHITE);
        p1.setForeground(Color.WHITE);

        // *** CENTRAL INFO PANEL ***
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Globals.WHITE);
        infoPanel.setLayout(new BorderLayout(0, 20));
        infoPanel.setBorder(Globals.border(Globals.DARKBLUE, 2));

        JPanel topLabelPanel = new JPanel();
        topLabelPanel.setLayout(new BorderLayout());

        JButton seeAllBookings = new JButton("ALL BOOKINGS");
        seeAllBookings.setActionCommand("view barber bookings");

        topLabelPanel.add(new JLabel("MY BOOKINGS:"), BorderLayout.WEST);
        topLabelPanel.add(seeAllBookings, BorderLayout.EAST);
        topLabelPanel.setBackground(Globals.WHITE);

        JPanel multipleBookingsPanel = new JPanel();
        multipleBookingsPanel.setBackground(Globals.WHITE);

        try {
            ArrayList<HashMap<String, String>> upcoming = controller.getBarberUpcomingBookings();
            multipleBookingsPanel.setLayout(new GridLayout(upcoming.size(), 1, 10, 20));
            multipleBookingsPanel.setBackground(Globals.WHITE);

            for (int i = 0; i < upcoming.size(); i++) {
                JPanel booking = new JPanel();
                booking.setBackground(Globals.WHITE);
                booking.setPreferredSize(new Dimension(250, 230));
                booking.setLayout(new BorderLayout());
                booking.setBorder(Globals.border(Globals.DARKBLUE, 1));

                JPanel left = new JPanel();
                left.setLayout(new BorderLayout());
                left.setBackground(Globals.WHITE);

                JPanel leftTop = new JPanel();
                leftTop.add(new JLabel("UPCOMING BOOKING:"));
                leftTop.setBackground(Globals.WHITE);

                JPanel leftBottom = new JPanel();
                leftBottom.setLayout(new BorderLayout());
                leftBottom.setBackground(Globals.WHITE);

                JLabel cust = new JLabel("<html>Customer: " + upcoming.get(i).get("customer name") + "<br />Phone: " + upcoming.get(i).get("customer phone") + "</html>");
                cust.setBackground(Globals.WHITE);
                JLabel dt = new JLabel("Date: " + Globals.formatDateFromSQL(upcoming.get(i).get("date")));
                dt.setBackground(Globals.WHITE);

                JPanel hrPanel = new JPanel();
                hrPanel.setBackground(Globals.WHITE);
                hrPanel.add(new JLabel("<html>Status: " + upcoming.get(i).get("status") + "<br />" + upcoming.get(i).get("time") + "</html>"), BorderLayout.CENTER);

                JLabel num = new JLabel("Booking ID: " + upcoming.get(i).get("id"));
                num.setBackground(Globals.WHITE);

                leftBottom.add(cust, BorderLayout.NORTH);
                leftBottom.add(dt, BorderLayout.WEST);
                leftBottom.add(hrPanel, BorderLayout.CENTER);
                leftBottom.add(num, BorderLayout.SOUTH);

                left.add(leftTop, BorderLayout.NORTH);
                left.add(leftBottom, BorderLayout.SOUTH);

                JPanel right = new JPanel();
                right.setBackground(Globals.WHITE);
                right.setLayout(new BorderLayout());
                JButton cancel = new JButton("CANCEL");
                cancel.setActionCommand("cancel booking " + upcoming.get(i).get("id"));

                JButton accept = new JButton("CONFIRM");
                accept.setActionCommand("confirm " + upcoming.get(i).get("id"));
                if (upcoming.get(i).get("status").equals("requested")) {
                    right.add(accept, BorderLayout.NORTH);
                }
                right.add(cancel, BorderLayout.SOUTH);

                booking.add(left, BorderLayout.CENTER);
                booking.add(right, BorderLayout.EAST);

                multipleBookingsPanel.add(booking);
                View.standardiseChildren(multipleBookingsPanel, true, controller);
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            multipleBookingsPanel.add(new JLabel("NO BOOKINGS FOUND"));
        }

        JScrollPane multiplePane = new JScrollPane(multipleBookingsPanel);
        multiplePane.setBackground(Globals.WHITE);

        infoPanel.add(topLabelPanel, BorderLayout.PAGE_START);
        infoPanel.add(multiplePane, BorderLayout.CENTER);

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

        JButton setAvailability = new JButton("<html>SET MY<br />AVAILABILITY</html>");
        setAvailability.setActionCommand("go to set availability");
        setAvailability.setPreferredSize(Globals.REGULAR_BUTTON_DIMENSION);

        rightPanel.add(setAvailability, BorderLayout.NORTH);
        rightPanel.add(logout, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        this.add(new LoggedLeftPanel(controller), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);

        View.standardiseChildren(mainPanel, false, controller);
    }
}
