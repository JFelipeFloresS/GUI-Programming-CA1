/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.views;

import barberapp.main.Controller;
import barberapp.main.Globals;
import barberapp.main.View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class CreateCustomer extends JPanel {

    public static JTextField emailAddress = null;
    public static JPasswordField password = null;
    public static JPasswordField confirmPass = null;
    public static JTextField firstName = null;
    public static JTextField lastName = null;
    public static JTextField phoneNumber = null;

    /**
     * Creates page to create a customer account.
     *
     * @param controller controller for CreateCustomer
     */
    public CreateCustomer(Controller controller) {
        this.setLayout(new BorderLayout());

        // **main panel**
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(Globals.RIGHT_PANEL_DIMENSION);
        mainPanel.setBackground(Globals.WHITE);
        mainPanel.setLayout(new BorderLayout());

        // padding
        JPanel leftBlank = new JPanel();
        leftBlank.setPreferredSize(Globals.PADDING_X1);
        leftBlank.setBackground(Globals.WHITE);

        // center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Globals.WHITE);
        centerPanel.setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p1.setPreferredSize(Globals.PADDING_Y3);
        p1.setBackground(Globals.WHITE);
        p1.setForeground(Color.WHITE);
        p1.add(new JLabel("I'M LOOKING FOR A BARBER"), BorderLayout.PAGE_START);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Globals.WHITE);
        infoPanel.setLayout(new GridLayout(12, 2, 0, 20));

        firstName = new JTextField(20);
        lastName = new JTextField(20);
        emailAddress = new JTextField(20);
        password = new JPasswordField(20);
        password.setEchoChar('•');
        phoneNumber = new JTextField(20);
        confirmPass = new JPasswordField(20);
        confirmPass.setEchoChar('•');
        JButton create = new JButton("CREATE ACCOUNT");
        create.setActionCommand("create customer");
        JPanel bPadding1 = new JPanel();
        bPadding1.setBackground(Globals.WHITE);
        JPanel bPadding2 = new JPanel();
        bPadding2.setBackground(Globals.WHITE);
        JPanel bPadding3 = new JPanel();
        bPadding3.setBackground(Globals.WHITE);

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
        rightPanel.setPreferredSize(Globals.PADDING_X3);
        rightPanel.setBackground(Globals.WHITE);

        JButton back = new JButton("BACK");
        back.setActionCommand("create new account");

        rightPanel.add(back);

        mainPanel.add(leftBlank, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        this.add(new AccountCreateLeftPanel(controller), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);

        View.standardiseChildren(infoPanel, true, controller);
        View.standardiseChildren(mainPanel, true, controller);
    }

    /**
     * @return email address input into the text field emailAddress
     */
    public static String getEmailAddress() {
        return emailAddress.getText();
    }

    /**
     * @return password input into the password field password
     */
    public static String getPass() {
        return password.getText();
    }

    /**
     * @return password input into the password field confirmPass
     */
    public static String getConfirmPass() {
        return confirmPass.getText();
    }

    /**
     * @return first name input into the text field firstName
     */
    public static String getFirstName() {
        return firstName.getText();
    }

    /**
     * @return last name input into the text field lastName
     */
    public static String getLastName() {
        return lastName.getText();
    }

    /**
     * @return phone input into the text field phoneNumber
     */
    public static String getPhoneNumber() {
        return phoneNumber.getText();
    }

}
