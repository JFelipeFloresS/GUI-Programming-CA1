/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

 public class InitialPage extends JPanel {
        /**
         * Shows initial page.
         * @param controller controller for the initial page
         */
        public InitialPage(Controller controller) {
            this.setLayout(new BorderLayout());
            
            // **top panel**
            JPanel topPanel = new JPanel();
            topPanel.setPreferredSize(Globals.topPanelDimension);
            topPanel.setBackground(Globals.BLUE);
            
            // page title
            JLabel panelTitle = new JLabel("FIND A BARBER");
            panelTitle.setHorizontalAlignment(SwingConstants.CENTER);
            panelTitle.setForeground(Color.WHITE);
            panelTitle.setFont(Globals.titleFont);
            
            topPanel.add(panelTitle);
            
            // **main panel**
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(Globals.bottomPanelDimension);
            mainPanel.setBackground(Globals.WHITE);
            mainPanel.setLayout(new BorderLayout(0, 10));
            
            // blank panel
            JPanel blankPanel = new JPanel();
            blankPanel.setPreferredSize(Globals.paddingY3);
            blankPanel.setBackground(Globals.WHITE);
            
            // center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(Globals.WHITE);
            centerPanel.setLayout(new BorderLayout());
            
            // padding
            JPanel leftBlank = new JPanel();
            leftBlank.setBackground(Globals.WHITE);
            leftBlank.setPreferredSize(Globals.paddingX4);
            JPanel rightBlank = new JPanel();
            rightBlank.setBackground(Globals.WHITE);
            rightBlank.setPreferredSize(Globals.paddingX4);
            
            // log in panel
            JPanel loginPanel = new JPanel();
            loginPanel.setPreferredSize(Globals.loginPanelDimension);
            loginPanel.setBorder(Globals.border(Color.BLACK, 4));
            loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
            loginPanel.setBackground(Globals.WHITE);
            
            // emailAddress input panel
            JPanel user = new JPanel();
            user.setLayout(new GridLayout(2, 1));
            user.setPreferredSize(new Dimension(500, 80));
            user.setMaximumSize(new Dimension(500, 80));
            user.setBackground(Globals.WHITE);
            JLabel userLabel = new JLabel("E-mail address: ");
            Globals.emailAddress = new JTextField(20);
            
            // add label and text field for emailAddress
            user.add(userLabel);
            user.add(Globals.emailAddress);
            
            // password input panel
            JPanel pass = new JPanel();
            pass.setLayout(new GridLayout(2, 1));
            pass.setPreferredSize(new Dimension(500, 80));
            pass.setMaximumSize(new Dimension(500, 80));
            pass.setBackground(Globals.WHITE);
            JLabel passLabel = new JLabel("Password: ");
            Globals.password = new JPasswordField(20);
            Globals.password.setEchoChar('•');
            
            // add label and text field for password
            pass.add(passLabel);
            pass.add(Globals.password);
            
            JPanel pad = new JPanel();
            pad.setPreferredSize(new Dimension(50, 30));
            pad.setMaximumSize(new Dimension(50, 30));
            pad.setBackground(Globals.WHITE);
            
            // log in button panel
            JPanel logPanel = new JPanel();
            logPanel.setBackground(Globals.WHITE);
            JButton login = new JButton("LOG IN");
            login.setActionCommand("log in");
            Globals.password.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                        login.doClick();
                    }
                }
            });
            Globals.emailAddress.addKeyListener(new KeyAdapter() {
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
            createAccountPanel.setBackground(Globals.WHITE);
            createAccountPanel.setPreferredSize(Globals.paddingY4);
            
            // line break code retrieved from https://stackoverflow.com/questions/13503280/new-line-n-is-not-working-in-jbutton-settextfnord-nfoo/13503281
            JButton createAccount = new JButton("<html>   CREATE<br/>ACCOUNT   </html>");
            createAccount.setActionCommand("create new account");
            
            createAccountPanel.add(createAccount);
            
                       
            // add panels to main panel
            mainPanel.add(blankPanel, BorderLayout.PAGE_START);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(createAccountPanel, BorderLayout.SOUTH);
            View.standardiseChildren(mainPanel, true, controller);
            // adding panels to initial page
            this.add(topPanel, BorderLayout.PAGE_START);
            this.add(mainPanel, BorderLayout.CENTER);
            Globals.emailAddress.requestFocusInWindow();
            
        }
        
    }