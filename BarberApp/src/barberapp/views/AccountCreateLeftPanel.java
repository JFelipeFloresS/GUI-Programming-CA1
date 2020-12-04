/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.views;

import barberapp.main.Globals;
import static barberapp.main.View.standardiseChildren;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class AccountCreateLeftPanel extends JPanel {

    /**
     * Creates left panel for account creating pages.
     *
     * @param controller controller for the accountCreateLeftPanel
     */
    public AccountCreateLeftPanel(ActionListener controller) {
        int windowWidth = Globals.WINDOW_WIDTH;
        int windowHeight = Globals.WINDOW_HEIGHT;

        this.setPreferredSize(Globals.LEFT_PANEL_DIMENSION);
        this.setBackground(Globals.BLUE);
        this.setLayout(new BorderLayout());

        // **top panel**
        JPanel logoPanel = new JPanel();
        logoPanel.setPreferredSize(new Dimension((int) (windowWidth / 4), (int) (windowHeight / 3)));
        logoPanel.setBackground(Globals.BLUE);

        // WHITE panel
        JPanel whitePanel = new JPanel();
        whitePanel.setPreferredSize(new Dimension((int) (windowWidth / 5), (int) (windowHeight / 5)));
        whitePanel.setBackground(Globals.WHITE);
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
        bottomPanel.setPreferredSize(new Dimension((int) (windowWidth / 4), (int) (2 * windowHeight / 3)));
        bottomPanel.setLayout(new BorderLayout());

        JPanel cacc = new JPanel();
        cacc.setBackground(Globals.BLUE);
        cacc.setPreferredSize(new Dimension((int) windowWidth / 5, (int) windowHeight / 3));

        JLabel create = new JLabel("CREATE");
        create.setForeground(Globals.WHITE);
        JLabel account = new JLabel("ACCOUNT");
        account.setForeground(Globals.WHITE);

        cacc.add(create);
        cacc.add(account);

        bottomPanel.add(cacc, BorderLayout.SOUTH);

        this.add(logoPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);
        standardiseChildren(logoPanel, true, controller);
        standardiseChildren(bottomPanel, true, controller);
        create.setForeground(Globals.WHITE);
        account.setForeground(Globals.WHITE);

    }
}
