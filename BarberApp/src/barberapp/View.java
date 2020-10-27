/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.jdatepicker.JDatePicker;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class View extends JFrame {
     // window size
    public static int windowWidth = 1200;
    public static int windowHeight = 800;
    
    
    // fonts
    public final Font titleFont = new Font("Tahoma", Font.BOLD, 46);
    public final Font bodyFont = new Font("Tahoma", Font.PLAIN, 26);
    public final Font smallFont = new Font("Tahoma", Font.PLAIN, 16);
    public final Font largeFont = new Font("Tahoma", Font.PLAIN, 36);
    
    // border
    public Border border (Color colour, int width) {
        return BorderFactory.createLineBorder(colour, width);
    }
    
    // dimensions
    public final Dimension regularButtonDimension = new Dimension((int)windowWidth / 6, (int)windowHeight / 12);
    public final Dimension loginPanelDimension = new Dimension((int)2 * windowWidth / 3, (int)windowHeight / 4);
    public final Dimension topPanelDimension = new Dimension(windowWidth, (int)windowHeight / 8);
    public final Dimension bottomPanelDimension = new Dimension(windowWidth, (int)7 * windowHeight / 8);
    public final Dimension leftPanelDimension = new Dimension((int)windowWidth / 4, windowHeight);
    public final Dimension rightPanelDimension = new Dimension((int)3 * windowWidth / 4, windowHeight);
    public final Dimension paddingY1 = new Dimension(windowWidth, (int)windowHeight / 10);
    public final Dimension paddingY3 = new Dimension(windowWidth, (int)windowHeight / 6);
    public final Dimension paddingY4 = new Dimension(windowWidth, (int)windowHeight / 4);
    public final Dimension paddingX1 = new Dimension((int)windowWidth / 10, windowHeight);
    public final Dimension paddingX2 = new Dimension((int)windowWidth / 8, windowHeight);
    public final Dimension paddingX3 = new Dimension((int)windowWidth / 6, windowHeight);
    public final Dimension paddingX4 = new Dimension((int)windowWidth / 4, windowHeight);
    public final Dimension paddingX5 = new Dimension((int)windowWidth / 3, windowHeight);
    
    // colours
    private final Color orange = new Color(255, 128, 0);
    private final Color textFieldColour = new Color(233, 233, 233);
    
    // fields
    private JTextField emailAddress = null;
    private JPasswordField password = null;
    private JPasswordField confirmPass = null;
    private JTextField firstName = null;
    private JTextField lastName = null;
    private JTextField location = null;
    private JTextField address = null;
    private JTextField town = null;
    private JTextField barberName = null;
    private JTextField review = null;
    private JComboBox allLocationsBox = null;
    private int stars = -1;
    private JDatePicker date = null;
    private Time time = null;
    private JLabel error = null;
    
    Controller controller;
      
    /**
     *
     * @param controller
     */
    public View(Controller controller) {
        this.controller = controller; 
        windowSetup();
        
    }
    
    private void windowSetup() {
        //initialise JFrame
        this.setVisible(true);
        this.setSize(windowWidth, windowHeight);
        this.setTitle("Find A Barber");
        
        //initialise main panel
        this.add(new initialPage());
        
        //finalise JFrame
        getContentPane().setFont(bodyFont);
        getContentPane().setForeground(Color.black);
        this.validate();
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // initial page
    public class initialPage extends JPanel {
        public initialPage() {
            this.setLayout(new BorderLayout());
            
            // **top panel**
            JPanel topPanel = new JPanel();
            topPanel.setPreferredSize(topPanelDimension);
            topPanel.setBackground(Color.BLACK);
            
            // page title
            JLabel panelTitle = new JLabel("FIND A BARBER");
            panelTitle.setHorizontalAlignment(SwingConstants.CENTER);
            panelTitle.setForeground(Color.WHITE);
            panelTitle.setFont(titleFont);
            
            topPanel.add(panelTitle);
            
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(bottomPanelDimension);
            mainPanel.setBackground(orange);
            mainPanel.setLayout(new BorderLayout(0, 10));
            
            // blank panel
            JPanel blankPanel = new JPanel();
            blankPanel.setPreferredSize(paddingY3);
            blankPanel.setBackground(orange);
            error = new JLabel();
            blankPanel.add(error);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(orange);
            centerPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setBackground(orange);
            leftBlank.setPreferredSize(paddingX4);
            JPanel rightBlank = new JPanel();
            rightBlank.setBackground(orange);
            rightBlank.setPreferredSize(paddingX4);
            
            // log in panel
            JPanel loginPanel = new JPanel();
            loginPanel.setPreferredSize(loginPanelDimension);
            loginPanel.setBorder(border(Color.BLACK, 3));
            loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
            
            // emailAddress input panel
            JPanel user = new JPanel();
            user.setBackground(Color.white);
            JLabel userLabel = new JLabel("E-mail address: ");
            emailAddress = new JTextField(20);
            
            // add label and text field for emailAddress
            user.add(userLabel);
            user.add(emailAddress);
            
            // password input panel
            JPanel pass = new JPanel();
            pass.setBackground(Color.white);
            JLabel passLabel = new JLabel("Password: ");
            password = new JPasswordField(20);
            password.setEchoChar('•');
            
            // add label and text field for password
            pass.add(passLabel);
            pass.add(password);
            
            // log in button panel
            JPanel logPanel = new JPanel();
            logPanel.setBackground(Color.white);
            JButton login = new JButton("LOG IN");
            login.setActionCommand("log in");
            password.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                        login.doClick();
                    }
                }
            });
            emailAddress.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                        login.doClick();
                    }
                }
            });
            
            logPanel.add(login);
            
            // add emailAddress, password and log in button to login panel
            loginPanel.add(user);
            loginPanel.add(pass);
            loginPanel.add(logPanel);
            
            
            // add padding and log in panel to center panel
            centerPanel.add(leftBlank, BorderLayout.LINE_START);
            centerPanel.add(loginPanel, BorderLayout.CENTER);
            centerPanel.add(rightBlank, BorderLayout.LINE_END);
            
            // create account button panel
            JPanel createAccountPanel = new JPanel();
            createAccountPanel.setBackground(orange);
            createAccountPanel.setPreferredSize(paddingY4);
            
            // line break code retrieved from https://stackoverflow.com/questions/13503280/new-line-n-is-not-working-in-jbutton-settextfnord-nfoo/13503281
            JButton createAccount = new JButton("<html>   CREATE<br/>ACCOUNT   </html>");
            createAccount.setActionCommand("create new account");
            
            createAccountPanel.add(createAccount);
            
                       
            // add panels to main panel
            mainPanel.add(blankPanel, BorderLayout.PAGE_START);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(createAccountPanel, BorderLayout.SOUTH);
            standardiseChildren(mainPanel, true, true, true);
            // adding panels to initial page
            this.add(topPanel, BorderLayout.PAGE_START);
            this.add(mainPanel, BorderLayout.CENTER);
            emailAddress.requestFocusInWindow();
            
        }
        
    }
    
    // left panel on create account pages
    public class accountCreateLeftPanel extends JPanel {
        public accountCreateLeftPanel() {
            this.setPreferredSize(leftPanelDimension);
            this.setBackground(Color.BLACK);
            this.setLayout(new BorderLayout());
            
            // **top panel**
            JPanel logoPanel = new JPanel();
            logoPanel.setPreferredSize(new Dimension((int)(windowWidth / 4), (int)(windowHeight / 3)));
            logoPanel.setBackground(Color.black);
            
            // white panel
            JPanel whitePanel = new JPanel();
            whitePanel.setPreferredSize(new Dimension((int)(windowWidth / 5), (int)(windowHeight / 5)));
            whitePanel.setBackground(Color.white);
            whitePanel.setLayout(new BorderLayout());
            
            // labels
            JLabel find = new JLabel("FIND");
            find.setForeground(Color.black);
            find.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel a = new JLabel("A");
            find.setForeground(Color.black);
            a.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel barber = new JLabel("BARBER");
            find.setForeground(Color.black);
            barber.setHorizontalAlignment(SwingConstants.CENTER);
            
            // add labels to white panel
            whitePanel.add(find, BorderLayout.NORTH);
            whitePanel.add(a, BorderLayout.CENTER);
            whitePanel.add(barber, BorderLayout.SOUTH);
            
            // add white panel to top panel
            logoPanel.add(whitePanel);
            
            // **bottom panel**
            JPanel bottomPanel = new JPanel();
            bottomPanel.setBackground(Color.black);
            bottomPanel.setPreferredSize(new Dimension((int)(windowWidth / 4), (int)(2 * windowHeight / 3)));
            bottomPanel.setLayout(new BorderLayout());
            
            JPanel cacc = new JPanel();
            cacc.setBackground(Color.black);
            cacc.setPreferredSize(new Dimension((int)windowWidth / 5, (int)windowHeight / 3));
            
            JLabel create = new JLabel("CREATE");
            create.setForeground(Color.WHITE);
            JLabel account = new JLabel("ACCOUNT");
            account.setForeground(Color.WHITE);
            
            cacc.add(create);
            cacc.add(account);
            
            bottomPanel.add(cacc, BorderLayout.SOUTH);
            
            this.add(logoPanel, BorderLayout.NORTH);
            this.add(bottomPanel, BorderLayout.SOUTH);
            
            create.setForeground(Color.white);
            
        }
    }
    
    public class createChoice extends JPanel {
        public createChoice () {
            this.setLayout(new BorderLayout());
            
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(rightPanelDimension);
            mainPanel.setBackground(orange);
            mainPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setPreferredSize(paddingX2);
            leftBlank.setBackground(orange);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(orange);
            centerPanel.setLayout(new BorderLayout());
            
            JPanel p1 = new JPanel();
            p1.setPreferredSize(paddingY3);
            p1.setBackground(orange);
            
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setBackground(orange);
            buttonsPanel.setLayout(new BorderLayout());
            
            JButton createCustomer = new JButton("I'M LOOKING FOR A BARBER");
            createCustomer.setActionCommand("go to create customer");
            createCustomer.setPreferredSize(new Dimension((int)windowWidth / 5, (int)windowHeight / 8));
            
            JPanel p3 = new JPanel();
            p3.setBackground(orange);
            
            JButton createBarber = new JButton("I'M A BARBER");
            createBarber.setActionCommand("go to create barber");
            createBarber.setPreferredSize(new Dimension((int)windowWidth / 5, (int)windowHeight / 8));
            
            buttonsPanel.add(createCustomer, BorderLayout.NORTH);
            buttonsPanel.add(p3, BorderLayout.CENTER);
            buttonsPanel.add(createBarber, BorderLayout.SOUTH);
            
            JPanel p2 = new JPanel();
            p2.setPreferredSize(paddingY3);
            p2.setBackground(orange);
            
            centerPanel.add(p1, BorderLayout.NORTH);
            centerPanel.add(buttonsPanel, BorderLayout.CENTER);
            centerPanel.add(p2, BorderLayout.SOUTH);
            
            // padding/back button
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(paddingX2);
            rightPanel.setBackground(orange);
            
            JButton back = new JButton("BACK");
            back.setActionCommand("back to initial page");
            
            rightPanel.add(back);
            
            
            mainPanel.add(leftBlank, BorderLayout.WEST);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            
            this.add(new accountCreateLeftPanel(), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            standardiseChildren(this, true, false, true);
        }
    }
    
    public class createBarber extends JPanel {
        public createBarber() {
            this.setLayout(new BorderLayout());
            
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(rightPanelDimension);
            mainPanel.setBackground(orange);
            mainPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setPreferredSize(paddingX2);
            leftBlank.setBackground(orange);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(orange);
            centerPanel.setLayout(new BorderLayout());
            
            error = new JLabel("");
            
            JPanel p1 = new JPanel();
            p1.setLayout(new BorderLayout());
            p1.setPreferredSize(paddingY3);
            p1.setBackground(orange);
            p1.setForeground(Color.WHITE);
            p1.add(new JLabel("I'M A BARBER"), BorderLayout.PAGE_START);
            p1.add(error, BorderLayout.CENTER);
            
            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(orange);
            infoPanel.setLayout(new GridLayout(18,2));
            
            firstName = new JTextField(20);
            lastName = new JTextField(20);
            emailAddress = new JTextField(20);
            address = new JTextField(20);
            password = new JPasswordField(20);
            password.setEchoChar('•');
            location = new JTextField(20);
            town = new JTextField(20);
            confirmPass = new JPasswordField(20);
            confirmPass.setEchoChar('•');
            JButton create = new JButton("CREATE ACCOUNT");
            create.setActionCommand("create barber");
            JPanel bPadding1 = new JPanel();
            bPadding1.setBackground(orange);
            JPanel bPadding2 = new JPanel();
            bPadding2.setBackground(orange);
            JPanel bPadding3 = new JPanel();
            bPadding3.setBackground(orange);
            
            infoPanel.add(new JLabel("Email: "));
            infoPanel.add(emailAddress);
            infoPanel.add(new JLabel("Password: "));
            infoPanel.add(password);
            infoPanel.add(new JLabel("Confirm Password: "));
            infoPanel.add(confirmPass);
            infoPanel.add(new JLabel("First Name: "));
            infoPanel.add(firstName);
            infoPanel.add(new JLabel("Last Name: "));
            infoPanel.add(lastName);
            infoPanel.add(new JLabel("Address: "));
            infoPanel.add(address);
            infoPanel.add(new JLabel("Location (D1, D2...): "));
            infoPanel.add(location);
            infoPanel.add(new JLabel("Town: "));
            infoPanel.add(town);
            infoPanel.add(bPadding1);
            infoPanel.add(bPadding2);
            infoPanel.add(bPadding3);
            infoPanel.add(create);
            
            
            JPanel p2 = new JPanel();
            p2.setPreferredSize(paddingY3);
            p2.setBackground(orange);
            
            centerPanel.add(p1, BorderLayout.NORTH);
            centerPanel.add(infoPanel, BorderLayout.CENTER);
            centerPanel.add(p2, BorderLayout.SOUTH);
            
            // padding/back button
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(paddingX2);
            rightPanel.setBackground(orange);
            
            JButton back = new JButton("BACK");
            back.setActionCommand("create new account");
            
            rightPanel.add(back);
            
            
            mainPanel.add(leftBlank, BorderLayout.WEST);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            
            this.add(new accountCreateLeftPanel(), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            standardiseChildren(infoPanel, true, true, true);
            standardiseChildren(this, true, false, true);
        }
    }
    
    public class createCustomer extends JPanel {
        public createCustomer() {
            this.setLayout(new BorderLayout());
            
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(rightPanelDimension);
            mainPanel.setBackground(orange);
            mainPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setPreferredSize(paddingX2);
            leftBlank.setBackground(orange);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(orange);
            centerPanel.setLayout(new BorderLayout());
            
            error = new JLabel("");
            
            JPanel p1 = new JPanel();
            p1.setLayout(new BorderLayout());
            p1.setPreferredSize(paddingY3);
            p1.setBackground(orange);
            p1.setForeground(Color.WHITE);
            p1.add(new JLabel("I'M LOOKING FOR A BARBER"), BorderLayout.PAGE_START);
            p1.add(error, BorderLayout.CENTER);
            
            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(orange);
            infoPanel.setLayout(new GridLayout(18,2));
            
            firstName = new JTextField(20);
            lastName = new JTextField(20);
            emailAddress = new JTextField(20);
            address = new JTextField(20);
            password = new JPasswordField(20);
            password.setEchoChar('•');
            location = new JTextField(20);
            confirmPass = new JPasswordField(20);
            confirmPass.setEchoChar('•');
            JButton create = new JButton("CREATE ACCOUNT");
            create.setActionCommand("create customer");
            JPanel bPadding1 = new JPanel();
            bPadding1.setBackground(orange);
            JPanel bPadding2 = new JPanel();
            bPadding2.setBackground(orange);
            JPanel bPadding3 = new JPanel();
            bPadding3.setBackground(orange);
            
            infoPanel.add(new JLabel("Email: "));
            infoPanel.add(emailAddress);
            infoPanel.add(new JLabel("Password: "));
            infoPanel.add(password);
            infoPanel.add(new JLabel("Confirm Password: "));
            infoPanel.add(confirmPass);
            infoPanel.add(new JLabel("First Name: "));
            infoPanel.add(firstName);
            infoPanel.add(new JLabel("Last Name: "));
            infoPanel.add(lastName);
            infoPanel.add(bPadding1);
            infoPanel.add(bPadding2);
            infoPanel.add(bPadding3);
            infoPanel.add(create);
            
            
            JPanel p2 = new JPanel();
            p2.setPreferredSize(paddingY3);
            p2.setBackground(orange);
            
            centerPanel.add(p1, BorderLayout.NORTH);
            centerPanel.add(infoPanel, BorderLayout.CENTER);
            centerPanel.add(p2, BorderLayout.SOUTH);
            
            // padding/back button
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(paddingX2);
            rightPanel.setBackground(orange);
            
            JButton back = new JButton("BACK");
            back.setActionCommand("create new account");
            
            rightPanel.add(back);
            
            
            mainPanel.add(leftBlank, BorderLayout.WEST);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            
            this.add(new accountCreateLeftPanel(), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            standardiseChildren(infoPanel, true, true, true);
            standardiseChildren(this, true, false, true);
        }
    }
    
    public class loggedLeftPanel extends JPanel {
        public loggedLeftPanel() {
            this.setPreferredSize(leftPanelDimension);
            this.setBackground(Color.BLACK);
            this.setLayout(new BorderLayout());
            
            // **top panel**
            JPanel logoPanel = new JPanel();
            logoPanel.setPreferredSize(new Dimension((int)(windowWidth / 4), (int)(windowHeight / 3)));
            logoPanel.setBackground(Color.black);
            
            // white panel
            JPanel whitePanel = new JPanel();
            whitePanel.setPreferredSize(new Dimension((int)(windowWidth / 5), (int)(windowHeight / 5)));
            whitePanel.setBackground(Color.white);
            whitePanel.setLayout(new BorderLayout());
            
            // labels
            JLabel find = new JLabel("FIND");
            find.setForeground(Color.black);
            find.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel a = new JLabel("A");
            find.setForeground(Color.black);
            a.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel barber = new JLabel("BARBER");
            find.setForeground(Color.black);
            barber.setHorizontalAlignment(SwingConstants.CENTER);
            
            // add labels to white panel
            whitePanel.add(find, BorderLayout.NORTH);
            whitePanel.add(a, BorderLayout.CENTER);
            whitePanel.add(barber, BorderLayout.SOUTH);
            
            // add white panel to top panel
            logoPanel.add(whitePanel);
            
            // **bottom panel**
            JPanel bottomPanel = new JPanel();
            bottomPanel.setBackground(Color.black);
            bottomPanel.setPreferredSize(new Dimension((int)(windowWidth / 4), (int)(2 * windowHeight / 3)));
            bottomPanel.setLayout(new BorderLayout());
            
            JPanel cacc = new JPanel();
            cacc.setBackground(Color.black);
            cacc.setPreferredSize(new Dimension((int)windowWidth / 5, (int)windowHeight / 3));
            
            JLabel create = new JLabel("WELCOME");
            create.setForeground(Color.WHITE);
            JLabel account = new JLabel(controller.getSessionFirstName().toUpperCase());
            account.setForeground(Color.WHITE);
            
            cacc.add(create);
            cacc.add(account);
            
            bottomPanel.add(cacc, BorderLayout.SOUTH);
            
            this.add(logoPanel, BorderLayout.NORTH);
            this.add(bottomPanel, BorderLayout.SOUTH);
            
            create.setForeground(Color.white);
        }
    }
    
    public class customerMain extends JPanel {
        public customerMain() {
            this.setLayout(new BorderLayout());
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(rightPanelDimension);
            mainPanel.setBackground(orange);
            mainPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setPreferredSize(paddingX1);
            leftBlank.setBackground(orange);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(orange);
            centerPanel.setLayout(new BorderLayout());
            
            error = new JLabel();
            
            JPanel p1 = new JPanel();
            p1.setLayout(new BorderLayout());
            p1.setPreferredSize(paddingY1);
            p1.setBackground(orange);
            p1.setForeground(Color.WHITE);
            p1.add(error, BorderLayout.PAGE_START);
            
            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(orange);
            infoPanel.setLayout(new BorderLayout());
            
            // *** search panel ***
            JPanel searchPanel = new JPanel();
            searchPanel.setPreferredSize(loginPanelDimension);
            searchPanel.setLayout(new BorderLayout());
            searchPanel.setBorder(border(Color.BLACK, 2));
            
            JPanel searchTopPanel = new JPanel();
            searchTopPanel.add(new JLabel("FIND A BARBER"));
            
            JPanel searchMainPanel = new JPanel();
            searchMainPanel.setLayout(new BorderLayout());
            
            JPanel searchName = new JPanel();
            searchName.setLayout(new BorderLayout());
            
            JPanel topSearchName = new JPanel();
            barberName = new JTextField(8);
            topSearchName.add(new JLabel("By name:"));
            topSearchName.add(barberName);
            
            JPanel centerSearchName = new JPanel();
            JButton searchNameButton = new JButton("SEARCH");
            searchNameButton.setActionCommand("search barber name");
            
            centerSearchName.add(searchNameButton);
            
            searchName.add(topSearchName, BorderLayout.CENTER);
            searchName.add(centerSearchName, BorderLayout.SOUTH);
            
            JPanel searchLocation = new JPanel();
            searchLocation.setLayout(new BorderLayout());
            
            JPanel topSearchLocation = new JPanel();
            allLocationsBox = new JComboBox(controller.getLocations());
            
            topSearchLocation.add(new JLabel("By location:"));
            topSearchLocation.add(allLocationsBox);
            
            JPanel centerSearchLocation = new JPanel();
            JButton searchLocationButton = new JButton("SEARCH");
            searchLocationButton.setActionCommand("search barber location");
            
            centerSearchLocation.add(searchLocationButton);
            
            searchLocation.add(topSearchLocation, BorderLayout.CENTER);
            searchLocation.add(centerSearchLocation, BorderLayout.SOUTH);
            
            searchMainPanel.add(searchName, BorderLayout.WEST);
            searchMainPanel.add(searchLocation, BorderLayout.EAST);
            
            searchPanel.add(searchTopPanel, BorderLayout.PAGE_START);
            searchPanel.add(searchMainPanel, BorderLayout.CENTER);
            
            // *** next booking panel ***
            JPanel nextBookingPanel = new JPanel();
            nextBookingPanel.setPreferredSize(loginPanelDimension);
            nextBookingPanel.setBorder(border(Color.BLACK, 2));
            nextBookingPanel.setLayout(new BorderLayout());
            
            JPanel leftNextBooking = new JPanel();
            leftNextBooking.setLayout(new BorderLayout());
            
            JPanel topNext = new JPanel();
            topNext.add(new JLabel("NEXT BOOKING:"));
            
            JPanel centerNext = new JPanel();
            centerNext.setPreferredSize(paddingX5);
            centerNext.setLayout(new BorderLayout());
            try {
                HashMap<String, String> nextBooking = controller.getNextCustomerBooking();
                centerNext.add(new JLabel("Date: " + nextBooking.get("date") + " | Barber: " + nextBooking.get("name")), BorderLayout.NORTH);
                centerNext.add(new JLabel("Time: " + nextBooking.get("time")), BorderLayout.EAST);
                centerNext.add(new JLabel("Address: " + nextBooking.get("town")), BorderLayout.WEST);
                centerNext.add(new JLabel(nextBooking.get("address")), BorderLayout.SOUTH);
            } catch (Exception e) {
                centerNext.add(new JLabel("NO UPCOMING BOOKINGS FOUND"));
            }
            
            leftNextBooking.add(topNext, BorderLayout.NORTH);
            leftNextBooking.add(centerNext, BorderLayout.CENTER);
            
            JPanel rightNextBooking = new JPanel();
            rightNextBooking.setPreferredSize(paddingX1);
            rightNextBooking.setLayout(new BorderLayout());
            
            JButton viewBookings = new JButton("<html>VIEW<br /> BOOKINGS</html>");
            viewBookings.setActionCommand("view customer bookings");
            
            JButton cancelBooking = new JButton("<html>CANCEL<br />BOOKING</html>");
            cancelBooking.setActionCommand("customer booking cancel");
            
            rightNextBooking.add(viewBookings, BorderLayout.NORTH);
            rightNextBooking.add(cancelBooking, BorderLayout.SOUTH);
            
            nextBookingPanel.add(leftNextBooking, BorderLayout.WEST);
            nextBookingPanel.add(rightNextBooking, BorderLayout.EAST);
            
            infoPanel.add(searchPanel, BorderLayout.NORTH);
            infoPanel.add(nextBookingPanel, BorderLayout.SOUTH);
            
            
            JPanel p2 = new JPanel();
            p2.setPreferredSize(paddingY3);
            p2.setBackground(orange);
            
            JButton review = new JButton("<html>REVIEW AN<br /> APPOINTMENT</html>");
            review.setActionCommand("go to review");
            review.setPreferredSize(regularButtonDimension);
            p2.add(review);
            
            centerPanel.add(p1, BorderLayout.NORTH);
            centerPanel.add(infoPanel, BorderLayout.CENTER);
            centerPanel.add(p2, BorderLayout.SOUTH);
            
            // padding/back button
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(paddingX3);
            rightPanel.setBackground(orange);
            rightPanel.setLayout(new BorderLayout());
            
            JButton logout = new JButton("LOG OUT");
            logout.setActionCommand("log out");
            logout.setPreferredSize(regularButtonDimension);
            
            
            rightPanel.add(logout, BorderLayout.NORTH);
            
            mainPanel.add(leftBlank, BorderLayout.WEST);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            
            this.add(new loggedLeftPanel(), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            
            standardiseChildren(this, true, false, true);
            viewBookings.setFont(smallFont);
            cancelBooking.setFont(smallFont);
        }
    }
    
    public class barberMain extends JPanel {
        public barberMain() {
            this.setLayout(new BorderLayout());
            
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(rightPanelDimension);
            mainPanel.setBackground(orange);
            mainPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setPreferredSize(paddingX1);
            leftBlank.setBackground(orange);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(orange);
            centerPanel.setLayout(new BorderLayout());
            
            error = new JLabel();
            
            JPanel p1 = new JPanel();
            p1.setLayout(new BorderLayout());
            p1.setPreferredSize(paddingY1);
            p1.setBackground(orange);
            p1.setForeground(Color.WHITE);
            p1.add(error, BorderLayout.PAGE_START);
            
            // *** CENTRAL INFO PANEL ***
            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(Color.blue);
            infoPanel.setLayout(new BorderLayout());
            infoPanel.setBorder(border(Color.BLACK, 2));
            
            JPanel topLabelPanel = new JPanel();
            topLabelPanel.setLayout(new BorderLayout());
            
            JButton seeAllBookings = new JButton("ALL BOOKINGS");
            seeAllBookings.setActionCommand("view barber bookings");
            
            topLabelPanel.add(new JLabel("MY BOOKINGS:"), BorderLayout.WEST);
            topLabelPanel.add(seeAllBookings, BorderLayout.EAST);
            topLabelPanel.setBackground(Color.WHITE);
            
            JPanel multipleBookingsPanel = new JPanel();
            multipleBookingsPanel.setBackground(Color.WHITE);
            
            JPanel padding1 = new JPanel();
            padding1.setPreferredSize(new Dimension(250, 20));
            padding1.setBackground(Color.WHITE);
            
            multipleBookingsPanel.add(padding1);
            try {
                ArrayList<String[]> upcoming = controller.getBarberUpcomingBookings();
                multipleBookingsPanel.setLayout(new GridLayout(upcoming.size() + 2,1));
                
                for (int i = 0; i < upcoming.size(); i++) {
                    JPanel booking = new JPanel();
                    booking.setPreferredSize(new Dimension(250, 100));
                    booking.setLayout(new BorderLayout());
                    booking.setBorder(border(Color.BLACK, 1));
                    
                    JPanel left = new JPanel();
                    left.setLayout(new BorderLayout());
                    left.setBackground(Color.WHITE);
                    
                    JPanel leftTop = new JPanel();
                    leftTop.add(new JLabel("UPCOMING BOOKING:"));
                    leftTop.setBackground(Color.WHITE);
                    
                    JPanel leftBottom = new JPanel();
                    leftBottom.setLayout(new BorderLayout());
                    leftBottom.setBackground(Color.WHITE);
                    
                    JLabel cust = new JLabel("Customer: " + upcoming.get(i)[3]);
                    JLabel dt = new JLabel("Date: " + upcoming.get(i)[0]);

                    JPanel hrPanel = new JPanel();
                    hrPanel.setBackground(Color.WHITE);
                    JLabel hr = new JLabel("Time: " + upcoming.get(i)[1]);
                    hr.setBorder(border(Color.black, 1));
                    hr.setPreferredSize(new Dimension(70, 20));
                    hr.setHorizontalTextPosition(SwingConstants.CENTER);
                    hrPanel.add(hr, BorderLayout.CENTER);
                    
                    JLabel num = new JLabel("Booking ID: " + upcoming.get(i)[2]);
                    
                    leftBottom.add(cust, BorderLayout.NORTH);
                    leftBottom.add(dt, BorderLayout.WEST);
                    leftBottom.add(hrPanel, BorderLayout.CENTER);
                    leftBottom.add(num, BorderLayout.SOUTH);
                    
                    
                    left.add(leftTop, BorderLayout.NORTH);
                    left.add(leftBottom, BorderLayout.SOUTH);
                    
                    JPanel right = new JPanel();
                    right.setBackground(Color.WHITE);
                    right.setLayout(new BorderLayout());
                    JButton cancel = new JButton("CANCEL");
                    cancel.addActionListener(controller);
                    cancel.setFont(smallFont);
                    cancel.setBackground(textFieldColour);
                    cancel.setActionCommand("cancel booking " + upcoming.get(i)[2]);
                    cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    right.add(cancel, BorderLayout.SOUTH);
                    
                    booking.add(left, BorderLayout.CENTER);
                    booking.add(right, BorderLayout.EAST);
                    
                    multipleBookingsPanel.add(booking);
                    
                }
                
            } catch (Exception e) {
                System.out.println(e);
                System.out.println(e.getMessage());
                multipleBookingsPanel.add(new JLabel("NO BOOKINGS FOUND"));
            }
            
            JScrollPane multiplePane = new JScrollPane(multipleBookingsPanel);
            
            infoPanel.add(topLabelPanel, BorderLayout.PAGE_START);
            infoPanel.add(multiplePane, BorderLayout.CENTER);
            
            
            JPanel p2 = new JPanel();
            p2.setPreferredSize(paddingY3);
            p2.setBackground(orange);
            
            centerPanel.add(p1, BorderLayout.NORTH);
            centerPanel.add(infoPanel, BorderLayout.CENTER);
            centerPanel.add(p2, BorderLayout.SOUTH);
            
            // padding/back button
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(paddingX3);
            rightPanel.setBackground(orange);
            rightPanel.setLayout(new BorderLayout());
            
            JButton logout = new JButton("LOG OUT");
            logout.setActionCommand("log out");
            logout.setPreferredSize(regularButtonDimension);
            
            
            rightPanel.add(logout, BorderLayout.NORTH);
            
            mainPanel.add(leftBlank, BorderLayout.WEST);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            
            this.add(new loggedLeftPanel(), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            
            standardiseChildren(this, true, false, true);
        }
    }
    
    /**
     * sets standard parameters to components 
     * concept retrieved from https://stackoverflow.com/questions/27774581/change-background-color-of-components-with-reference-to-color-variable-java
    */
    private void standardiseChildren(JPanel panel, boolean font, boolean foreground, boolean background) {
        Component[] component = panel.getComponents();
        for(Component c : component) {
            if (c instanceof JButton) {
                if (font) {
                    c.setFont(bodyFont);
                }
                if (background) {
                    c.setBackground(textFieldColour);
                    ((JButton) c).setBorder(border(Color.black, 2));
                }
                if (foreground) {
                    c.setPreferredSize(regularButtonDimension);
                    c.setForeground(Color.black);
                }
                ((JButton) c).addActionListener(controller);
                ((JButton) c).setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else if (c instanceof JPanel) {
                standardiseChildren((JPanel)c, font, foreground, background);
            } else if (c instanceof JLabel) {
                if (font) {
                    c.setFont(bodyFont);
                }
                if (foreground) {
                    c.setForeground(Color.black);
                }
            } else if (c instanceof JTextField) {
                if (background) {
                    c.setBackground(textFieldColour);
                    ((JTextField) c).setBorder(border(Color.black, 1));
                }
                if (font) {
                    c.setFont(bodyFont);
                }
            }
        }
    }
    
    public JComboBox getAllLocationsBox() {
        return allLocationsBox;
    }
    
    public String getEmailAddress() {
        return emailAddress.getText();
    } 
    
    public String getPass() {
        return password.getText();
    }
    
    public String getConfirmPass() {
        return confirmPass.getText();
    }
    
    public String getFirstName() {
        return firstName.getText();
    }
    
    public String getLastName() {
        return lastName.getText();
    }
    
    public String getSetLocation() {
        return location.getText();
    }
    
    public String getAddress() {
        return address.getText();
    }
    
    public String getTown() {
        return town.getText();
    }
    
    public String getBarberName() {
        return barberName.getText();
    }
    
    public void setError(String e) {
        error.setText(e);
    }
    
}
