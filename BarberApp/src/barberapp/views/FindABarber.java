/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.views;

import barberapp.main.Globals;
import static barberapp.main.View.standardiseChildren;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class FindABarber extends JPanel {

    public static JTextField barberName = null;
    public static JComboBox allLocationsBox = null;
    public static JPanel leftFindABarberPanel = null;
    public static JPanel rightFindABarberPanel = null;

    /**
     * Creates the find a barber page.
     *
     * @param controller for FindABarber
     */
    public FindABarber(barberapp.main.Controller controller) {
        this.setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Globals.WHITE);
        mainPanel.setLayout(new BorderLayout(10, 10));

        // ** top panel **
        JPanel topPanel = new JPanel();
        topPanel.setBorder(Globals.border(Globals.DARKBLUE, 1));
        topPanel.setPreferredSize(new Dimension(Globals.WINDOW_WIDTH, (int) Globals.WINDOW_HEIGHT / 5));
        topPanel.setBackground(Globals.WHITE);
        topPanel.setLayout(new BorderLayout());

        JPanel topTopPanel = new JPanel();
        topTopPanel.setBackground(Globals.WHITE);
        topTopPanel.setLayout(new BorderLayout());

        JButton returnPage = new JButton("BACK");
        returnPage.setActionCommand("back to main customer");

        topTopPanel.add(returnPage, BorderLayout.EAST);

        JPanel leftTopPanel = new JPanel();
        leftTopPanel.setLayout(new BorderLayout());
        leftTopPanel.setBackground(Globals.WHITE);

        JLabel topLeftLabel = new JLabel("BY NAME");
        topLeftLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        JPanel centerLeftPanel = new JPanel();
        centerLeftPanel.setBackground(Globals.WHITE);

        barberName = new JTextField(10);
        JButton searchName = new JButton("SEARCH");
        searchName.setActionCommand("search barber name find");

        barberName.addKeyListener(new KeyAdapter(){
           @Override
           public void keyPressed (KeyEvent e) {
               if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                   searchName.doClick();
               }
           }
        });
        
        centerLeftPanel.add(barberName);
        centerLeftPanel.add(searchName);

        leftTopPanel.add(topLeftLabel, BorderLayout.NORTH);
        leftTopPanel.add(centerLeftPanel, BorderLayout.CENTER);

        JPanel rightTopPanel = new JPanel();
        rightTopPanel.setLayout(new BorderLayout());
        rightTopPanel.setBackground(Globals.WHITE);

        JLabel topRightTopLabel = new JLabel("BY LOCATION");
        JPanel centerRightTopPanel = new JPanel();
        centerRightTopPanel.setBackground(Globals.WHITE);

        allLocationsBox = new JComboBox(controller.getLocations().toArray());
        JButton searchLocation = new JButton("SEARCH");
        searchLocation.setActionCommand("search barber location find");

        centerRightTopPanel.add(allLocationsBox);
        centerRightTopPanel.add(searchLocation);

        rightTopPanel.add(topRightTopLabel, BorderLayout.NORTH);
        rightTopPanel.add(centerRightTopPanel, BorderLayout.SOUTH);

        topPanel.add(topTopPanel, BorderLayout.NORTH);
        topPanel.add(leftTopPanel, BorderLayout.WEST);
        topPanel.add(rightTopPanel, BorderLayout.EAST);

        // ** left panel **
        leftFindABarberPanel = new JPanel();
        leftFindABarberPanel.setPreferredSize(Globals.PADDING_X5);
        leftFindABarberPanel.setBackground(Globals.WHITE);
        leftFindABarberPanel.setLayout(new BorderLayout());

        // ** right panel **
        rightFindABarberPanel = new JPanel();
        rightFindABarberPanel.setPreferredSize(Globals.PADDING_X5);
        rightFindABarberPanel.setBackground(Globals.WHITE);
        rightFindABarberPanel.setLayout(new BorderLayout());

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftFindABarberPanel, BorderLayout.WEST);
        mainPanel.add(rightFindABarberPanel, BorderLayout.CENTER);

        this.add(new LoggedLeftPanel(controller), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);

        standardiseChildren(mainPanel, false, controller);
    }

    /**
     * Shows all barbers that show up on the search.
     *
     * @param searchBy "name" or "location"
     * @param controller controller for FindABarber
     */
    public static void searchForBarber(String searchBy, barberapp.main.Controller controller) {
        if (leftFindABarberPanel.getComponentCount() > 1) {
            leftFindABarberPanel.removeAll();
        }
        leftFindABarberPanel.add(new JLabel("SEARCH RESULT: "), BorderLayout.NORTH);
        ArrayList<HashMap<String, String>> searchResults;
        if (searchBy.equals("name")) {
            searchResults = controller.searchForBarberName();
        } else {
            searchResults = controller.searchForBarberLocation();
        }

        JPanel allBarbers = new JPanel();
        allBarbers.setLayout(new GridLayout(searchResults.size() + 1, 1));

        if (searchResults.size() > 0) {

            for (int i = 0; i < searchResults.size(); i++) {
                JPanel singleBarber = new JPanel();
                singleBarber.setLayout(new BorderLayout());
                singleBarber.setBorder(Globals.border(Globals.DARKBLUE, 1));
                singleBarber.setBackground(Globals.WHITE);
                singleBarber.setPreferredSize(new Dimension((int) (Globals.WINDOW_WIDTH / 3.5), (int) Globals.WINDOW_HEIGHT / 10));
                singleBarber.setMaximumSize(new Dimension((int) (Globals.WINDOW_WIDTH / 3.5), (int) Globals.WINDOW_HEIGHT / 10));
                singleBarber.setMinimumSize(new Dimension((int) (Globals.WINDOW_WIDTH / 3.5), (int) Globals.WINDOW_HEIGHT / 10));

                JPanel leftSingle = new JPanel();
                leftSingle.setBackground(Globals.WHITE);
                leftSingle.setLayout(new BorderLayout());
                leftSingle.add(new JLabel("<html>" + searchResults.get(i).get("name").toUpperCase() + "<br />"
                        + searchResults.get(i).get("address") + "<br />" + searchResults.get(i).get("town") + " - " + searchResults.get(i).get("location") + "<br />"
                        + "Phone No: " + searchResults.get(i).get("phone")
                        + "</html>"), BorderLayout.CENTER);

                JPanel rightSingle = new JPanel();
                rightSingle.setBackground(Globals.WHITE);

                JButton checkBarber = new JButton("CHECK");
                checkBarber.setPreferredSize(new Dimension((int) 100, (int) 45));
                checkBarber.setActionCommand("check availability " + searchResults.get(i).get("id"));

                rightSingle.add(checkBarber);

                singleBarber.add(leftSingle, BorderLayout.WEST);
                singleBarber.add(rightSingle, BorderLayout.CENTER);

                allBarbers.add(singleBarber);
            }

            JPanel p1 = new JPanel();
            p1.setPreferredSize(new Dimension((int) (Globals.WINDOW_WIDTH / 3.5), (int) (Globals.WINDOW_HEIGHT / 10)));
            p1.setBackground(Globals.WHITE);

            allBarbers.add(p1);

            JScrollPane sp = new JScrollPane(allBarbers);
            leftFindABarberPanel.add(sp, BorderLayout.CENTER);
        } else {
            leftFindABarberPanel.add(new JLabel("NO MATCHES FOUND"));
        }

        standardiseChildren(leftFindABarberPanel, true, controller);
        standardiseChildren(allBarbers, false, controller);
    }

    /**
     * Shows all available slots for the chosen barber.
     *
     * @param getB barber ID
     * @param controller controller for FindABarber
     */
    public static void showBarberAvailability(int getB, barberapp.main.Controller controller) {
        rightFindABarberPanel.removeAll();
        ArrayList<HashMap<String, String>> availableList = controller.getbarberAvailability(getB, null);
        HashMap<String, String> bInfo = controller.getBarber(getB);
        rightFindABarberPanel.add(new JLabel("<html>AVAILABLE TIMES: <br />"
                + bInfo.get("first name") + " " + bInfo.get("last name") + "<br />"
                + bInfo.get("address") + ", " + bInfo.get("town") + " - " + bInfo.get("location") + "<br />"
                + "Phone: " + bInfo.get("phone") + "</html>"), BorderLayout.NORTH);

        JPanel times = new JPanel();
        times.setBackground(Globals.WHITE);
        times.setLayout(new GridLayout(availableList.size(), 1, 3, 5));

        for (int i = 0; i < availableList.size(); i++) {
            JPanel st = new JPanel();
            st.setBackground(Globals.WHITE);
            st.setBorder(Globals.border(Globals.DARKBLUE, 1));
            st.add(new JLabel(availableList.get(i).get("date") + " | " + availableList.get(i).get("time")));
            JButton bookB = new JButton("BOOK");
            bookB.setActionCommand("book " + availableList.get(i).get("date") + " " + availableList.get(i).get("time") + " " + getB);
            st.add(bookB);
            times.add(st);
        }

        JScrollPane sp = new JScrollPane(times);
        rightFindABarberPanel.add(sp);

        standardiseChildren(rightFindABarberPanel, true, controller);
        standardiseChildren(times, false, controller);
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

    /**
     * Sets the text of the text field barberName.
     *
     * @param n name to set the text as
     */
    public static void setBarberName(String n) {
        barberName.setText(n);
    }

}
