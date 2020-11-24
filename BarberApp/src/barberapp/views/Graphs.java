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
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class Graphs extends JPanel {

    public Graphs(Controller controller) {
        this.setLayout(new BorderLayout());
        this.setBackground(Globals.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(Globals.WINDOW_WIDTH, (int) (Globals.WINDOW_HEIGHT / 5)));
        topPanel.setBackground(Globals.BLUE);
        topPanel.setForeground(Globals.WHITE);
        topPanel.setBorder(Globals.border(Globals.DARKBLUE, 2));
        topPanel.setLayout(new BorderLayout());

        JLabel l = new JLabel("ADMIN");
        l.setFont(Globals.TITLE_FONT);
        l.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel logPanel = new JPanel();
        logPanel.setBackground(Globals.BLUE);

        JButton logout = new JButton("LOG OUT");
        logout.setActionCommand("log out");

        logPanel.add(logout);

        topPanel.add(l, BorderLayout.CENTER);
        topPanel.add(logPanel, BorderLayout.EAST);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel graph = new JPanel();

        this.add(topPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        standardiseChildren(topPanel, true, controller);
        standardiseChildren(mainPanel, true, controller);
    }
}
