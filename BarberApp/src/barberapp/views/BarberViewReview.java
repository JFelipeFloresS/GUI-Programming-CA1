/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.views;

import barberapp.main.Controller;
import barberapp.main.Globals;
import barberapp.assets.Images;
import barberapp.main.View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class BarberViewReview extends JPanel {

    /**
     * Creates a page to show the review for an appointment.
     *
     * @param controller controller for BarberViewReview
     * @param bookingID booking ID to be seen
     */
    public BarberViewReview(barberapp.main.Controller controller, int bookingID) {
        this.setLayout(new BorderLayout(10, 0));
        this.setBackground(Globals.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));

        JPanel messagePanel = new JPanel();
        messagePanel.setPreferredSize(Globals.PADDING_Y3);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10, 10));
        centerPanel.setBorder(Globals.border(Globals.DARKBLUE, 2));

        HashMap<String, String> info = controller.getBookingInfo(bookingID);
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout(10, 10));
        top.add(new JLabel("<html>Booking ID: " + bookingID + "<br />"
                + "Customer: " + info.get("customer name") + "<br />"
                + "Phone: " + info.get("customer phone")
                + "</html>"), BorderLayout.WEST);

        top.add(new JLabel("<html>" + info.get("date") + "<br />"
                + info.get("time").substring(0, 5)
                + "</html>"), BorderLayout.EAST);

        top.add(new JLabel("CUSTOMER REVIEW: "), BorderLayout.SOUTH);

        HashMap<String, String> review = controller.getBookingReview(bookingID);
        JPanel center = new JPanel();
        center.setLayout(new BorderLayout(10, 10));

        JPanel starsPanel = new JPanel();
        JLabel star1 = new JLabel();
        JLabel star2 = new JLabel();
        JLabel star3 = new JLabel();
        JLabel star4 = new JLabel();
        JLabel star5 = new JLabel();

        Image selectedStar = new Images().selectedStar(30);
        Image unselectedStar = new Images().unselectedStar(30);
        if (!review.isEmpty()) {
            switch (review.get("stars")) {
                case "1":
                    star1.setIcon(new ImageIcon(selectedStar));
                    star2.setIcon(new ImageIcon(unselectedStar));
                    star3.setIcon(new ImageIcon(unselectedStar));
                    star4.setIcon(new ImageIcon(unselectedStar));
                    star5.setIcon(new ImageIcon(unselectedStar));
                    break;
                case "2":
                    star1.setIcon(new ImageIcon(selectedStar));
                    star2.setIcon(new ImageIcon(selectedStar));
                    star3.setIcon(new ImageIcon(unselectedStar));
                    star4.setIcon(new ImageIcon(unselectedStar));
                    star5.setIcon(new ImageIcon(unselectedStar));
                    break;
                case "3":
                    star1.setIcon(new ImageIcon(selectedStar));
                    star2.setIcon(new ImageIcon(selectedStar));
                    star3.setIcon(new ImageIcon(selectedStar));
                    star4.setIcon(new ImageIcon(unselectedStar));
                    star5.setIcon(new ImageIcon(unselectedStar));
                    break;
                case "4":
                    star1.setIcon(new ImageIcon(selectedStar));
                    star2.setIcon(new ImageIcon(selectedStar));
                    star3.setIcon(new ImageIcon(selectedStar));
                    star4.setIcon(new ImageIcon(selectedStar));
                    star5.setIcon(new ImageIcon(unselectedStar));
                    break;
                case "5":
                    star1.setIcon(new ImageIcon(selectedStar));
                    star2.setIcon(new ImageIcon(selectedStar));
                    star3.setIcon(new ImageIcon(selectedStar));
                    star4.setIcon(new ImageIcon(selectedStar));
                    star5.setIcon(new ImageIcon(selectedStar));
                    break;
                default:
                    break;
            }
        } else {
            star1.setIcon(new ImageIcon(unselectedStar));
            star2.setIcon(new ImageIcon(unselectedStar));
            star3.setIcon(new ImageIcon(unselectedStar));
            star4.setIcon(new ImageIcon(unselectedStar));
            star5.setIcon(new ImageIcon(unselectedStar));
        }

        starsPanel.add(star1);
        starsPanel.add(star2);
        starsPanel.add(star3);
        starsPanel.add(star4);
        starsPanel.add(star5);

        JPanel reviewPanel = new JPanel();

        JLabel r = new JLabel("CUSTOMER HASN'T LEFT A REVIEW YET");
        r.setFont(Globals.BODY_FONT);
        r.setHorizontalTextPosition(SwingConstants.CENTER);

        if (!review.isEmpty()) {
            String wrap = review.get("review");
            if (wrap.length() > 50) {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < ((int) (wrap.length() / 50) + 1); i++) {
                    int ending;
                    if ((i + 1) * 50 > wrap.length()) {
                        ending = wrap.length();
                    } else {
                        ending = (i + 1) * 50;
                    }
                    list.add(wrap.substring(i * 50, ending) + "<br />");
                }
                wrap = "<html>";
                for (int i = 0; i < list.size(); i++) {
                    wrap += list.get(i);
                }
                wrap += "</html>";
            }
            r.setText(wrap);
        }

        JScrollPane sp = new JScrollPane(r);
        sp.setPreferredSize(new Dimension(650, 220));
        reviewPanel.add(sp);

        center.add(starsPanel, BorderLayout.NORTH);
        center.add(reviewPanel, BorderLayout.CENTER);

        centerPanel.add(top, BorderLayout.NORTH);
        centerPanel.add(center, BorderLayout.CENTER);

        JPanel p1 = new JPanel();
        p1.setPreferredSize(Globals.PADDING_Y1);

        mainPanel.add(messagePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(p1, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout(10, 50));

        JButton back = new JButton("BACK");
        back.setActionCommand("back to barber bookings");

        JButton logOut = new JButton("LOG OUT");
        logOut.setActionCommand("log out");

        rightPanel.add(back, BorderLayout.NORTH);
        rightPanel.add(logOut, BorderLayout.SOUTH);

        this.add(new LoggedLeftPanel(controller), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);

        View.standardiseChildren(mainPanel, true, controller);
        View.standardiseChildren(rightPanel, true, controller);
    }
}
