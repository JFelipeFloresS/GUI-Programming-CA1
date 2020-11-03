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
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

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
    public final Dimension paddingY2 = new Dimension(windowWidth, (int)windowHeight / 8);
    public final Dimension paddingY3 = new Dimension(windowWidth, (int)windowHeight / 6);
    public final Dimension paddingY4 = new Dimension(windowWidth, (int)windowHeight / 4);
    public final Dimension paddingY5 = new Dimension(windowWidth, (int)windowHeight / 3);
    public final Dimension paddingX1 = new Dimension((int)windowWidth / 10, windowHeight);
    public final Dimension paddingX2 = new Dimension((int)windowWidth / 8, windowHeight);
    public final Dimension paddingX3 = new Dimension((int)windowWidth / 6, windowHeight);
    public final Dimension paddingX4 = new Dimension((int)windowWidth / 4, windowHeight);
    public final Dimension paddingX5 = new Dimension((int)windowWidth / 3, windowHeight);
    
    // colours
    private static final Color WHITE = new Color(250, 250, 250);
    private static final Color BLUE = new Color(133, 133, 233);
    private static final Color DARKBLUE = new Color(85, 85, 185);
    private static final Color TEXTFIELDCOLOUR = new Color(233, 233, 233);
    private static final Color DEFAULTCOLOUR = new Color(238, 238, 238);
    
    // fields
    private JTextField emailAddress = null;
    private JPasswordField password = null;
    private JPasswordField confirmPass = null;
    private JTextField firstName = null;
    private JTextField lastName = null;
    private JTextField location = null;
    private JTextField phoneNumber = null;
    private JTextField address = null;
    private JTextField town = null;
    private JTextField barberName = null;
    private JTextArea review = null;
    private JButton star1 = null;
    private JButton star2 = null;
    private JButton star3 = null;
    private JButton star4 = null;
    private JButton star5 = null;
    private JComboBox allLocationsBox = null;
    private JCheckBox[] availableCheckBox = null;
    private int stars = -1;
    private boolean[] isAvailable = null;
    private JComboBox[] date = null;
    private JLabel pickedDate = null;
    private JButton selectDate = null;
    private JScrollPane allTimesSP = null;
    private JPanel allTimes = null;
    private JButton enterAvailability = null;
    private JPanel mainTime = null;
    private JPanel leftFindABarberPanel = null;
    private JPanel rightFindABarberPanel = null;
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
        this.setResizable(false);
        
        //initialise main panel
        this.add(new submitReview(2));
        
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
            topPanel.setBackground(BLUE);
            
            // page title
            JLabel panelTitle = new JLabel("FIND A BARBER");
            panelTitle.setHorizontalAlignment(SwingConstants.CENTER);
            panelTitle.setForeground(Color.WHITE);
            panelTitle.setFont(titleFont);
            
            topPanel.add(panelTitle);
            
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(bottomPanelDimension);
            mainPanel.setBackground(WHITE);
            mainPanel.setLayout(new BorderLayout(0, 10));
            
            // blank panel
            JPanel blankPanel = new JPanel();
            blankPanel.setPreferredSize(paddingY3);
            blankPanel.setBackground(WHITE);
            error = new JLabel();
            blankPanel.add(error);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(WHITE);
            centerPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setBackground(WHITE);
            leftBlank.setPreferredSize(paddingX4);
            JPanel rightBlank = new JPanel();
            rightBlank.setBackground(WHITE);
            rightBlank.setPreferredSize(paddingX4);
            
            // log in panel
            JPanel loginPanel = new JPanel();
            loginPanel.setPreferredSize(loginPanelDimension);
            loginPanel.setBorder(border(Color.BLACK, 4));
            loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
            
            // emailAddress input panel
            JPanel user = new JPanel();
            user.setLayout(new GridLayout(2, 1));
            user.setPreferredSize(new Dimension(500, 80));
            user.setMaximumSize(new Dimension(500, 80));
            user.setBackground(Color.white);
            JLabel userLabel = new JLabel("E-mail address: ");
            emailAddress = new JTextField(20);
            
            // add label and text field for emailAddress
            user.add(userLabel);
            user.add(emailAddress);
            
            // password input panel
            JPanel pass = new JPanel();
            pass.setLayout(new GridLayout(2, 1));
            pass.setPreferredSize(new Dimension(500, 80));
            pass.setMaximumSize(new Dimension(500, 80));
            pass.setBackground(Color.white);
            JLabel passLabel = new JLabel("Password: ");
            password = new JPasswordField(20);
            password.setEchoChar('•');
            
            // add label and text field for password
            pass.add(passLabel);
            pass.add(password);
            
            JPanel pad = new JPanel();
            pad.setPreferredSize(new Dimension(50, 30));
            pad.setMaximumSize(new Dimension(50, 30));
            
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
            loginPanel.add(pad);
            loginPanel.add(logPanel);
            
            
            // add padding and log in panel to center panel
            centerPanel.add(leftBlank, BorderLayout.LINE_START);
            centerPanel.add(loginPanel, BorderLayout.CENTER);
            centerPanel.add(rightBlank, BorderLayout.LINE_END);
            
            // create account button panel
            JPanel createAccountPanel = new JPanel();
            createAccountPanel.setBackground(WHITE);
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
            this.setBackground(BLUE);
            this.setLayout(new BorderLayout());
            
            // **top panel**
            JPanel logoPanel = new JPanel();
            logoPanel.setPreferredSize(new Dimension((int)(windowWidth / 4), (int)(windowHeight / 3)));
            logoPanel.setBackground(BLUE);
            
            // WHITE panel
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
            
            // add labels to WHITE panel
            whitePanel.add(find, BorderLayout.NORTH);
            whitePanel.add(a, BorderLayout.CENTER);
            whitePanel.add(barber, BorderLayout.SOUTH);
            
            // add WHITE panel to top panel
            logoPanel.add(whitePanel);
            
            // **bottom panel**
            JPanel bottomPanel = new JPanel();
            bottomPanel.setBackground(BLUE);
            bottomPanel.setPreferredSize(new Dimension((int)(windowWidth / 4), (int)(2 * windowHeight / 3)));
            bottomPanel.setLayout(new BorderLayout());
            
            JPanel cacc = new JPanel();
            cacc.setBackground(BLUE);
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
            mainPanel.setBackground(WHITE);
            mainPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setPreferredSize(paddingX2);
            leftBlank.setBackground(WHITE);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(WHITE);
            centerPanel.setLayout(new BorderLayout());
            
            JPanel p1 = new JPanel();
            p1.setPreferredSize(paddingY3);
            p1.setBackground(WHITE);
            
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setBackground(WHITE);
            buttonsPanel.setLayout(new BorderLayout());
            
            JButton createCustomer = new JButton("I'M LOOKING FOR A BARBER");
            createCustomer.setActionCommand("go to create customer");
            createCustomer.setPreferredSize(new Dimension((int)windowWidth / 5, (int)windowHeight / 8));
            
            JPanel p3 = new JPanel();
            p3.setBackground(WHITE);
            
            JButton createBarber = new JButton("I'M A BARBER");
            createBarber.setActionCommand("go to create barber");
            createBarber.setPreferredSize(new Dimension((int)windowWidth / 5, (int)windowHeight / 8));
            
            buttonsPanel.add(createCustomer, BorderLayout.NORTH);
            buttonsPanel.add(p3, BorderLayout.CENTER);
            buttonsPanel.add(createBarber, BorderLayout.SOUTH);
            
            JPanel p2 = new JPanel();
            p2.setPreferredSize(paddingY3);
            p2.setBackground(WHITE);
            
            centerPanel.add(p1, BorderLayout.NORTH);
            centerPanel.add(buttonsPanel, BorderLayout.CENTER);
            centerPanel.add(p2, BorderLayout.SOUTH);
            
            // padding/back button
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(paddingX2);
            rightPanel.setBackground(WHITE);
            
            JButton back = new JButton("BACK");
            back.setActionCommand("back to initial page");
            
            rightPanel.add(back);
            
            
            mainPanel.add(leftBlank, BorderLayout.WEST);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            
            this.add(new accountCreateLeftPanel(), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            standardiseChildren(mainPanel, true, false, true);
        }
    }
    
    public class createBarber extends JPanel {
        public createBarber() {
            this.setLayout(new BorderLayout());
            
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(rightPanelDimension);
            mainPanel.setBackground(WHITE);
            mainPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setPreferredSize(paddingX2);
            leftBlank.setBackground(WHITE);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(WHITE);
            centerPanel.setLayout(new BorderLayout());
            
            error = new JLabel("");
            
            JPanel p1 = new JPanel();
            p1.setLayout(new BorderLayout());
            p1.setPreferredSize(paddingY2);
            p1.setBackground(WHITE);
            p1.setForeground(Color.WHITE);
            p1.add(new JLabel("I'M A BARBER"), BorderLayout.PAGE_START);
            p1.add(error, BorderLayout.CENTER);
            
            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(WHITE);
            infoPanel.setLayout(new GridLayout(15,2, 0, 20));
            
            firstName = new JTextField(20);
            lastName = new JTextField(20);
            emailAddress = new JTextField(20);
            phoneNumber = new JTextField(20);
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
            bPadding1.setBackground(WHITE);
            
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
            infoPanel.add(new JLabel("Phone Number: "));
            infoPanel.add(phoneNumber);
            infoPanel.add(new JLabel("Address: "));
            infoPanel.add(address);
            infoPanel.add(new JLabel("Location (D1, D2...): "));
            infoPanel.add(location);
            infoPanel.add(new JLabel("Town: "));
            infoPanel.add(town);
            infoPanel.add(bPadding1);
            infoPanel.add(create);
            
            centerPanel.add(p1, BorderLayout.NORTH);
            centerPanel.add(infoPanel, BorderLayout.CENTER);
            
            // padding/back button
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(paddingX2);
            rightPanel.setBackground(WHITE);
            
            JButton back = new JButton("BACK");
            back.setActionCommand("create new account");
            
            rightPanel.add(back);
            
            
            mainPanel.add(leftBlank, BorderLayout.WEST);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            
            this.add(new accountCreateLeftPanel(), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            standardiseChildren(infoPanel, true, true, true);
            standardiseChildren(mainPanel, true, false, true);
        }
    }
    
    public class createCustomer extends JPanel {
        public createCustomer() {
            this.setLayout(new BorderLayout());
            
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(rightPanelDimension);
            mainPanel.setBackground(WHITE);
            mainPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setPreferredSize(paddingX2);
            leftBlank.setBackground(WHITE);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(WHITE);
            centerPanel.setLayout(new BorderLayout());
            
            error = new JLabel("");
            
            JPanel p1 = new JPanel();
            p1.setLayout(new BorderLayout());
            p1.setPreferredSize(paddingY3);
            p1.setBackground(WHITE);
            p1.setForeground(Color.WHITE);
            p1.add(new JLabel("I'M LOOKING FOR A BARBER"), BorderLayout.PAGE_START);
            p1.add(error, BorderLayout.CENTER);
            
            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(WHITE);
            infoPanel.setLayout(new GridLayout(12,2, 0, 20));
            
            firstName = new JTextField(20);
            lastName = new JTextField(20);
            emailAddress = new JTextField(20);
            address = new JTextField(20);
            password = new JPasswordField(20);
            password.setEchoChar('•');
            phoneNumber = new JTextField(20);
            confirmPass = new JPasswordField(20);
            confirmPass.setEchoChar('•');
            JButton create = new JButton("CREATE ACCOUNT");
            create.setActionCommand("create customer");
            JPanel bPadding1 = new JPanel();
            bPadding1.setBackground(WHITE);
            JPanel bPadding2 = new JPanel();
            bPadding2.setBackground(WHITE);
            JPanel bPadding3 = new JPanel();
            bPadding3.setBackground(WHITE);
            
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
            infoPanel.add(new JLabel("Phone Number: "));
            infoPanel.add(phoneNumber);
            infoPanel.add(bPadding1);
            infoPanel.add(create);
            
            centerPanel.add(p1, BorderLayout.NORTH);
            centerPanel.add(infoPanel, BorderLayout.CENTER);
            
            // padding/back button
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(paddingX2);
            rightPanel.setBackground(WHITE);
            
            JButton back = new JButton("BACK");
            back.setActionCommand("create new account");
            
            rightPanel.add(back);
            
            
            mainPanel.add(leftBlank, BorderLayout.WEST);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            
            this.add(new accountCreateLeftPanel(), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            standardiseChildren(infoPanel, true, true, true);
            standardiseChildren(mainPanel, true, false, true);
        }
    }
    
    public class loggedLeftPanel extends JPanel {
        public loggedLeftPanel() {
            this.setPreferredSize(leftPanelDimension);
            this.setBackground(BLUE);
            this.setLayout(new BorderLayout());
            
            // **top panel**
            JPanel logoPanel = new JPanel();
            logoPanel.setPreferredSize(new Dimension((int)(windowWidth / 4), (int)(windowHeight / 3)));
            logoPanel.setBackground(BLUE);
            
            // WHITE panel
            JPanel whitePanel = new JPanel();
            whitePanel.setPreferredSize(new Dimension((int)(windowWidth / 5), (int)(windowHeight / 5)));
            whitePanel.setBackground(Color.white);
            whitePanel.setBorder(border(DARKBLUE, 3));
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
            
            // add labels to WHITE panel
            whitePanel.add(find, BorderLayout.NORTH);
            whitePanel.add(a, BorderLayout.CENTER);
            whitePanel.add(barber, BorderLayout.SOUTH);
            
            // add WHITE panel to top panel
            logoPanel.add(whitePanel);
            
            // **bottom panel**
            JPanel bottomPanel = new JPanel();
            bottomPanel.setBackground(BLUE);
            bottomPanel.setPreferredSize(new Dimension((int)(windowWidth / 4), (int)(2 * windowHeight / 3)));
            bottomPanel.setLayout(new BorderLayout());
            
            JPanel cacc = new JPanel();
            cacc.setBorder(border(DARKBLUE, 1));
            cacc.setBackground(DARKBLUE);
            cacc.setPreferredSize(new Dimension((int)windowWidth / 5, (int)windowHeight / 13));
            
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
            standardiseChildren(logoPanel, true, false ,true);
            standardiseChildren(bottomPanel, true, false ,true);
        }
    }
    
    public class customerMain extends JPanel {
        public customerMain() {
            this.setLayout(new BorderLayout());
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(rightPanelDimension);
            mainPanel.setBackground(WHITE);
            mainPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setPreferredSize(paddingX1);
            leftBlank.setBackground(WHITE);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(WHITE);
            centerPanel.setLayout(new BorderLayout());
            
            error = new JLabel();
            
            JPanel p1 = new JPanel();
            p1.setLayout(new BorderLayout());
            p1.setPreferredSize(paddingY1);
            p1.setBackground(WHITE);
            p1.setForeground(Color.WHITE);
            p1.add(error, BorderLayout.PAGE_START);
            
            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(WHITE);
            infoPanel.setLayout(new BorderLayout());
            
            // *** search panel ***
            JPanel searchPanel = new JPanel();
            searchPanel.setPreferredSize(loginPanelDimension);
            searchPanel.setLayout(new BorderLayout());
            searchPanel.setBorder(border(Color.BLACK, 2));
            searchPanel.setBackground(WHITE);
            
            JPanel searchTopPanel = new JPanel();
            searchTopPanel.setBackground(BLUE);
            JLabel findALabel = new JLabel("FIND A BARBER");
            findALabel.setForeground(WHITE);
            searchTopPanel.add(findALabel);
            
            JPanel searchMainPanel = new JPanel();
            searchMainPanel.setBackground(WHITE);
            searchMainPanel.setLayout(new BorderLayout());
            
            JPanel searchName = new JPanel();
            searchName.setBackground(WHITE);
            searchName.setLayout(new BorderLayout());
            
            JPanel topSearchName = new JPanel();
            topSearchName.setBackground(WHITE);
            barberName = new JTextField(8);
            topSearchName.add(new JLabel("By name:"));
            topSearchName.add(barberName);
            
            JPanel centerSearchName = new JPanel();
            centerSearchName.setBackground(WHITE);
            JButton searchNameButton = new JButton("SEARCH");
            searchNameButton.setActionCommand("search barber name");
            
            centerSearchName.add(searchNameButton);
            
            searchName.add(topSearchName, BorderLayout.CENTER);
            searchName.add(centerSearchName, BorderLayout.SOUTH);
            
            JPanel searchLocation = new JPanel();
            searchLocation.setBackground(WHITE);
            searchLocation.setLayout(new BorderLayout());
            
            JPanel topSearchLocation = new JPanel();
            topSearchLocation.setBackground(WHITE);
            allLocationsBox = new JComboBox(controller.getLocations());
            
            topSearchLocation.add(new JLabel("By location:"));
            topSearchLocation.add(allLocationsBox);
            
            JPanel centerSearchLocation = new JPanel();
            centerSearchLocation.setBackground(WHITE);
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
            nextBookingPanel.setBackground(WHITE);
            nextBookingPanel.setPreferredSize(loginPanelDimension);
            nextBookingPanel.setBorder(border(Color.BLACK, 2));
            nextBookingPanel.setLayout(new BorderLayout());
            
            JPanel leftNextBooking = new JPanel();
            leftNextBooking.setBackground(WHITE);
            leftNextBooking.setLayout(new BorderLayout());
            
            JPanel topNext = new JPanel();
            topNext.setBackground(WHITE);
            topNext.add(new JLabel("NEXT BOOKING:"));
            
            JPanel centerNext = new JPanel();
            centerNext.setBackground(WHITE);
            centerNext.setPreferredSize(paddingX5);
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
            rightNextBooking.setBackground(WHITE);
            rightNextBooking.setPreferredSize(paddingX1);
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
            p2.setPreferredSize(paddingY3);
            p2.setBackground(WHITE);
            
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
            rightPanel.setBackground(WHITE);
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
            
            
            standardiseChildren(mainPanel, true, false, true);
            viewBookings.setFont(smallFont);
            cancelBooking.setFont(smallFont);
        }
    }
    
    public class findABarber extends JPanel {
        public findABarber() {
            this.setLayout(new BorderLayout());
            
            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(WHITE);
            mainPanel.setLayout(new BorderLayout());
            
            // ** top panel **
            JPanel topPanel = new JPanel();
            topPanel.setBorder(border(DARKBLUE, 1));
            topPanel.setPreferredSize(new Dimension(windowWidth, (int)windowHeight / 5));
            topPanel.setBackground(WHITE);
            topPanel.setLayout(new BorderLayout());
            
            JPanel topTopPanel = new JPanel();
            topTopPanel.setBackground(WHITE);
            topTopPanel.setLayout(new BorderLayout());
            
            error = new JLabel();
            
            JButton returnPage = new JButton("BACK");
            returnPage.setActionCommand("back to main customer");
            
            topTopPanel.add(error, BorderLayout.WEST);
            topTopPanel.add(returnPage, BorderLayout.EAST);
            
            JPanel leftTopPanel = new JPanel();
            leftTopPanel.setLayout(new BorderLayout());
            leftTopPanel.setBackground(WHITE);
            
            JLabel topLeftLabel = new JLabel("BY NAME");
            topLeftLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            
            JPanel centerLeftPanel = new JPanel();
            centerLeftPanel.setBackground(WHITE);
            
            barberName = new JTextField(10);
            JButton searchName = new JButton("SEARCH");
            searchName.setActionCommand("search barber name");
            
            centerLeftPanel.add(barberName);
            centerLeftPanel.add(searchName);
            
            leftTopPanel.add(topLeftLabel, BorderLayout.NORTH);
            leftTopPanel.add(centerLeftPanel, BorderLayout.CENTER);
            
            JPanel rightTopPanel = new JPanel();
            rightTopPanel.setLayout(new BorderLayout());
            rightTopPanel.setBackground(WHITE);
            
            JLabel topRightTopLabel = new JLabel("BY LOCATION");
            JPanel centerRightTopPanel = new JPanel();
            centerRightTopPanel.setBackground(WHITE);
            
            allLocationsBox = new JComboBox(controller.getLocations());
            JButton searchLocation = new JButton("SEARCH");
            searchLocation.setActionCommand("search barber location");
            
            centerRightTopPanel.add(allLocationsBox);
            centerRightTopPanel.add(searchLocation);
            
            rightTopPanel.add(topRightTopLabel, BorderLayout.NORTH);
            rightTopPanel.add(centerRightTopPanel, BorderLayout.SOUTH);
            
            topPanel.add(topTopPanel, BorderLayout.NORTH);
            topPanel.add(leftTopPanel, BorderLayout.WEST);
            topPanel.add(rightTopPanel, BorderLayout.EAST);
            
            // ** left panel **
            leftFindABarberPanel = new JPanel();
            leftFindABarberPanel.setPreferredSize(paddingX5);
            leftFindABarberPanel.setBackground(WHITE);
            leftFindABarberPanel.setLayout(new BorderLayout());
            
            // ** right panel **
            rightFindABarberPanel = new JPanel();
            rightFindABarberPanel.setPreferredSize(paddingX5);
            rightFindABarberPanel.setBackground(WHITE);
            rightFindABarberPanel.setLayout(new BorderLayout());
            
            mainPanel.add(topPanel, BorderLayout.NORTH);
            mainPanel.add(leftFindABarberPanel, BorderLayout.WEST);
            mainPanel.add(rightFindABarberPanel, BorderLayout.CENTER);
            
            this.add(new loggedLeftPanel(), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            standardiseChildren(mainPanel, true, false, true);
            error.setForeground(Color.red);
        }
    }
    
    public class customerBookings extends JPanel {
        public customerBookings() {
            this.setLayout(new BorderLayout(15, 0));
            this.setBackground(WHITE);
            
            JPanel mainBookings = new JPanel();
            mainBookings.setLayout(new BorderLayout(30, 0));
            
            // center panel
            JPanel centerBookings = new JPanel();
            centerBookings.setLayout(new BorderLayout(15, 0));
            
            error = new JLabel();
            error.setPreferredSize(paddingY3);
            
            JPanel allBookings = new JPanel();
            allBookings.setBorder(border(DARKBLUE, 2));
            allBookings.setLayout(new BorderLayout(10, 10));
            
            JPanel bookings = new JPanel();
            ArrayList<String[]> b = controller.getCustomerBookings();
            bookings.setLayout(new GridLayout(b.size(), 1, 20, 30));
            
            if (b.size()>0) {
                for (int i = 0; i < b.size(); i++) {
                    JPanel book = new JPanel();
                    book.setBorder(border(DARKBLUE, 1));
                    book.setLayout(new BorderLayout());
                    
                    book.add(new JLabel("<html>Barber: " + b.get(i)[4] + "<br />"
                            + b.get(i)[5] + "<br />" 
                            + b.get(i)[6] + " - " + b.get(i)[7] + "<br />"
                            + "Phone: " + b.get(i)[8]
                            + "</html>"), BorderLayout.WEST);
                    book.add(new JLabel("<html>" + b.get(i)[1] + "<br />"
                            + b.get(i)[2] + "<br />"
                            + "Status: " + b.get(i)[3].substring(0, 1).toUpperCase() + b.get(i)[3].substring(1)
                            + "</html>"), BorderLayout.CENTER);
                    if (b.get(i)[3].equals("completed")) {
                        JButton review = new JButton("REVIEW");
                        review.setActionCommand("review " + b.get(i)[0]);
                        book.add(review, BorderLayout.EAST);
                    } else if (b.get(i)[3].equals("upcoming")) {
                        JButton cancel = new JButton("CANCEL");
                        cancel.setActionCommand("cancel booking " + b.get(i)[0]);
                        book.add(cancel, BorderLayout.EAST);
                    }

                    bookings.add(book);
                }
            } else {
                JPanel noBook = new JPanel();
                noBook.add(new JLabel("NO BOOKINGS FOUND"));
                
                bookings.add(noBook);
            }
            JScrollPane sp = new JScrollPane(bookings);
            standardiseChildren(bookings, true, true, true);
            
            allBookings.add(new JLabel("ALL MY BOOKINGS:"), BorderLayout.NORTH);
            allBookings.add(sp);
            
            JPanel padding = new JPanel();
            padding.setPreferredSize(paddingY1);
            
            centerBookings.add(error, BorderLayout.NORTH);
            centerBookings.add(allBookings, BorderLayout.CENTER);
            centerBookings.add(padding, BorderLayout.SOUTH);
            
            //right panel
            JPanel rightBookings = new JPanel();
            rightBookings.setLayout(new BorderLayout(10, 50));
            
            JButton back = new JButton("BACK");
            back.setActionCommand("back to main customer");
            
            JButton logOut = new JButton("LOG OUT");
            logOut.setActionCommand("log out");
            
            rightBookings.add(back, BorderLayout.NORTH);
            rightBookings.add(logOut, BorderLayout.SOUTH);
            
            mainBookings.add(centerBookings, BorderLayout.CENTER);
            mainBookings.add(rightBookings, BorderLayout.EAST);
            
            this.add(new loggedLeftPanel(), BorderLayout.WEST);
            this.add(mainBookings);
            
            standardiseChildren(mainBookings, true, true, true);
        }
    }
    
    public class submitReview extends JPanel {
        public submitReview(int bookingID) {
            this.setLayout(new BorderLayout(10, 0));
            this.setBackground(WHITE);
            
            JPanel centerSubmit = new JPanel();
            centerSubmit.setLayout(new BorderLayout(10, 10));
            
            JPanel padding = new JPanel();
            padding.setPreferredSize(paddingY3);
            error = new JLabel();
            padding.add(error);
            
            JPanel mainPanel = new JPanel();
            mainPanel.setBorder(border(DARKBLUE, 2));
            mainPanel.setLayout(new BorderLayout(5, 10));
            
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BorderLayout(10, 10));
            
            HashMap<String, String> info = controller.getBookingInfo(bookingID);
            
            infoPanel.add(new JLabel("<html>Barber: " + info.get("barber name") + " " + info.get("barber last name") + "<br />"
                    + info.get("date") + "<br />"
                    + info.get("time").substring(0, 5)
                    + "</html>"), BorderLayout.WEST);
            
            infoPanel.add(new JLabel("<html>Booking ID: " + bookingID + "<br />"
                    + info.get("address") + "<br />"
                    + info.get("town") + " - " + info.get("location") + "<br />"
                    + "Phone: " + info.get("barber phone")
                    + "</html>"), BorderLayout.EAST);
            
            JPanel reviewPanel = new JPanel();
            reviewPanel.setLayout(new BorderLayout(10, 10));
            
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());
            
            JPanel starPanel = new JPanel();
            
            star1 = new JButton();
            star1.setIcon(new ImageIcon(selectedStar()));
            star1.setActionCommand("star 1");
            
            star2 = new JButton();
            star2.setIcon(new ImageIcon(unselectedStar()));
            star2.setActionCommand("star 2");
            
            star3 = new JButton();
            star3.setIcon(new ImageIcon(unselectedStar()));
            star3.setActionCommand("star 3");
            
            star4 = new JButton();
            star4.setIcon(new ImageIcon(unselectedStar()));
            star4.setActionCommand("star 4");
            
            star5 = new JButton();
            star5.setIcon(new ImageIcon(unselectedStar()));
            star5.setActionCommand("star 5");
            
            starPanel.add(star1);
            starPanel.add(star2);
            starPanel.add(star3);
            starPanel.add(star4);
            starPanel.add(star5);
            
            topPanel.add(new JLabel("REVIEW:"), BorderLayout.WEST);
            topPanel.add(starPanel, BorderLayout.CENTER);
            
            JPanel centerPanel = new JPanel();
            review = new JTextArea(7, 20);
            
            centerPanel.add(review);
            
            JPanel bottomPanel = new JPanel();
            JButton submit = new JButton("SUBMIT");
            submit.setActionCommand("submit review " + bookingID);
            bottomPanel.add(submit);
            
            reviewPanel.add(topPanel, BorderLayout.NORTH);
            reviewPanel.add(centerPanel, BorderLayout.CENTER);
            reviewPanel.add(bottomPanel, BorderLayout.SOUTH);
            
            mainPanel.add(infoPanel, BorderLayout.NORTH);
            mainPanel.add(reviewPanel, BorderLayout.CENTER);
            
            JPanel p2 = new JPanel();
            p2.setPreferredSize(paddingY1);
            
            centerSubmit.add(padding, BorderLayout.NORTH);
            centerSubmit.add(mainPanel, BorderLayout.CENTER);
            centerSubmit.add(p2, BorderLayout.SOUTH);
            
            JPanel rightSubmit = new JPanel();
            rightSubmit.setLayout(new BorderLayout(10, 50));
            
            JButton back = new JButton("BACK");
            back.setActionCommand("back to main customer");
            
            JButton logOut = new JButton("LOG OUT");
            logOut.setActionCommand("log out");
            
            rightSubmit.add(back, BorderLayout.NORTH);
            rightSubmit.add(logOut, BorderLayout.SOUTH);
            
            this.add(new loggedLeftPanel(), BorderLayout.WEST);
            this.add(centerSubmit, BorderLayout.CENTER);
            this.add(rightSubmit, BorderLayout.EAST);
            
            standardiseChildren(centerSubmit, true, true, true);
            standardiseChildren(rightSubmit, true, true, true);
            standardiseChildren(starPanel, false, false, false);
        }
    }
    
    public class barberMain extends JPanel {
        public barberMain() {
            this.setLayout(new BorderLayout());
            
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(rightPanelDimension);
            mainPanel.setBackground(WHITE);
            mainPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setPreferredSize(paddingX1);
            leftBlank.setBackground(WHITE);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(WHITE);
            centerPanel.setLayout(new BorderLayout());
            
            error = new JLabel();
            
            JPanel p1 = new JPanel();
            p1.setLayout(new BorderLayout());
            p1.setPreferredSize(paddingY1);
            p1.setBackground(WHITE);
            p1.setForeground(Color.WHITE);
            p1.add(error, BorderLayout.PAGE_START);
            
            // *** CENTRAL INFO PANEL ***
            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(Color.blue);
            infoPanel.setLayout(new BorderLayout());
            infoPanel.setBorder(border(DARKBLUE, 2));
            
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
            
            try {
                ArrayList<String[]> upcoming = controller.getBarberUpcomingBookings();
                multipleBookingsPanel.setLayout(new GridLayout(upcoming.size() + 1,1));
                
                for (int i = 0; i < upcoming.size(); i++) {
                    JPanel booking = new JPanel();
                    booking.setPreferredSize(new Dimension(250, 230));
                    booking.setLayout(new BorderLayout());
                    booking.setBorder(border(DARKBLUE, 1));
                    
                    JPanel left = new JPanel();
                    left.setLayout(new BorderLayout());
                    left.setBackground(Color.WHITE);
                    
                    JPanel leftTop = new JPanel();
                    leftTop.add(new JLabel("UPCOMING BOOKING:"));
                    leftTop.setBackground(Color.WHITE);
                    
                    JPanel leftBottom = new JPanel();
                    leftBottom.setLayout(new BorderLayout());
                    leftBottom.setBackground(Color.WHITE);
                    
                    JLabel cust = new JLabel("<html>Customer: " + upcoming.get(i)[3] + "<br />Phone: " + upcoming.get(i)[4] + "</html>");
                    JLabel dt = new JLabel("Date: " + upcoming.get(i)[0]);

                    JPanel hrPanel = new JPanel();
                    hrPanel.setBackground(Color.WHITE);
                    hrPanel.add(new JLabel("<html>Status: " + upcoming.get(i)[5] + "<br />" + upcoming.get(i)[1] + "</html>"), BorderLayout.CENTER);
                    
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
                    cancel.setActionCommand("cancel booking " + upcoming.get(i)[2]);
                    
                    JButton accept = new JButton("CONFIRM");
                    accept.setActionCommand("confirm " + upcoming.get(i)[2]);
                    if (upcoming.get(i)[5].equals("requested")) {
                        right.add(accept, BorderLayout.NORTH);
                    }
                    right.add(cancel, BorderLayout.SOUTH);
                    
                    booking.add(left, BorderLayout.CENTER);
                    booking.add(right, BorderLayout.EAST);
                    
                    multipleBookingsPanel.add(booking);
                    standardiseChildren(multipleBookingsPanel, true, false, true);
                }
                
            } catch (Exception e) {
                System.out.println(e);
                System.out.println(e.getMessage());
                multipleBookingsPanel.add(new JLabel("NO BOOKINGS FOUND"));
            }
            
            multipleBookingsPanel.add(padding1);
            JScrollPane multiplePane = new JScrollPane(multipleBookingsPanel);
            
            infoPanel.add(topLabelPanel, BorderLayout.PAGE_START);
            infoPanel.add(multiplePane, BorderLayout.CENTER);
            
            
            JPanel p2 = new JPanel();
            p2.setPreferredSize(paddingY3);
            p2.setBackground(WHITE);
            
            centerPanel.add(p1, BorderLayout.NORTH);
            centerPanel.add(infoPanel, BorderLayout.CENTER);
            centerPanel.add(p2, BorderLayout.SOUTH);
            
            // padding/back button
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(paddingX3);
            rightPanel.setBackground(WHITE);
            rightPanel.setLayout(new BorderLayout());
            
            JButton logout = new JButton("LOG OUT");
            logout.setActionCommand("log out");
            logout.setPreferredSize(regularButtonDimension);
            
            JButton setAvailability = new JButton("<html>SET MY<br />AVAILABILITY</html>");
            setAvailability.setActionCommand("go to set availability");
            setAvailability.setPreferredSize(regularButtonDimension);
            
            rightPanel.add(setAvailability, BorderLayout.NORTH);
            rightPanel.add(logout, BorderLayout.SOUTH);
            
            mainPanel.add(leftBlank, BorderLayout.WEST);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            
            this.add(new loggedLeftPanel(), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            
            standardiseChildren(mainPanel, true, false, true);
        }
    }
    
    public class availabilityPage extends JPanel {
        public availabilityPage() {
            this.setLayout(new BorderLayout());
            
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(rightPanelDimension);
            mainPanel.setBackground(WHITE);
            mainPanel.setLayout(new BorderLayout());
            
            // ** 3 main panels **
            //*left panel - calendar*
            JPanel leftPanel = new JPanel();
            leftPanel.setPreferredSize(paddingX5);
            leftPanel.setBackground(WHITE);
            
            JPanel mainCalendar = new JPanel();
            mainCalendar.setPreferredSize(new Dimension((int)(windowWidth / 3.5), (int)(windowHeight / 1.1)));
            mainCalendar.setBorder(border(DARKBLUE, 2));
            
            leftPanel.add(mainCalendar);
            
            JPanel p2 = new JPanel();
            p2.setPreferredSize(new Dimension((int)(windowWidth / 4), (int)(windowHeight / 8)));
            
            JLabel pickLabel = new JLabel("PICK DATE:");
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime localToday = LocalDateTime.now();
            String today = dtf.format(localToday);
            
            date = new JComboBox[3];
            String[] days = new String[31];
            for (int i = 0; i < days.length; i++) {
                days[i] = String.valueOf(i + 1);
            }
            String[] months = new String[12];
            for (int i = 0; i < months.length; i++) {
                months[i] = String.valueOf(i + 1);
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
            
            
            JPanel p3 = new JPanel();
            p3.setPreferredSize(new Dimension((int)(windowWidth / 4), (int)(windowHeight / 8)));
            
            // !!!!FIX DATE PICKER TO GET PICKED DATE!!!!!
            selectDate = new JButton("PICK DATE");
            selectDate.setActionCommand("select date");
            
            p2.add(pickLabel);
            mainCalendar.add(p2);
            mainCalendar.add(date[0]);
            mainCalendar.add(date[1]);
            mainCalendar.add(date[2]);
            mainCalendar.add(p3);
            mainCalendar.add(selectDate);
            
            //*middle panel - times*
            JPanel midPanel = new JPanel();
            midPanel.setBackground(WHITE);
            
            mainTime = new JPanel();
            mainTime.setBorder(border(DARKBLUE, 2));
            mainTime.setPreferredSize(new Dimension((int)(windowWidth / 3.8), (int)(windowHeight / 1.1)));
            
            midPanel.add(mainTime);
            
            //*right panel - log out*
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(paddingX2);
            rightPanel.setBackground(WHITE);
            
            JPanel p4 = new JPanel();
            p4.setPreferredSize(paddingY3);
            p4.setBackground(WHITE);
            
            JButton goBack = new JButton("BACK");
            goBack.setActionCommand("back to main barber");
            
            JPanel p5 = new JPanel();
            p5.setPreferredSize(paddingY3);
            p5.setBackground(WHITE);
            
            JButton logOut = new JButton("LOG OUT");
            logOut.setActionCommand("log out");
            
            rightPanel.add(p4);
            rightPanel.add(goBack);
            rightPanel.add(p5);
            rightPanel.add(logOut);
            
            mainPanel.add(leftPanel, BorderLayout.WEST);
            mainPanel.add(midPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            
            this.add(new loggedLeftPanel(), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            standardiseChildren(mainPanel, true, false, true);
        }
    }
    
    public class barberBookings extends JPanel {
        public barberBookings(){
            this.setLayout(new BorderLayout(15, 0));
            this.setBackground(WHITE);
            
            JPanel mainBookings = new JPanel();
            mainBookings.setLayout(new BorderLayout(30, 0));
            
            // center panel
            JPanel centerBookings = new JPanel();
            centerBookings.setLayout(new BorderLayout(15, 0));
            
            error = new JLabel();
            error.setPreferredSize(paddingY3);
            
            JPanel allBookings = new JPanel();
            allBookings.setBorder(border(DARKBLUE, 2));
            allBookings.setLayout(new BorderLayout(10, 10));
            
            JPanel bookings = new JPanel();
            ArrayList<String[]> b = controller.getBarberBookings();
            bookings.setLayout(new GridLayout(b.size(), 1, 20, 30));
            
            if (b.size()>0) {
                for (int i = 0; i < b.size(); i++) {
                    JPanel book = new JPanel();
                    book.setBorder(border(DARKBLUE, 1));
                    book.setLayout(new BorderLayout());
                    
                    book.add(new JLabel("<html>Customer: " + b.get(i)[4] + "<br />"
                            + "Phone: " + b.get(i)[5] + "<br />"
                            + b.get(i)[1] + "<br />"
                            + b.get(i)[2].substring(0, 5) + "<br />"
                            + "Status: " + b.get(i)[3].substring(0, 1).toUpperCase() + b.get(i)[3].substring(1)
                            + "</html>"), BorderLayout.WEST);
                    if (!b.get(i)[3].equals("cancelled")) {
                        JButton change = new JButton("UPDATE");
                        change.setActionCommand("change status " + b.get(i)[0]);
                        book.add(change, BorderLayout.SOUTH);
                    }
                    if (b.get(i)[3].equals("completed")) {
                        JButton review = new JButton("SEE REVIEW");
                        review.setActionCommand("change status " + b.get(i)[0]);
                        book.add(review, BorderLayout.EAST);
                    } else if (b.get(i)[3].equals("upcoming")) {
                        JButton cancel = new JButton("CANCEL");
                        cancel.setActionCommand("cancel booking " + b.get(i)[0]);
                        book.add(cancel, BorderLayout.EAST);
                    } else if (b.get(i)[3].equals("requested")) {
                        JButton accept = new JButton("CONFIRM");
                        accept.setActionCommand("confirm " + b.get(i)[0]);
                        book.add(accept);
                    }

                    bookings.add(book);
                }
            } else {
                JPanel noBook = new JPanel();
                noBook.add(new JLabel("NO BOOKINGS FOUND"));
                
                bookings.add(noBook);
            }
            JScrollPane sp = new JScrollPane(bookings);
            standardiseChildren(bookings, true, true, true);
            
            allBookings.add(new JLabel("ALL MY BOOKINGS:"), BorderLayout.NORTH);
            allBookings.add(sp);
            
            JPanel padding = new JPanel();
            padding.setPreferredSize(paddingY1);
            
            centerBookings.add(error, BorderLayout.NORTH);
            centerBookings.add(allBookings, BorderLayout.CENTER);
            centerBookings.add(padding, BorderLayout.SOUTH);
            
            //right panel
            JPanel rightBookings = new JPanel();
            rightBookings.setLayout(new BorderLayout(10, 50));
            
            JButton back = new JButton("BACK");
            back.setActionCommand("back to main barber");
            
            JButton logOut = new JButton("LOG OUT");
            logOut.setActionCommand("log out");
            
            rightBookings.add(back, BorderLayout.NORTH);
            rightBookings.add(logOut, BorderLayout.SOUTH);
            
            mainBookings.add(centerBookings, BorderLayout.CENTER);
            mainBookings.add(rightBookings, BorderLayout.EAST);
            
            this.add(new loggedLeftPanel(), BorderLayout.WEST);
            this.add(mainBookings);
            
            standardiseChildren(mainBookings, true, true, true);
        }
    }
    
    /**
     * sets standard parameters to components 
     * concept retrieved from https://stackoverflow.com/questions/27774581/change-background-color-of-components-with-reference-to-color-variable-java
    */
    private void standardiseChildren(JPanel panel, boolean font, boolean foreground, boolean background) {
        if (panel.getBackground().equals(DEFAULTCOLOUR)) {
            panel.setBackground(WHITE);
        }
        Component[] component = panel.getComponents();
        for(Component c : component) {
            if (c instanceof JButton) {
                if (((JButton) c).getActionCommand().contains("star")) {
                    ((JButton) c).addActionListener(controller);
                    ((JButton) c).setCursor(new Cursor(Cursor.HAND_CURSOR));
                    c.setBackground(null);
                    ((JButton) c).setBorder(null);
                } else {
                    if (font) {
                        c.setFont(bodyFont);
                    }
                    if (background) {
                        c.setBackground(BLUE);
                        ((JButton) c).setBorder(border(DARKBLUE, 2));
                        c.setForeground(WHITE);
                    }
                    if (foreground) {
                        c.setPreferredSize(regularButtonDimension);
                    }
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
                    c.setBackground(TEXTFIELDCOLOUR);
                    ((JTextField) c).setBorder(border(DARKBLUE, 1));
                }
                if (font) {
                    c.setFont(bodyFont);
                }
            } else if(c instanceof JTextArea) {
                c.setBackground(TEXTFIELDCOLOUR);
                ((JTextArea) c).setBorder(border(DARKBLUE, 1));
                c.setFont(bodyFont);
            } else if (c instanceof JComboBox) {
                c.setBackground(WHITE);
                ((JComboBox) c).setBorder(border(DARKBLUE, 1));
                c.setFont(bodyFont);
                ((JComboBox) c).setUI(ColorArrowUI.createUI(((JComboBox) c)));
            } else if(c instanceof JCheckBox){
                c.setBackground(WHITE);
                c.setFont(bodyFont);
            } else if (c instanceof JScrollPane) {
                ((JScrollPane) c).getVerticalScrollBar().setBackground(DARKBLUE);
                ((JScrollPane) c).getVerticalScrollBar().setUI(new standardScrollBar());
            }
        }
    }
    
    /**
     * code to change color of combo box retrieved from
     * http://www.java2s.com/Tutorial/Java/0240__Swing/CustomizingaJComboBoxLookandFeel.htm
     */
    public static class ColorArrowUI extends BasicComboBoxUI{
        public static ComboBoxUI createUI(JComponent c) {
            return new ColorArrowUI();
        }
        
        @Override
        protected JButton createArrowButton() {
            return new BasicArrowButton(
                    BasicArrowButton.SOUTH,
                    BLUE, DARKBLUE,
                    WHITE, DARKBLUE
            );
        }

    }
    
    /**
     * hiding buttons from scrollbar code retrieved from
     * https://stackoverflow.com/questions/7633354/how-to-hide-the-arrow-buttons-in-a-jscrollbar
     */
    public static class standardScrollBar extends BasicScrollBarUI {
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return invisibleButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return invisibleButton();
        }

        private JButton invisibleButton() {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(0,0));
            btn.setMaximumSize(new Dimension(0,0));
            btn.setMinimumSize(new Dimension(0,0));
            return btn;
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
    
    public String getPhoneNumber() {
        return phoneNumber.getText();
    }
    
    public String getSetLocation() {
        return location.getText();
    }
    
    public String getpickedDate() {
        String full = pickedDate.getText();
        String y = full.substring(6);
        String m = full.substring(3, 5);
        String d = full.substring(0, 2);
        
        return y+"-"+m+"-"+d;
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
    
    public HashMap<String, Boolean> getAvailableCheckBoxSelection() {
        HashMap<String, Boolean> available = new HashMap<>();
        
        for (int i = 0; i < availableCheckBox.length; i++) {
            available.put(availableCheckBox[i].getName() + ":00", availableCheckBox[i].isSelected());
        }
        
        return available;
    }
    
    public String getReview() {
        return review.getText();
    }
    
    public int getStars() {
        return stars;
    }
    
    public void setReview(String r) {
        review.setText(r);
    }
    
    public void setStars(int s) {
        stars = s;
    }
    
    public void setBarberName(String n) {
        barberName.setText(n);
    }
    
    public void setPickedDate() {
        if (allTimesSP != null) {
            allTimesSP.removeAll();
        }
        if (mainTime != null) {
            mainTime.removeAll();
        }
        
        pickedDate = new JLabel();
        mainTime.add(pickedDate);

        allTimes = new JPanel();
        allTimes.setBackground(WHITE);
        allTimes.setLayout(new GridLayout(48, 1));
        String addZeroToDay = "";
        if (Integer.parseInt(date[0].getSelectedItem().toString())<10) {
            addZeroToDay = "0";
        }
        String pickedDay = addZeroToDay + date[0].getSelectedItem();
        String addZeroToMonth = "";
        if (Integer.parseInt(date[1].getSelectedItem().toString())<10) {
            addZeroToMonth = "0";
        }
        String pickedMonth = addZeroToMonth + date[1].getSelectedItem();
        pickedDate.setText(pickedDay + "/" + pickedMonth + "/" + date[2].getSelectedItem());
        isAvailable = controller.checkBarberAvailability(controller.getSessionID(), getpickedDate());
        
        int h = 0;
        boolean isHalf = false;
        String m = ":00";
        String currentTime;
        availableCheckBox = new JCheckBox[48];

        for (int i = 0; i < 48; i++) {
                JPanel singleTime = new JPanel();
                singleTime.setBorder(border(DARKBLUE, 1));
                singleTime.setPreferredSize(new Dimension((int)(windowWidth / 4.5), (int)(windowHeight / 10)));
                singleTime.setLayout(new BorderLayout());
                
                String addZero = "";
                if (h<10) {
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
            allTimesSP.setPreferredSize(new Dimension((int)(windowWidth / 3.9), (int)(windowHeight / 1.4)));
            mainTime.add(allTimesSP);
            
            
            enterAvailability = new JButton("ENTER AVAILABILITY");
            enterAvailability.setActionCommand("enter barber availability");
            
            mainTime.add(enterAvailability);
            
            standardiseChildren(mainTime, true, false, true);
            standardiseChildren(allTimes, true, false, true);
    }
    
    public void searchForBarber(String searchBy) {
        if (leftFindABarberPanel.getComponentCount()>1) {
            leftFindABarberPanel.removeAll();
        }
        leftFindABarberPanel.add(new JLabel("SEARCH RESULT: "), BorderLayout.NORTH);
        ArrayList<String[]> searchResults;
        if (searchBy.equals("name")) {
            searchResults = controller.searchForBarberName();
        } else {
            searchResults = controller.searchForBarberLocation();
        }
        
        JPanel allBarbers = new JPanel();
        allBarbers.setLayout(new GridLayout(searchResults.size() + 1,1));
        
        if (searchResults.size()>0) {
            
            for (int i = 0; i < searchResults.size(); i++) {
                JPanel singleBarber = new JPanel();
                singleBarber.setLayout(new BorderLayout());
                singleBarber.setBorder(border(DARKBLUE, 1));
                singleBarber.setBackground(WHITE);
                singleBarber.setPreferredSize(new Dimension((int)(windowWidth / 3.5), (int)windowHeight / 10));
                singleBarber.setMaximumSize(new Dimension((int)(windowWidth / 3.5), (int)windowHeight / 10));
                singleBarber.setMinimumSize(new Dimension((int)(windowWidth / 3.5), (int)windowHeight / 10));

                JPanel leftSingle = new JPanel();
                leftSingle.setBackground(WHITE);
                leftSingle.setLayout(new BorderLayout());
                leftSingle.add(new JLabel("<html>" + searchResults.get(i)[1].toUpperCase() + " " + searchResults.get(i)[2].toUpperCase() + "<br />"
                        + searchResults.get(i)[4] + "<br />" + searchResults.get(i)[5] + " - " + searchResults.get(i)[6] + "<br />"
                        + "Phone No: " + searchResults.get(i)[3]
                        + "</html>"), BorderLayout.CENTER);

                JPanel rightSingle = new JPanel();
                rightSingle.setBackground(WHITE);

                JButton checkBarber = new JButton("CHECK");
                checkBarber.setPreferredSize(new Dimension((int) 100,(int) 45));
                checkBarber.setActionCommand("check availability " + searchResults.get(i)[0]);

                rightSingle.add(checkBarber);

                singleBarber.add(leftSingle, BorderLayout.WEST);
                singleBarber.add(rightSingle, BorderLayout.CENTER);

                allBarbers.add(singleBarber);
            }
        
            JPanel p1 = new JPanel();
            p1.setPreferredSize(new Dimension((int)(windowWidth / 3.5), (int)(windowHeight / 10)));
            p1.setBackground(WHITE);

            allBarbers.add(p1);

            JScrollPane sp = new JScrollPane(allBarbers);
            leftFindABarberPanel.add(sp, BorderLayout.CENTER);
        } else {
            leftFindABarberPanel.add(new JLabel("NO MATCHES FOUND"));
        }
        
        standardiseChildren(leftFindABarberPanel, true, false, true);
        standardiseChildren(allBarbers, true, false, true);
    }
    
    public void showBarberAvailability(int getB) {
        ArrayList<String[]> availableList = controller.getbarberAvailability(getB, null);
        String[] bInfo = controller.getBarber(getB);
        rightFindABarberPanel.add(new JLabel("<html>AVAILABLE TIMES: <br />" + bInfo[0] + "<br />" + bInfo[1] + ", " + bInfo[2] + " - " + bInfo[3] + "<br />Phone: " + bInfo[4] + "</html>"), BorderLayout.NORTH);
        
        JPanel times = new JPanel();
        times.setBackground(WHITE);
        times.setLayout(new GridLayout(availableList.size(), 1, 3, 5));
        
        for (int i = 0; i < availableList.size(); i++) {
            JPanel st = new JPanel();
            st.setBackground(WHITE);
            st.setBorder(border(DARKBLUE, 1));
            st.add(new JLabel(availableList.get(i)[0] + " | " + availableList.get(i)[1]));
            JButton bookB = new JButton("BOOK");
            bookB.setActionCommand("book " + availableList.get(i)[0] + " " + availableList.get(i)[1] + " " + getB);
            st.add(bookB);
            times.add(st);
        }
        
        JScrollPane sp = new JScrollPane(times);
        rightFindABarberPanel.add(sp);
        
        standardiseChildren(rightFindABarberPanel, true, false, true);
        standardiseChildren(times, true, false, true);
    }
    
    public void setError(String e) {
        error.setText(e);
    }
    
    public Image unselectedStar() {
        Image star = null;
        try {
            star = ImageIO.read(getClass().getResource("unselectedStar.png"));
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        return star.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
    }
    
    public Image selectedStar() {
        Image star = null;
        try {
            star = ImageIO.read(getClass().getResource("selectedStar.png"));
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        return star.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
    }
    
    public void starPressed(int n) {
        switch(n) {
            case 5:
                star5.setIcon(new ImageIcon(selectedStar()));
                star4.setIcon(new ImageIcon(selectedStar()));
                star3.setIcon(new ImageIcon(selectedStar()));
                star2.setIcon(new ImageIcon(selectedStar()));
                star1.setIcon(new ImageIcon(selectedStar()));
                break;
            case 4:
                star5.setIcon(new ImageIcon(unselectedStar()));
                star4.setIcon(new ImageIcon(selectedStar()));
                star3.setIcon(new ImageIcon(selectedStar()));
                star2.setIcon(new ImageIcon(selectedStar()));
                star1.setIcon(new ImageIcon(selectedStar()));
                break;
            case 3:
                star5.setIcon(new ImageIcon(unselectedStar()));
                star4.setIcon(new ImageIcon(unselectedStar()));
                star3.setIcon(new ImageIcon(selectedStar()));
                star2.setIcon(new ImageIcon(selectedStar()));
                star1.setIcon(new ImageIcon(selectedStar()));
                break;
            case 2:
                star5.setIcon(new ImageIcon(unselectedStar()));
                star4.setIcon(new ImageIcon(unselectedStar()));
                star3.setIcon(new ImageIcon(unselectedStar()));
                star2.setIcon(new ImageIcon(selectedStar()));
                star1.setIcon(new ImageIcon(selectedStar()));
                break;
            case 1:
                star5.setIcon(new ImageIcon(unselectedStar()));
                star4.setIcon(new ImageIcon(unselectedStar()));
                star3.setIcon(new ImageIcon(unselectedStar()));
                star2.setIcon(new ImageIcon(unselectedStar()));
                star1.setIcon(new ImageIcon(selectedStar()));
                break;
        }
        stars = n;
    }
}