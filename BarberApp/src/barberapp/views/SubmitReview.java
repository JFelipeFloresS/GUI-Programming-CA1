/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.views;

import barberapp.main.Globals;
import barberapp.assets.Images;
import static barberapp.main.View.standardiseChildren;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

    public static JButton star1 = null;
    public static JButton star2 = null;
    public static JButton star3 = null;
    public static JButton star4 = null;
    public static JButton star5 = null;
    public static JTextArea review = null;
    public static int stars = -1;

    /**
     * Creates a page for customers to write a review for an appointment.
     *
     * @param controller controller for SubmitReview
     * @param bookingID booking to be reviewed
     */
    public SubmitReview(barberapp.main.Controller controller, int bookingID) {
        this.setLayout(new BorderLayout(10, 0));
        this.setBackground(Globals.WHITE);

        JPanel centerSubmit = new JPanel();
        centerSubmit.setLayout(new BorderLayout(10, 10));

        JPanel padding = new JPanel();
        padding.setPreferredSize(Globals.PADDING_Y3);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(Globals.border(Globals.DARKBLUE, 2));
        mainPanel.setLayout(new BorderLayout(5, 10));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout(10, 10));

        HashMap<String, String> info = controller.getBookingInfo(bookingID);

        infoPanel.add(new JLabel("<html>Barber: " + info.get("barber name") + "<br />"
                + Globals.formatDateFromSQL(info.get("date")) + "<br />"
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

        Image selectedStar = new Images().selectedStar(30);
        Image unselectedStar = new Images().unselectedStar(30);

        star1 = new JButton();
        star1.setIcon(new ImageIcon(unselectedStar));
        star1.setActionCommand("star 1");

        star2 = new JButton();
        star2.setIcon(new ImageIcon(unselectedStar));
        star2.setActionCommand("star 2");

        star3 = new JButton();
        star3.setIcon(new ImageIcon(unselectedStar));
        star3.setActionCommand("star 3");

        star4 = new JButton();
        star4.setIcon(new ImageIcon(unselectedStar));
        star4.setActionCommand("star 4");

        star5 = new JButton();
        star5.setIcon(new ImageIcon(unselectedStar));
        star5.setActionCommand("star 5");

        HashMap<String, String> previousReview = controller.getBookingReview(bookingID);
        if (!previousReview.isEmpty()) {
            switch (previousReview.get("stars")) {
                case "5":
                    star5.setIcon(new ImageIcon(selectedStar));
                case "4":
                    star4.setIcon(new ImageIcon(selectedStar));
                case "3":
                    star3.setIcon(new ImageIcon(selectedStar));
                case "2":
                    star2.setIcon(new ImageIcon(selectedStar));
                case "1":
                    star1.setIcon(new ImageIcon(selectedStar));
            }

            stars = Integer.parseInt(previousReview.get("stars"));
        }

        starPanel.add(star1);
        starPanel.add(star2);
        starPanel.add(star3);
        starPanel.add(star4);
        starPanel.add(star5);

        topPanel.add(new JLabel("REVIEW:"), BorderLayout.WEST);
        topPanel.add(starPanel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel();
        review = new JTextArea(7, 20);
        if (!previousReview.isEmpty()) {
            review.setText(previousReview.get("review"));
        }
        /**
         * Code to maintain the size of JTextArea retrieved from
         * https://stackoverflow.com/questions/4045357/how-to-stop-wordwrapped-jtextarea-from-resizing-to-fit-large-content
         */
        review.setLineWrap(true);
        review.setWrapStyleWord(true);
        review.setMaximumSize(new Dimension(Globals.WINDOW_WIDTH / 3, 300));
        
        centerPanel.add(review);

        JPanel bottomPanel = new JPanel();
        JButton submit = new JButton("SUBMIT");
        if (previousReview.isEmpty()) {
            submit.setActionCommand("submit review " + bookingID);
        } else {
            submit.setActionCommand("update review " + bookingID);
        }
        bottomPanel.add(submit);
        
        review.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submit.doClick();
                }
            }
        });

        reviewPanel.add(topPanel, BorderLayout.NORTH);
        reviewPanel.add(centerPanel, BorderLayout.CENTER);
        reviewPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(reviewPanel, BorderLayout.CENTER);

        JPanel p2 = new JPanel();
        p2.setPreferredSize(Globals.PADDING_Y1);

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

    /**
     * @return review input into the text field review
     */
    public static String getReview() {
        return review.getText();
    }

    /**
     * @return number of stars given by customer to an appointment
     */
    public static int getStars() {
        return stars;
    }

    /**
     * Sets review inserted by customer to the text area review.
     *
     * @param r review to be displayed
     */
    public static void setReview(String r) {
        review.setText(r);
    }

    /**
     * Sets stars given by customer to an appointment.
     *
     * @param s stars to be displayed
     */
    public static void setStars(int s) {
        stars = s;
    }

    /**
     * Changes the image of the stars and the integer stars based on what star
     * has been pressed.
     *
     * @param n star number
     */
    public static void starPressed(int n) {
        Image selectedStar = new Images().selectedStar(30);
        Image unselectedStar = new Images().unselectedStar(30);
        switch (n) {
            case 5:
                star5.setIcon(new ImageIcon(selectedStar));
                star4.setIcon(new ImageIcon(selectedStar));
                star3.setIcon(new ImageIcon(selectedStar));
                star2.setIcon(new ImageIcon(selectedStar));
                star1.setIcon(new ImageIcon(selectedStar));
                break;
            case 4:
                star5.setIcon(new ImageIcon(unselectedStar));
                star4.setIcon(new ImageIcon(selectedStar));
                star3.setIcon(new ImageIcon(selectedStar));
                star2.setIcon(new ImageIcon(selectedStar));
                star1.setIcon(new ImageIcon(selectedStar));
                break;
            case 3:
                star5.setIcon(new ImageIcon(unselectedStar));
                star4.setIcon(new ImageIcon(unselectedStar));
                star3.setIcon(new ImageIcon(selectedStar));
                star2.setIcon(new ImageIcon(selectedStar));
                star1.setIcon(new ImageIcon(selectedStar));
                break;
            case 2:
                star5.setIcon(new ImageIcon(unselectedStar));
                star4.setIcon(new ImageIcon(unselectedStar));
                star3.setIcon(new ImageIcon(unselectedStar));
                star2.setIcon(new ImageIcon(selectedStar));
                star1.setIcon(new ImageIcon(selectedStar));
                break;
            case 1:
                star5.setIcon(new ImageIcon(unselectedStar));
                star4.setIcon(new ImageIcon(unselectedStar));
                star3.setIcon(new ImageIcon(unselectedStar));
                star2.setIcon(new ImageIcon(unselectedStar));
                star1.setIcon(new ImageIcon(selectedStar));
                break;
        }

        stars = n;
    }

}
