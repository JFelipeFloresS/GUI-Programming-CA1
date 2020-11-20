/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp;

import static barberapp.View.standardiseChildren;
import java.awt.BorderLayout;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class SubmitReview extends JPanel {
    /**
         * Creates a page for customers to write a review for an appointment.
         * 
         * @param controller controller for SubmitReview
         * @param bookingID booking to be reviewed
         */
        public SubmitReview(Controller controller, int bookingID) {
            this.setLayout(new BorderLayout(10, 0));
            this.setBackground(Globals.WHITE);
            
            JPanel centerSubmit = new JPanel();
            centerSubmit.setLayout(new BorderLayout(10, 10));
            
            JPanel padding = new JPanel();
            padding.setPreferredSize(Globals.paddingY3);
            
            JPanel mainPanel = new JPanel();
            mainPanel.setBorder(Globals.border(Globals.DARKBLUE, 2));
            mainPanel.setLayout(new BorderLayout(5, 10));
            
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BorderLayout(10, 10));
            
            HashMap<String, String> info = controller.getBookingInfo(bookingID);
            
            infoPanel.add(new JLabel("<html>Barber: " + info.get("barber name") + " " + info.get("barber last name") + "<br />"
                    + info.get("date") + "<br />"
                    + info.get("time").substring(0, 5)
                    + "</html>"), BorderLayout.WEST);
            
            infoPanel.add(new JLabel("<html>Booking ID: " + bookingID + "<br />"
                    + info.get("address") + "<br />"
                    + info.get("town") + " - " + info.get("location") + "<br />"
                    + "Phone: " + info.get("barber phone")
                    + "</html>"), BorderLayout.EAST);
            
            JPanel reviewPanel = new JPanel();
            reviewPanel.setLayout(new BorderLayout(10, 10));
            
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());
            
            JPanel starPanel = new JPanel();
            
            Image selectedStar = new Stars().selectedStar();
            Image unselectedStar = new Stars().unselectedStar();
            
            Globals.star1 = new JButton();
            Globals.star1.setIcon(new ImageIcon(unselectedStar));
            Globals.star1.setActionCommand("star 1");
            
            Globals.star2 = new JButton();
            Globals.star2.setIcon(new ImageIcon(unselectedStar));
            Globals.star2.setActionCommand("star 2");
            
            Globals.star3 = new JButton();
            Globals.star3.setIcon(new ImageIcon(unselectedStar));
            Globals.star3.setActionCommand("star 3");
            
            Globals.star4 = new JButton();
            Globals.star4.setIcon(new ImageIcon(unselectedStar));
            Globals.star4.setActionCommand("star 4");
            
            Globals.star5 = new JButton();
            Globals.star5.setIcon(new ImageIcon(unselectedStar));
            Globals.star5.setActionCommand("star 5");
            
            HashMap<String, String> previousReview =  controller.getBookingReview(bookingID);
            if (!previousReview.isEmpty()) {
                switch (previousReview.get("stars")) {
                    case "5":
                        Globals.star5.setIcon(new ImageIcon(selectedStar));
                    case "4":
                        Globals.star4.setIcon(new ImageIcon(selectedStar));
                    case "3":
                        Globals.star3.setIcon(new ImageIcon(selectedStar));
                    case "2":
                        Globals.star2.setIcon(new ImageIcon(selectedStar));
                    case "1":
                        Globals.star1.setIcon(new ImageIcon(selectedStar));
                }
                
                Globals.stars = Integer.parseInt(previousReview.get("stars"));
            }
            
            starPanel.add(Globals.star1);
            starPanel.add(Globals.star2);
            starPanel.add(Globals.star3);
            starPanel.add(Globals.star4);
            starPanel.add(Globals.star5);
            
            topPanel.add(new JLabel("REVIEW:"), BorderLayout.WEST);
            topPanel.add(starPanel, BorderLayout.CENTER);
            
            JPanel centerPanel = new JPanel();
            Globals.review = new JTextArea(7, 20);
            if (!previousReview.isEmpty()) {
                Globals.review.setText(previousReview.get("review"));
            }
            
            centerPanel.add(Globals.review);
            
            JPanel bottomPanel = new JPanel();
            JButton submit = new JButton("SUBMIT");
            if (previousReview.isEmpty()) {
                submit.setActionCommand("submit review " + bookingID);
            } else {
                submit.setActionCommand("update review " + bookingID);
            }
            bottomPanel.add(submit);
            
            reviewPanel.add(topPanel, BorderLayout.NORTH);
            reviewPanel.add(centerPanel, BorderLayout.CENTER);
            reviewPanel.add(bottomPanel, BorderLayout.SOUTH);
            
            mainPanel.add(infoPanel, BorderLayout.NORTH);
            mainPanel.add(reviewPanel, BorderLayout.CENTER);
            
            JPanel p2 = new JPanel();
            p2.setPreferredSize(Globals.paddingY1);
            
            centerSubmit.add(padding, BorderLayout.NORTH);
            centerSubmit.add(mainPanel, BorderLayout.CENTER);
            centerSubmit.add(p2, BorderLayout.SOUTH);
            
            JPanel rightSubmit = new JPanel();
            rightSubmit.setLayout(new BorderLayout(10, 50));
            
            JButton back = new JButton("BACK");
            back.setActionCommand("back to main customer");
            
            JButton logOut = new JButton("LOG OUT");
            logOut.setActionCommand("log out");
            
            rightSubmit.add(back, BorderLayout.NORTH);
            rightSubmit.add(logOut, BorderLayout.SOUTH);
            
            this.add(new LoggedLeftPanel(controller), BorderLayout.WEST);
            this.add(centerSubmit, BorderLayout.CENTER);
            this.add(rightSubmit, BorderLayout.EAST);
            
            standardiseChildren(centerSubmit, true, controller);
            standardiseChildren(rightSubmit, true, controller);
            standardiseChildren(starPanel, false, controller);
        }
}
