/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class Globals {
    // window size
    public static int windowWidth = 1200;
    public static int windowHeight = 800;
    
    
    // fonts
    public static final Font titleFont = new Font("Tahoma", Font.BOLD, 46);
    public static final Font bodyFont = new Font("Tahoma", Font.PLAIN, 26);
    public static final Font smallFont = new Font("Tahoma", Font.PLAIN, 16);
    public static final Font largeFont = new Font("Tahoma", Font.PLAIN, 36);
    
    /**
     * Creates border.
     * 
     * @param colour colour of border
     * @param width width of border
     * @return border
     */
    public static Border border (Color colour, int width) {
        return BorderFactory.createLineBorder(colour, width);
    }
    
    // dimensions
    public static final Dimension regularButtonDimension = new Dimension((int)windowWidth / 6, (int)windowHeight / 12);
    public static final Dimension loginPanelDimension = new Dimension((int)2 * windowWidth / 3, (int)windowHeight / 4);
    public static final Dimension topPanelDimension = new Dimension(windowWidth, (int)windowHeight / 8);
    public static final Dimension bottomPanelDimension = new Dimension(windowWidth, (int)7 * windowHeight / 8);
    public static final Dimension leftPanelDimension = new Dimension((int)windowWidth / 4, windowHeight);
    public static final Dimension rightPanelDimension = new Dimension((int)3 * windowWidth / 4, windowHeight);
    public static final Dimension paddingY1 = new Dimension(windowWidth, (int)windowHeight / 10);
    public static final Dimension paddingY2 = new Dimension(windowWidth, (int)windowHeight / 8);
    public static final Dimension paddingY3 = new Dimension(windowWidth, (int)windowHeight / 6);
    public static final Dimension paddingY4 = new Dimension(windowWidth, (int)windowHeight / 4);
    public static final Dimension paddingY5 = new Dimension(windowWidth, (int)windowHeight / 3);
    public static final Dimension paddingX1 = new Dimension((int)windowWidth / 10, windowHeight);
    public static final Dimension paddingX2 = new Dimension((int)windowWidth / 8, windowHeight);
    public static final Dimension paddingX3 = new Dimension((int)windowWidth / 6, windowHeight);
    public static final Dimension paddingX4 = new Dimension((int)windowWidth / 4, windowHeight);
    public static final Dimension paddingX5 = new Dimension((int)windowWidth / 3, windowHeight);
    
    // colours
    public static final Color WHITE = new Color(250, 250, 250);
    public static final Color BLUE = new Color(133, 133, 233);
    public static final Color DARKBLUE = new Color(85, 85, 185);
    public static final Color TEXTFIELDCOLOUR = new Color(233, 233, 233);
    public static final Color DEFAULTCOLOUR = new Color(238, 238, 238);
    
    // fields
    public static JTextField emailAddress = null;
    public static JPasswordField password = null;
    public static JPasswordField confirmPass = null;
    public static JTextField firstName = null;
    public static JTextField lastName = null;
    public static JTextField location = null;
    public static JTextField phoneNumber = null;
    public static JTextField address = null;
    public static JTextField town = null;
    public static JTextField barberName = null;
    public static JTextArea review = null;
    public static JButton star1 = null;
    public static JButton star2 = null;
    public static JButton star3 = null;
    public static JButton star4 = null;
    public static JButton star5 = null;
    public static JComboBox allLocationsBox = null;
    public static JComboBox statuses = null;
    public static JCheckBox[] availableCheckBox = null;
    public static int stars = -1;
    public static boolean[] isAvailable = null;
    public static JComboBox[] date = null;
    public static JLabel pickedDate = null;
    public static JButton selectDate = null;
    public static JScrollPane allTimesSP = null;
    public static JPanel allTimes = null;
    public static JButton enterAvailability = null;
    public static JPanel mainTime = null;
    public static JPanel leftFindABarberPanel = null;
    public static JPanel rightFindABarberPanel = null;
    
        
    /**
     * @return combo box with all locations there are barbers in
     */
    public static JComboBox getAllLocationsBox() {
        return allLocationsBox;
    }
    
    /**
     * @return email address input into the text field emailAddress
     */
    public static String getEmailAddress() {
        return emailAddress.getText();
    } 
    
    /**
     * @return password input into the password field password
     */
    public static String getPass() {
        return password.getText();
    }
    
    /**
     * @return password input into the password field confirmPass
     */
    public static String getConfirmPass() {
        return confirmPass.getText();
    }
    
    /**
     * @return first name input into the text field firstName
     */
    public static String getFirstName() {
        return firstName.getText();
    }
    
    /**
     * @return last name input into the text field lastName
     */
    public static String getLastName() {
        return lastName.getText();
    }
    
    /**
     * @return phone input into the text field phoneNumber
     */
    public static String getPhoneNumber() {
        return phoneNumber.getText();
    }
    
    /**
     * @return location input into the text field location
     */
    public static String getSetLocation() {
        return location.getText();
    }
    
    /**
     * @return picked date from the label pickedDate
     */
    public static String getpickedDate() {
        String full = pickedDate.getText();
        String y = full.substring(6);
        String m = full.substring(3, 5);
        String d = full.substring(0, 2);
        
        return y+"-"+m+"-"+d;
    }
    
    /**
     * @return address input into the text field address
     */
    public static String getAddress() {
        return address.getText();
    }
    
    /**
     * @return town input into the text field town
     */
    public static String getTown() {
        return town.getText();
    }
    
    /**
     * @return barber name to be searched from the text field barberName
     */
    public static String getBarberName() {
        return barberName.getText();
    }
    
    /**
     * Gets available check box selection.
     * 
     * @return HashMap with the keys being the slot time and the value being whether the barber is available for that slot
     */
    public static HashMap<String, Boolean> getAvailableCheckBoxSelection() {
        HashMap<String, Boolean> available = new HashMap<>();
        
        for (int i = 0; i < availableCheckBox.length; i++) {
            available.put(availableCheckBox[i].getName() + ":00", availableCheckBox[i].isSelected());
        }
        
        return available;
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
     * @return status chosen from combo box statuses
     */
    public static String getStatus() {
        return statuses.getSelectedItem().toString();
    }
    
    /**
     * @return location selected by user
     */
    public static String getSelectedLocation() {
        return allLocationsBox.getSelectedItem().toString();
    }
    
    /**
     * Sets what location is selected.
     * 
     * @param l location to be selected
     */
    public static void setSelectedLocation(String l) {
        allLocationsBox.setSelectedItem(l);
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
     * Sets the text of the text field barberName.
     * 
     * @param n name to set the text as
     */
    public static void setBarberName(String n) {
        barberName.setText(n);
    }
}
