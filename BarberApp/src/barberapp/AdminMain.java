/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp;

import static barberapp.View.standardiseChildren;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class AdminMain extends JPanel {
    public AdminMain(Controller controller) {
            this.setLayout(new BorderLayout(10, 10));
            this.setBackground(Globals.WHITE);
            
            JPanel topPanel = new JPanel();
            topPanel.setPreferredSize(new Dimension(Globals.windowWidth, (int)(Globals.windowHeight / 5)));
            topPanel.setBackground(Globals.BLUE);
            topPanel.setForeground(Globals.WHITE);
            topPanel.setBorder(Globals.border(Globals.DARKBLUE, 2));
            topPanel.setLayout(new BorderLayout());
            
            JLabel l = new JLabel("ADMIN");
            l.setFont(Globals.titleFont);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            
            JPanel logPanel = new JPanel();
            logPanel.setBackground(Globals.BLUE);
            
            JButton logout = new JButton("LOG OUT");
            logout.setActionCommand("log out");
            
            logPanel.add(logout);
            
            topPanel.add(l, BorderLayout.CENTER);
            topPanel.add(logPanel, BorderLayout.EAST);
            
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout(10, 10));
            
            JPanel leftMainPanel = new JPanel();
            leftMainPanel.setLayout(new BorderLayout());
            leftMainPanel.setPreferredSize(new Dimension((Globals.windowWidth / 2) - 20, (Globals.windowHeight / 3) - 20));
            JPanel rightMainPanel = new JPanel();
            rightMainPanel.setLayout(new BorderLayout());
            rightMainPanel.setPreferredSize(new Dimension((Globals.windowWidth / 2) - 20, (Globals.windowHeight / 3) - 20));
            
            leftMainPanel.add(new JLabel("<html>Number of accounts: " + controller.getAccountsCount() + "<br />"
                    + "Number of barbers: " + controller.getBarbersCount() + "<br />"
                    + "Number of customers: " + controller.getCustomersCount() + "<br />"
                    + "</html>"), BorderLayout.EAST);
            rightMainPanel.add(new JLabel("<html>Number of appointments: " + controller.getTotalAppointmentsCount() + "<br />"
                    + "Completed appointments: " + controller.getAppointmentsCount("completed") + "<br />"
                    + "No show appointments: " + controller.getAppointmentsCount("no show") + "<br />"
                    + "Upcoming appointments: " + controller.getAppointmentsCount("upcoming") + "<br />"
                    + "Requested appointments: " + controller.getAppointmentsCount("requested") + "<br />"
                    + "Cancelled appointments: " + controller.getAppointmentsCount("cancelled") + "<br />"
                    + "</html>"), BorderLayout.WEST);
            
            JPanel botPanel = new JPanel();
            
            JButton graphs = new JButton("GRAPHS");
            
            botPanel.add(graphs);
            
            mainPanel.add(leftMainPanel, BorderLayout.WEST);
            mainPanel.add(rightMainPanel, BorderLayout.EAST);
            mainPanel.add(botPanel, BorderLayout.SOUTH);
            
            this.add(topPanel, BorderLayout.NORTH);
            this.add(mainPanel, BorderLayout.CENTER);
            standardiseChildren(topPanel, true, controller);
            standardiseChildren(mainPanel, true, controller);
        }
}
