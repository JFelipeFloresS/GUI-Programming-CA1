/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.views;

import barberapp.assets.Images;
import barberapp.main.Controller;
import barberapp.main.Globals;
import static barberapp.main.View.standardiseChildren;
import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.beans.PropertyChangeListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class AvailabilityPage extends JPanel {

    public static JComboBox[] date = null;
    public static JButton selectDate = null;
    public static JPanel mainTime = null;
    public static JScrollPane allTimesSP = null;
    public static JLabel pickedDate = null;
    public static JPanel allTimes = null;
    public static boolean[] isAvailable = null;
    public static JCheckBox[] availableCheckBox = null;
    public static JButton enterAvailability = null;
    public static JDateChooser calendar = null;
    public static JTextField text = null;

    /**
     * Creates a page for a barber to enter their availability.
     *
     * @param controller controller for AvailabilityPage
     */
    public AvailabilityPage(barberapp.main.Controller controller) {
        this.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(Globals.RIGHT_PANEL_DIMENSION);
        mainPanel.setBackground(Globals.WHITE);
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();

        // ** 3 main panels **
        //*left panel - calendar*
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(Globals.PADDING_X5);
        leftPanel.setBackground(Globals.WHITE);

        JPanel mainCalendar = new JPanel();
        mainCalendar.setPreferredSize(new Dimension((int) (Globals.WINDOW_WIDTH / 3.5), (int) (Globals.WINDOW_HEIGHT / 1.1)));
        mainCalendar.setBorder(Globals.border(Globals.DARKBLUE, 2));

        leftPanel.add(mainCalendar);

        JPanel p2 = new JPanel();
        p2.setPreferredSize(new Dimension((int) (Globals.WINDOW_WIDTH / 4), (int) (Globals.WINDOW_HEIGHT / 8)));

        JLabel pickLabel = new JLabel("PICK DATE:");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime localToday = LocalDateTime.now();
        String today = String.valueOf(dtf.format(localToday));

        date = new JComboBox[3];
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

        date[0] = new JComboBox(days);
        date[0].setSelectedItem(today.substring(0, 2));

        date[1] = new JComboBox(months);
        date[1].setSelectedItem(today.substring(3, 5));

        date[2] = new JComboBox(years);
        date[2].setSelectedItem(today.substring(6));

        popupListener listener = new popupListener();
        for (int i = 0; i < date.length; i++) {
            date[i].setEnabled(false);
            date[i].setName("availability date");
            //date[i].setActionCommand("click");
            //date[i].getUI().setPopupVisible(date[i], false);
            //date[i].addPopupMenuListener(listener);
            /**
             * The following code was retrieved from
             * https://stackoverflow.com/questions/4827635/better-readability-contrast-in-a-disabled-jcombobox
             */
            date[i].setRenderer(new DefaultListCellRenderer() {
                @Override
                public void paint(Graphics g) {
                    setForeground(Color.BLACK);
                    super.paint(g);
                }
            });
        }

        JPanel p3 = new JPanel();
        p3.setPreferredSize(new Dimension((int) (Globals.WINDOW_WIDTH / 16), (int) (Globals.WINDOW_HEIGHT / 8)));

        // !!!!FIX DATE PICKER TO GET PICKED DATE!!!!!
        selectDate = new JButton("PICK DATE");
        selectDate.setActionCommand("select date");

        text = new JTextField(0);
        text.getDocument().addDocumentListener(new docListener());
        text.setVisible(false);

        JCalendar c = new JCalendar();

        IDateEditor editor = new Editor(c, text);

        calendar = new JDateChooser(c, Timestamp.valueOf(localToday), "dd-MM-yyyy", editor);
        calendar.setPreferredSize(new Dimension(40, 40));
        calendar.setMinimumSize(new Dimension(40, 40));
        calendar.setMaximumSize(new Dimension(40, 40));
        calendar.getCalendarButton().setIcon(new ImageIcon(new Images().calendarImage(30)));
        calendar.getCalendarButton().setText("Calendar");
        calendar.getCalendarButton().setIconTextGap(-190);

        p2.add(pickLabel);
        mainCalendar.add(p2);
        mainCalendar.add(date[0]);
        mainCalendar.add(date[1]);
        mainCalendar.add(date[2]);
        mainCalendar.add(calendar);
        mainCalendar.add(p3);
        mainCalendar.add(selectDate);

        //*middle panel - times*
        JPanel midPanel = new JPanel();
        midPanel.setBackground(Globals.WHITE);

        mainTime = new JPanel();
        mainTime.setBorder(Globals.border(Globals.DARKBLUE, 2));
        mainTime.setPreferredSize(new Dimension((int) (Globals.WINDOW_WIDTH / 3.8), (int) (Globals.WINDOW_HEIGHT / 1.1)));

        midPanel.add(mainTime);

        //*right panel - log out*
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(Globals.PADDING_X2);
        rightPanel.setBackground(Globals.WHITE);
        rightPanel.setLayout(new BorderLayout(50, 300));

        JButton goBack = new JButton("BACK");
        goBack.setActionCommand("back to main barber");

        JButton logOut = new JButton("LOG OUT");
        logOut.setActionCommand("log out");

        rightPanel.add(goBack, BorderLayout.NORTH);
        rightPanel.add(logOut, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(midPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        this.add(new LoggedLeftPanel(controller), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);

        standardiseChildren(mainPanel, true, controller);
        goBack.setPreferredSize(new Dimension(130, 40));
        logOut.setPreferredSize(new Dimension(130, 40));
    }

    /**
     * Shows all hours for the date picked by barber and whether or not that
     * slot is set as available.
     *
     * @param controller controller for AvailabilityPage
     */
    public static void setPickedDate(Controller controller) {
        if (allTimesSP != null) {
            allTimesSP.removeAll();
        }
        if (mainTime != null) {
            mainTime.removeAll();
        }

        pickedDate = new JLabel();
        mainTime.add(pickedDate);

        allTimes = new JPanel();
        allTimes.setBackground(Globals.WHITE);
        allTimes.setLayout(new GridLayout(48, 1));

        String pickedDay = date[0].getSelectedItem().toString();
        String pickedMonth = date[1].getSelectedItem().toString();
        pickedDate.setText(pickedDay + "/" + pickedMonth + "/" + date[2].getSelectedItem());
        isAvailable = controller.checkBarberAvailability(controller.getSessionID(), getpickedDate());

        int h = 0;
        boolean isHalf = false;
        String m = ":00";
        String currentTime;
        availableCheckBox = new JCheckBox[48];

        for (int i = 0; i < 48; i++) {
            JPanel singleTime = new JPanel();
            singleTime.setBorder(Globals.border(Globals.DARKBLUE, 1));
            singleTime.setPreferredSize(new Dimension((int) (Globals.WINDOW_WIDTH / 4.5), (int) (Globals.WINDOW_HEIGHT / 10)));
            singleTime.setLayout(new BorderLayout());

            String addZero = "";
            if (h < 10) {
                addZero = "0";
            }
            currentTime = addZero + String.valueOf(h) + m;
            JLabel thisTime = new JLabel(currentTime);
            JPanel availabilityPanel = new JPanel();
            availableCheckBox[i] = new JCheckBox();
            availableCheckBox[i].setName(currentTime);

            if (isAvailable[i]) {
                availableCheckBox[i].setSelected(true);
            }

            if (isHalf) {
                h++;
                m = ":00";
            } else {
                m = ":30";
            }
            isHalf = !isHalf;

            availabilityPanel.add(new JLabel("Available"));
            availabilityPanel.add(availableCheckBox[i]);

            singleTime.add(thisTime, BorderLayout.CENTER);
            singleTime.add(availabilityPanel, BorderLayout.EAST);

            allTimes.add(singleTime);
        }

        allTimesSP = new JScrollPane(allTimes);
        allTimesSP.setPreferredSize(new Dimension((int) (Globals.WINDOW_WIDTH / 3.9), (int) (Globals.WINDOW_HEIGHT / 1.4)));
        mainTime.add(allTimesSP);

        enterAvailability = new JButton("<html>ENTER<br />AVAILABILITY</html>");
        enterAvailability.setActionCommand("enter barber availability");

        mainTime.add(enterAvailability);

        standardiseChildren(mainTime, true, controller);
        standardiseChildren(allTimes, true, null);
    }

    /**
     * @return picked date from the label pickedDate
     */
    public static String getpickedDate() {
        String full = pickedDate.getText();
        String y = full.substring(6);
        String m = full.substring(3, 5);
        String d = full.substring(0, 2);

        return y + "-" + m + "-" + d;
    }

    /**
     * Gets available check box selection.
     *
     * @return HashMap with the keys being the slot time and the value being
     * whether the barber is available for that slot
     */
    public static HashMap<String, Boolean> getAvailableCheckBoxSelection() {
        HashMap<String, Boolean> available = new HashMap<>();

        for (int i = 0; i < availableCheckBox.length; i++) {
            available.put(availableCheckBox[i].getName() + ":00", availableCheckBox[i].isSelected());
        }

        return available;
    }

    public class Editor implements IDateEditor {

        private final JCalendar calendar;
        private SimpleDateFormat format;

        public Editor(JCalendar calendar, JTextField text) {
            this.format = new SimpleDateFormat("dd-MM-yyyy");
            this.calendar = calendar;
        }

        @Override
        public java.util.Date getDate() {
            return this.calendar.getDate();
        }

        @Override
        public void setDate(java.util.Date date) {
            text.setText(String.valueOf(this.format.format(date)));
        }

        @Override
        public void setDateFormatString(String string) {
            this.format = new SimpleDateFormat(string);
        }

        @Override
        public String getDateFormatString() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setSelectableDateRange(java.util.Date date, java.util.Date date1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public java.util.Date getMaxSelectableDate() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public java.util.Date getMinSelectableDate() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setMaxSelectableDate(java.util.Date date) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setMinSelectableDate(java.util.Date date) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public JComponent getUiComponent() {
            return (JComponent) text;
        }

        @Override
        public void setLocale(Locale locale) {
        }

        @Override
        public void setEnabled(boolean bln) {
            this.calendar.setEnabled(bln);
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener pl) {
        }

        @Override
        public void addPropertyChangeListener(String string, PropertyChangeListener pl) {
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener pl) {
        }

        @Override
        public void removePropertyChangeListener(String string, PropertyChangeListener pl) {
        }
    }

    public class docListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            String[] d = text.getText().split("-");
            if (d.length > 2) {
                date[0].setSelectedItem(d[0]);
                date[1].setSelectedItem(d[1]);
                date[2].setSelectedItem(d[2]);
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }

    public class popupListener implements PopupMenuListener {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            calendar.getCalendarButton().doClick();
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
        }
    }

}
