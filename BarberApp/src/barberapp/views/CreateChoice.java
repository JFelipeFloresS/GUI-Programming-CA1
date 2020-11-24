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
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class CreateChoice extends JPanel {

    /**
     * Creates main page of choice between creating a barber account and
     * customer account pages.
     *
     * @param controller controller for CreateChoice
     */
    public CreateChoice(Controller controller) {
        this.setLayout(new BorderLayout());

        // **main panel**
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(Globals.RIGHT_PANEL_DIMENSION);
        mainPanel.setBackground(Globals.WHITE);
        mainPanel.setLayout(new BorderLayout());

        // padding
        JPanel leftBlank = new JPanel();
        leftBlank.setPreferredSize(Globals.PADDING_X2);
        leftBlank.setBackground(Globals.WHITE);

        // center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Globals.WHITE);
        centerPanel.setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        p1.setPreferredSize(Globals.PADDING_Y3);
        p1.setBackground(Globals.WHITE);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Globals.WHITE);
        buttonsPanel.setLayout(new BorderLayout());

        JButton createCustomer = new JButton("I'M LOOKING FOR A BARBER");
        createCustomer.setActionCommand("go to create customer");
        createCustomer.setPreferredSize(new Dimension((int) Globals.WINDOW_WIDTH / 5, (int) Globals.WINDOW_HEIGHT / 8));

        JPanel p3 = new JPanel();
        p3.setBackground(Globals.WHITE);

        JButton createBarber = new JButton("I'M A BARBER");
        createBarber.setActionCommand("go to create barber");
        createBarber.setPreferredSize(new Dimension((int) Globals.WINDOW_WIDTH / 5, (int) Globals.WINDOW_HEIGHT / 8));

        buttonsPanel.add(createCustomer, BorderLayout.NORTH);
        buttonsPanel.add(p3, BorderLayout.CENTER);
        buttonsPanel.add(createBarber, BorderLayout.SOUTH);

        JPanel p2 = new JPanel();
        p2.setPreferredSize(Globals.PADDING_Y3);
        p2.setBackground(Globals.WHITE);

        centerPanel.add(p1, BorderLayout.NORTH);
        centerPanel.add(buttonsPanel, BorderLayout.CENTER);
        centerPanel.add(p2, BorderLayout.SOUTH);

        // padding/back button
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(Globals.PADDING_X3);
        rightPanel.setBackground(Globals.WHITE);

        JButton back = new JButton("BACK");
        back.setActionCommand("back to initial page");

        rightPanel.add(back);

        mainPanel.add(leftBlank, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        this.add(new AccountCreateLeftPanel(controller), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);

        View.standardiseChildren(mainPanel, true, controller);
    }
}
