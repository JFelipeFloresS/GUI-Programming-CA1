/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp;

import static barberapp.View.standardiseChildren;
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
public class CustomerBookings extends JPanel {
        /**
         * Creates a view for all bookings for a given customer.
         * 
         * @param controller controller for CustomerBookings
         */
        public CustomerBookings(Controller controller) {
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
            ArrayList<HashMap<String, String>> b = controller.getCustomerBookings();
            bookings.setLayout(new GridLayout(b.size(), 1, 20, 30));
            
            if (b.size()>0) {
                for (int i = 0; i < b.size(); i++) {
                    JPanel book = new JPanel();
                    book.setBorder(Globals.border(Globals.DARKBLUE, 1));
                    book.setLayout(new BorderLayout(10, 0));
                    
                    book.add(new JLabel("<html>Barber: " + b.get(i).get("barber name") + "<br />"
                            + b.get(i).get("address") + "<br />" 
                            + b.get(i).get("town") + " - " + b.get(i).get("location") + "<br />"
                            + "Phone: " + b.get(i).get("phone")
                            + "</html>"), BorderLayout.WEST);
                    book.add(new JLabel("<html>" + b.get(i).get("date") + "<br />"
                            + b.get(i).get("time") + "<br />"
                            + "Status: " + b.get(i).get("status").substring(0, 1).toUpperCase() + b.get(i).get("status").substring(1)
                            + "</html>"), BorderLayout.CENTER);

                    JPanel bPanel = new JPanel();
                    
                    if (b.get(i).get("status").equals("completed")) {
                        JButton review = new JButton("REVIEW");
                        review.setActionCommand("review " + b.get(i).get("id"));
                        bPanel.add(review);
                    } else if (b.get(i).get("status").equals("upcoming")) {
                        JButton cancel = new JButton("CANCEL");
                        cancel.setActionCommand("cancel booking " + b.get(i).get("id"));
                        bPanel.add(cancel);
                    }
                    
                    book.add(bPanel, BorderLayout.EAST);
                    bookings.add(book);
                }
            } else {
                JPanel noBook = new JPanel();
                noBook.add(new JLabel("NO BOOKINGS FOUND"));
                
                bookings.add(noBook);
            }
            JScrollPane sp = new JScrollPane(bookings);
            standardiseChildren(bookings, true, controller);
            
            allBookings.add(new JLabel("ALL MY BOOKINGS:"), BorderLayout.NORTH);
            allBookings.add(sp);
            
            JPanel padding = new JPanel();
            padding.setPreferredSize(Globals.paddingY1);
            
            centerBookings.add(allBookings, BorderLayout.CENTER);
            centerBookings.add(padding, BorderLayout.SOUTH);
            
            //right panel
            JPanel rightBookings = new JPanel();
            rightBookings.setLayout(new BorderLayout(10, 50));
            
            JButton back = new JButton("BACK");
            back.setActionCommand("back to main customer");
            
            JButton logOut = new JButton("LOG OUT");
            logOut.setActionCommand("log out");
            
            rightBookings.add(back, BorderLayout.NORTH);
            rightBookings.add(logOut, BorderLayout.SOUTH);
            
            mainBookings.add(centerBookings, BorderLayout.CENTER);
            mainBookings.add(rightBookings, BorderLayout.EAST);
            
            this.add(new LoggedLeftPanel(controller), BorderLayout.WEST);
            this.add(mainBookings);
            
            standardiseChildren(mainBookings, false, controller);
        }
}
