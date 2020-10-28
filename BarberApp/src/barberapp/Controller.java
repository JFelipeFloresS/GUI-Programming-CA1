package barberapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class Controller implements ActionListener{
    private DBConnection connection;
    private View view;
    
    public Controller() {
        this.connection = new DBConnection(this);
        this.view = new View(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().contains("cancel booking ")) {
            int cancelID = Integer.parseInt(e.getActionCommand().substring(15));
            try {
                this.connection.cancelBooking(cancelID);
                this.view.setError("Booking cancelled successfully");
            } catch (Exception except) {
                this.view.setError("<html>Error:<br />" + except.getMessage() + "</html>");
            }
            return;
        }
        
        switch(e.getActionCommand()) {
            case "create new account":
                changeScreen(this.view.new createChoice());
                break;
            case "log in":
                if (this.connection.checkCredentials(this.view.getEmailAddress(), this.view.getPass())) {
                    this.connection.logIn(this.view.getEmailAddress());
                    if (this.connection.getType().equals("customer")) {
                        changeScreen(this.view.new customerMain());
                    } else {
                        changeScreen(this.view.new barberMain());
                    }
                } else {
                    this.view.setError("<html>Email or password incorrect, please try again!<br />Don't have an account? Create one now!</html>");
                }
                break;
            case "log out":
                this.connection.logOut();
                changeScreen(this.view.new initialPage());
                break;
            case "go to create barber":
                changeScreen(this.view.new createBarber());
                break;
            case "create barber":
                
                if (!isValidEmailAddress(this.view.getEmailAddress())) {
                    this.view.setError("Please insert a valid email address");
                    break;
                } else {
                    this.view.setError("");
                }
                if (this.view.getPass().length() < 8) {
                    this.view.setError("Password too short");
                    break;
                } else if (!isValidPassword(this.view.getPass())) {
                    this.view.setError("<html>Password must contain a digit, <br />a lowercase and an uppercase letter</html>");
                    break;
                } else if (!this.view.getPass().equals(this.view.getConfirmPass())) {
                    this.view.setError("Password and confirmation don't match");
                    break;
                }
                
                this.view.setError("");
                
                if (this.view.getFirstName().length() < 1 || this.view.getLastName().length() < 1 || this.view.getSetLocation().length() < 1 || this.view.getAddress().length() < 1) {
                    this.view.setError("Please fill in all fields!");
                    break;
                } 
                this.view.setError("");
                switch(this.connection.createAccount(this.view.getEmailAddress(), this.view.getPass(), "barber", this.view.getFirstName(), this.view.getLastName(), this.view.getSetLocation(), this.view.getAddress(), this.view.getTown())){
                    case "done":
                        changeScreen(this.view.new initialPage());
                        this.view.setError("Account created successfully");
                    case "repeated email":
                        this.view.setError("<html>An account with this<br /> email address already exists</html>");
                        break;
                    case "account created":
                        this.view.setError("Account created successfully, error adding your address");
                        break;
                    default: 
                        this.view.setError("Unexpected error. Please try again.");
                        break;
                }
                break;
            case "go to create customer":
                changeScreen(this.view.new createCustomer());
                break;
            case "go to review":
                System.out.println("show review page");
                //this.changeScreen(new customerReview(this));
                break;
            case "view customer bookings":
                System.out.println("show all bookings");
                //this.changeScreen(new customerBookings(this));
                break;
            case "customer booking cancel":
                System.out.println("cancel booking");
                break;
            case "create customer":
                
                if (isValidEmailAddress(this.view.getEmailAddress())) {
                    this.view.setError("");
                } else {
                    this.view.setError("Please insert a valid email address");
                    break;
                }
                if (this.view.getPass().length() < 8) {
                    this.view.setError("Password too short");
                    break;
                } else if (!isValidPassword(this.view.getPass())) {
                    this.view.setError("<html>Password must contain a digit, <br />a lowercase and an uppercase letter</html>");
                    break;
                } else if (!this.view.getPass().equals(this.view.getConfirmPass())) {
                    this.view.setError("Password and confirmation don't match");
                    break;
                }
                
                this.view.setError("");
                
                if (this.view.getFirstName().length() < 1 || this.view.getLastName().length() < 1) {
                    this.view.setError("Please fill in all fields!");
                    break;
                } 
                this.view.setError("");
                switch(this.connection.createAccount(this.view.getEmailAddress(), this.view.getPass(), "customer", this.view.getFirstName(), this.view.getLastName(), null, null, null)){
                    case "done":
                        changeScreen(this.view.new initialPage());
                        this.view.setError("Account created successfully");
                    case "repeated email":
                        this.view.setError("<html>An account with this<br /> email address already exists</html>");
                        break;
                    case "1 = email":
                        this.view.setError("Error creating password");
                        break;
                    case "2 = password":
                        this.view.setError("Error inserting names to database");
                        break;
                    case "3 = name":
                        this.view.setError("Error setting account type");
                        break;
                    case "4 = type":
                        this.view.setError("Error adding location to the database");
                        break;
                    default: 
                        this.view.setError("Unexpected error. Please try again.");
                        break;
                }
                
                break;
            case "back to initial page":
                changeScreen(this.view.new initialPage());
                break;
            case "search barber name":
                if (this.view.getBarberName().length() < 1) {
                    this.view.setError("Please enter a name to search for");
                } else {
                    System.out.println("Search for " + this.view.getBarberName());
                }
                break;
            case "search barber location":
                System.out.println("Show all barbers in " + this.view.getAllLocationsBox().getSelectedItem());
                break;
            case "go to set availability":
                changeScreen(this.view.new availabilityPage());
                break;
            case "select date":
                this.view.setPickedDate();
                break;
            case "enter barber availability":
                boolean[] availability = this.view.getAvailableCheckBoxSelection();
                HashMap<String, String> currAvailability = this.connection.getAvailability(this.connection.getID(), this.view.getpickedDate());
                int h = 0;
                String m = ":00";
                boolean isHalf = false;
                String currTime;
                for (int i = 0; i < availability.length; i++) {
                    if (h<10) {
                        currTime = "0";
                    } else {
                        currTime = "";
                    }
                    currTime += String.valueOf(h) + m;
                    
                    if (currAvailability.containsValue(currTime + ":00")) {
                        if (!availability[i]) {
                            this.connection.removeAvailability(this.connection.getID(), this.view.getpickedDate(), currTime);
                        }
                    } else {
                        if (availability[i]) {
                            this.connection.addAvailability(this.connection.getID(), this.view.getpickedDate(), currTime);
                        }
                    }
                    
                    if (isHalf) {
                        m = ":00";
                        h++;
                    } else {
                        m = ":30";
                    }
                    isHalf = !isHalf;
                }
                break;
            case "back to main barber":
                changeScreen(this.view.new barberMain());
                break;
            default:
                System.out.println(e.getActionCommand());
                break;
        }
    }
        
    private boolean isValidEmailAddress(String e) {
        /**
        * code to check if string is a valid email address
        * retrieved from https://stackoverflow.com/questions/18463848/how-to-tell-if-a-random-string-is-an-email-address-or-something-else
        */
       Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
       Matcher mat = pattern.matcher(e);
       
       return mat.matches();
    }
    
    private boolean isValidPassword(String p) {
        /**
         * manipulating regex
         * retrieved from https://www.geeksforgeeks.org/check-if-a-string-contains-uppercase-lowercase-special-characters-and-numeric-values/#:~:text=Traverse%20the%20string%20character%20by,it%20is%20a%20lowercase%20letter.
         */
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher mat = pattern.matcher(p);
        
        return mat.matches();
    }
    
    private void changeScreen(JPanel newPanel) {
        this.view.getContentPane().removeAll();
        this.view.add(newPanel);
        this.view.validate();
        this.view.repaint();
    }
    
    public ArrayList<String[]> getBarberUpcomingBookings() {
        return this.connection.getBarberUpcomingBookings();
    }
    
    public HashMap<String, String> getNextCustomerBooking() {
        return this.connection.getNextCustomerBooking();
    }
    
    public String[] getLocations() {
        return this.connection.getLocations();
    }
    
    public String getSessionFirstName() {
        return this.connection.getFirstName();
    }
    
    public int getSessionID() {
        return connection.getID();
    }
    
    public boolean[] checkBarberAvailability(int barber, String date) {
        boolean[] isAvailable = new boolean[48];
        HashMap<String, String> availability = this.connection.getAvailability(barber, date);
        
        int h = 0;
        String currTime;
        boolean isHalf = false;
        String m = ":00";
        for (int i = 0; i < isAvailable.length; i++) {
            currTime = String.valueOf(h) + m;
            isAvailable[i] = availability.containsValue(currTime);
            
            isHalf = !isHalf;
            if (isHalf) {
                m = ":30";
            } else {
                m = ":00";
                h++;
            }
        }
        return isAvailable;
    }
}
