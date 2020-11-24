/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.views;

import barberapp.main.Controller;
import barberapp.main.Globals;
import barberapp.main.View;
import barberapp.views.LoggedLeftPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class BarberBookings extends JPanel {

    /**
     * Creates a page to show the barber all their bookings.
     *
     * @param controller controller for BarberBookings
     */
    public BarberBookings(Controller controller) {
        this.setLayout(new BorderLayout(15, 0));
        this.setBackground(Globals.WHITE);

        JPanel mainBookings = new JPanel();
        mainBookings.setLayout(new BorderLayout(30, 0));

        // center panel
        JPanel centerBookings = new JPanel();
        centerBookings.setLayout(new BorderLayout(15, 0));

        JPanel allBookings = new JPanel();
        allBookings.setBorder(Globals.border(Globals.DARKBLUE, 2));
        allBookings.setLayout(new BorderLayout(10, 10));

        JPanel bookings = new JPanel();
        ArrayList<HashMap<String, String>> b = controller.getBarberBookings();
        bookings.setLayout(new GridLayout(b.size(), 1, 20, 30));

        if (b.size() > 0) {
            for (int i = 0; i < b.size(); i++) {
                JPanel book = new JPanel();
                book.setBorder(Globals.border(Globals.DARKBLUE, 1));
                book.setBackground(Globals.TEXTFIELDCOLOUR);
                book.setLayout(new BorderLayout());

                book.add(new JLabel("<html>Customer: " + b.get(i).get("customer name") + "<br />"
                        + "Phone: " + b.get(i).get("phone") + "<br />"
                        + b.get(i).get("date") + "<br />"
                        + b.get(i).get("time").substring(0, 5) + "<br />"
                        + "Status: " + b.get(i).get("status").substring(0, 1).toUpperCase() + b.get(i).get("status").substring(1)
                        + "</html>"), BorderLayout.WEST);

                JPanel buttonPanel = new JPanel();
                buttonPanel.setBackground(Globals.TEXTFIELDCOLOUR);

                switch (b.get(i).get("status")) {
                    case "completed":
                        JButton review = new JButton("SEE REVIEW");
                        review.setActionCommand("go to change status " + b.get(i).get("id"));
                        buttonPanel.add(review);
                        break;
                    case "upcoming":
                        if (controller.isOld(b.get(i).get("date"), b.get(i).get("time"))) {
                            buttonPanel.setLayout(new BorderLayout(10, 10));
                            JButton complete = new JButton("COMPLETED");
                            complete.setActionCommand("complete booking " + b.get(i).get("id"));
                            buttonPanel.add(complete, BorderLayout.NORTH);

                            JButton noShow = new JButton("NO SHOW");
                            noShow.setActionCommand("no show booking " + b.get(i).get("id"));
                            buttonPanel.add(noShow, BorderLayout.SOUTH);
                        } else {
                            JButton cancel = new JButton("CANCEL");
                            cancel.setActionCommand("cancel booking " + b.get(i).get("id"));
                            buttonPanel.add(cancel);
                        }
                        break;
                    case "requested":
                        buttonPanel.setLayout(new BorderLayout(10, 10));
                        JButton accept = new JButton("CONFIRM");
                        accept.setActionCommand("confirm " + b.get(i).get("id"));
                        buttonPanel.add(accept, BorderLayout.NORTH);

                        JButton cancel = new JButton("CANCEL");
                        cancel.setActionCommand("cancel booking " + b.get(i).get("id"));
                        buttonPanel.add(cancel, BorderLayout.SOUTH);
                        break;
                    default:
                        break;
                }
                book.add(buttonPanel, BorderLayout.EAST);

                bookings.add(book);
            }
        } else {
            JPanel noBook = new JPanel();
            noBook.add(new JLabel("NO BOOKINGS FOUND"));

            bookings.add(noBook);
        }
        JScrollPane sp = new JScrollPane(bookings);
        View.standardiseChildren(bookings, true, controller);

        allBookings.add(new JLabel("ALL MY BOOKINGS:"), BorderLayout.NORTH);
        allBookings.add(sp);

        JPanel padding = new JPanel();
        padding.setPreferredSize(Globals.PADDING_Y1);

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

        this.add(new LoggedLeftPanel(controller), BorderLayout.WEST);
        this.add(mainBookings);

        View.standardiseChildren(mainBookings, true, controller);
    }
}
