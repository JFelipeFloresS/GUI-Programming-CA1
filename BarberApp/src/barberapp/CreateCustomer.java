/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp;

import static barberapp.View.standardiseChildren;
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
    /**
         * Creates page to create a customer account.
         * 
         * @param controller controller for CreateCustomer
         */
        public CreateCustomer(Controller controller) {
            this.setLayout(new BorderLayout());
            
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(Globals.rightPanelDimension);
            mainPanel.setBackground(Globals.WHITE);
            mainPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setPreferredSize(Globals.paddingX1);
            leftBlank.setBackground(Globals.WHITE);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(Globals.WHITE);
            centerPanel.setLayout(new BorderLayout());
            
            JPanel p1 = new JPanel();
            p1.setLayout(new BorderLayout());
            p1.setPreferredSize(Globals.paddingY3);
            p1.setBackground(Globals.WHITE);
            p1.setForeground(Color.WHITE);
            p1.add(new JLabel("I'M LOOKING FOR A BARBER"), BorderLayout.PAGE_START);
            
            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(Globals.WHITE);
            infoPanel.setLayout(new GridLayout(12,2, 0, 20));
            
            Globals.firstName = new JTextField(20);
            Globals.lastName = new JTextField(20);
            Globals.emailAddress = new JTextField(20);
            Globals.address = new JTextField(20);
            Globals.password = new JPasswordField(20);
            Globals.password.setEchoChar('•');
            Globals.phoneNumber = new JTextField(20);
            Globals.confirmPass = new JPasswordField(20);
            Globals.confirmPass.setEchoChar('•');
            JButton create = new JButton("CREATE ACCOUNT");
            create.setActionCommand("create customer");
            JPanel bPadding1 = new JPanel();
            bPadding1.setBackground(Globals.WHITE);
            JPanel bPadding2 = new JPanel();
            bPadding2.setBackground(Globals.WHITE);
            JPanel bPadding3 = new JPanel();
            bPadding3.setBackground(Globals.WHITE);
            
            infoPanel.add(new JLabel("Email: "));
            infoPanel.add(Globals.emailAddress);
            infoPanel.add(new JLabel("Password: "));
            infoPanel.add(Globals.password);
            infoPanel.add(new JLabel("Confirm Password: "));
            infoPanel.add(Globals.confirmPass);
            infoPanel.add(new JLabel("First Name: "));
            infoPanel.add(Globals.firstName);
            infoPanel.add(new JLabel("Last Name: "));
            infoPanel.add(Globals.lastName);
            infoPanel.add(new JLabel("Phone Number: "));
            infoPanel.add(Globals.phoneNumber);
            infoPanel.add(bPadding1);
            infoPanel.add(create);
            
            centerPanel.add(p1, BorderLayout.NORTH);
            centerPanel.add(infoPanel, BorderLayout.CENTER);
            
            // padding/back button
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(Globals.paddingX3);
            rightPanel.setBackground(Globals.WHITE);
            
            JButton back = new JButton("BACK");
            back.setActionCommand("create new account");
            
            rightPanel.add(back);
            
            
            mainPanel.add(leftBlank, BorderLayout.WEST);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            
            this.add(new AccountCreateLeftPanel(controller), BorderLayout.WEST);
            this.add(mainPanel, BorderLayout.CENTER);
            
            standardiseChildren(infoPanel, true, controller);
            standardiseChildren(mainPanel, true, controller);
        }
}
