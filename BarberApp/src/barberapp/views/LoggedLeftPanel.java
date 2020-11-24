/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.views;

import barberapp.main.Controller;
import barberapp.main.Globals;
import static barberapp.main.View.standardiseChildren;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class LoggedLeftPanel extends JPanel {

    /**
     * Creates left panel for all logged pages.
     *
     * @param controller controller for LoggedLeftPanel
     */
    public LoggedLeftPanel(Controller controller) {
        this.setPreferredSize(Globals.LEFT_PANEL_DIMENSION);
        this.setBackground(Globals.BLUE);
        this.setLayout(new BorderLayout());

        // **top panel**
        JPanel logoPanel = new JPanel();
        logoPanel.setPreferredSize(new Dimension((int) (Globals.WINDOW_WIDTH / 4), (int) (Globals.WINDOW_HEIGHT / 3)));
        logoPanel.setBackground(Globals.BLUE);

        // WHITE panel
        JPanel whitePanel = new JPanel();
        whitePanel.setPreferredSize(new Dimension((int) (Globals.WINDOW_WIDTH / 5), (int) (Globals.WINDOW_HEIGHT / 5)));
        whitePanel.setBackground(Color.white);
        whitePanel.setBorder(Globals.border(Globals.DARKBLUE, 3));
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
        bottomPanel.setBackground(Globals.BLUE);
        bottomPanel.setPreferredSize(new Dimension((int) (Globals.WINDOW_WIDTH / 4), (int) (2 * Globals.WINDOW_HEIGHT / 3)));
        bottomPanel.setLayout(new BorderLayout());

        JPanel cacc = new JPanel();
        cacc.setBorder(Globals.border(Globals.DARKBLUE, 1));
        cacc.setBackground(Globals.DARKBLUE);
        cacc.setPreferredSize(new Dimension((int) Globals.WINDOW_WIDTH / 5, (int) Globals.WINDOW_HEIGHT / 13));

        JLabel welcome = new JLabel("WELCOME");
        welcome.setForeground(Color.WHITE);
        JLabel thisName = new JLabel(controller.getSessionFirstName().toUpperCase());
        thisName.setForeground(Color.WHITE);

        cacc.add(welcome);
        cacc.add(thisName);

        bottomPanel.add(cacc, BorderLayout.SOUTH);

        this.add(logoPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);

        standardiseChildren(logoPanel, true, controller);
        standardiseChildren(bottomPanel, true, controller);
        welcome.setForeground(Color.white);
        thisName.setForeground(Color.white);
    }
}
