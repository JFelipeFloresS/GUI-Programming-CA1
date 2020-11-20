/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp;

import static barberapp.View.standardiseChildren;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class AvailabilityPage extends JPanel {

    /**
     * Creates a page for a barber to enter their availability.
     *
     * @param controller controller for AvailabilityPage
     */
    public AvailabilityPage(Controller controller) {
        this.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(Globals.rightPanelDimension);
        mainPanel.setBackground(Globals.WHITE);
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();

        // ** 3 main panels **
        //*left panel - calendar*
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(Globals.paddingX5);
        leftPanel.setBackground(Globals.WHITE);

        JPanel mainCalendar = new JPanel();
        mainCalendar.setPreferredSize(new Dimension((int) (Globals.windowWidth / 3.5), (int) (Globals.windowHeight / 1.1)));
        mainCalendar.setBorder(Globals.border(Globals.DARKBLUE, 2));

        leftPanel.add(mainCalendar);

        JPanel p2 = new JPanel();
        p2.setPreferredSize(new Dimension((int) (Globals.windowWidth / 4), (int) (Globals.windowHeight / 8)));

        JLabel pickLabel = new JLabel("PICK DATE:");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime localToday = LocalDateTime.now();
        String today = String.valueOf(dtf.format(localToday));

        Globals.date = new JComboBox[3];
        String[] days = new String[31];
        for (int i = 0; i < days.length; i++) {
            days[i] = "";
            if (i < 9) {
                days[i] = "0";
            }
            days[i] += String.valueOf(i + 1);
        }
        String[] months = new String[12];
        for (int i = 0; i < months.length; i++) {
            months[i] = "";
            if (i < 9) {
                months[i] = "0";
            }
            months[i] += String.valueOf(i + 1);
        }
        String[] years = new String[2];
        years[0] = today.substring(6);
        years[1] = String.valueOf((Integer.parseInt(today.substring(6))) + 1);

        Globals.date[0] = new JComboBox(days);
        Globals.date[0].setSelectedItem(today.substring(0, 2));

        Globals.date[1] = new JComboBox(months);
        Globals.date[1].setSelectedItem(today.substring(3, 5));

        Globals.date[2] = new JComboBox(years);
        Globals.date[2].setSelectedItem(today.substring(6));

        JPanel p3 = new JPanel();
        p3.setPreferredSize(new Dimension((int) (Globals.windowWidth / 4), (int) (Globals.windowHeight / 8)));

        // !!!!FIX DATE PICKER TO GET PICKED DATE!!!!!
        Globals.selectDate = new JButton("PICK DATE");
        Globals.selectDate.setActionCommand("select date");

        p2.add(pickLabel);
        mainCalendar.add(p2);
        mainCalendar.add(Globals.date[0]);
        mainCalendar.add(Globals.date[1]);
        mainCalendar.add(Globals.date[2]);
        mainCalendar.add(p3);
        mainCalendar.add(Globals.selectDate);

        //*middle panel - times*
        JPanel midPanel = new JPanel();
        midPanel.setBackground(Globals.WHITE);

        Globals.mainTime = new JPanel();
        Globals.mainTime.setBorder(Globals.border(Globals.DARKBLUE, 2));
        Globals.mainTime.setPreferredSize(new Dimension((int) (Globals.windowWidth / 3.8), (int) (Globals.windowHeight / 1.1)));

        midPanel.add(Globals.mainTime);

        //*right panel - log out*
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(Globals.paddingX2);
        rightPanel.setBackground(Globals.WHITE);

        JPanel p4 = new JPanel();
        p4.setPreferredSize(Globals.paddingY3);
        p4.setBackground(Globals.WHITE);

        JButton goBack = new JButton("BACK");
        goBack.setActionCommand("back to main barber");

        JPanel p5 = new JPanel();
        p5.setPreferredSize(Globals.paddingY3);
        p5.setBackground(Globals.WHITE);

        JButton logOut = new JButton("LOG OUT");
        logOut.setActionCommand("log out");

        rightPanel.add(p4);
        rightPanel.add(goBack);
        rightPanel.add(p5);
        rightPanel.add(logOut);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(midPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        this.add(new LoggedLeftPanel(controller), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);

        standardiseChildren(mainPanel, true, controller);
    }

    /**
     * Shows all hours for the date picked by barber and whether or not that
     * slot is set as available.
     */
    public void setPickedDate(Controller controller) {
        if (Globals.allTimesSP != null) {
            Globals.allTimesSP.removeAll();
        }
        if (Globals.mainTime != null) {
            Globals.mainTime.removeAll();
        }

        Globals.pickedDate = new JLabel();
        Globals.mainTime.add(Globals.pickedDate);

        Globals.allTimes = new JPanel();
        Globals.allTimes.setBackground(Globals.WHITE);
        Globals.allTimes.setLayout(new GridLayout(48, 1));

        String pickedDay = Globals.date[0].getSelectedItem().toString();
        String pickedMonth = Globals.date[1].getSelectedItem().toString();
        Globals.pickedDate.setText(pickedDay + "/" + pickedMonth + "/" + Globals.date[2].getSelectedItem());
        Globals.isAvailable = controller.checkBarberAvailability(controller.getSessionID(), Globals.getpickedDate());

        int h = 0;
        boolean isHalf = false;
        String m = ":00";
        String currentTime;
        Globals.availableCheckBox = new JCheckBox[48];

        for (int i = 0; i < 48; i++) {
            JPanel singleTime = new JPanel();
            singleTime.setBorder(Globals.border(Globals.DARKBLUE, 1));
            singleTime.setPreferredSize(new Dimension((int) (Globals.windowWidth / 4.5), (int) (Globals.windowHeight / 10)));
            singleTime.setLayout(new BorderLayout());

            String addZero = "";
            if (h < 10) {
                addZero = "0";
            }
            currentTime = addZero + String.valueOf(h) + m;
            JLabel thisTime = new JLabel(currentTime);
            JPanel availabilityPanel = new JPanel();
            Globals.availableCheckBox[i] = new JCheckBox();
            Globals.availableCheckBox[i].setName(currentTime);

            if (Globals.isAvailable[i]) {
                Globals.availableCheckBox[i].setSelected(true);
            }

            if (isHalf) {
                h++;
                m = ":00";
            } else {
                m = ":30";
            }
            isHalf = !isHalf;

            availabilityPanel.add(new JLabel("Available"));
            availabilityPanel.add(Globals.availableCheckBox[i]);

            singleTime.add(thisTime, BorderLayout.CENTER);
            singleTime.add(availabilityPanel, BorderLayout.EAST);

            Globals.allTimes.add(singleTime);
        }

        Globals.allTimesSP = new JScrollPane(Globals.allTimes);
        Globals.allTimesSP.setPreferredSize(new Dimension((int) (Globals.windowWidth / 3.9), (int) (Globals.windowHeight / 1.4)));
        Globals.mainTime.add(Globals.allTimesSP);

        Globals.enterAvailability = new JButton("ENTER AVAILABILITY");
        Globals.enterAvailability.setActionCommand("enter barber availability");

        Globals.mainTime.add(Globals.enterAvailability);

        standardiseChildren(Globals.mainTime, true, controller);
        standardiseChildren(Globals.allTimes, true, controller);
    }
}
