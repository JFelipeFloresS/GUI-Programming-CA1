package barberapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
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
            try {
                cancelBooking(Integer.parseInt(e.getActionCommand().substring(15)));
                HashMap<String, String> bookingInfo = this.connection.getBookingInfo(Integer.parseInt(e.getActionCommand().substring(15)));
                this.connection.addAvailability(Integer.parseInt(bookingInfo.get("barber")), bookingInfo.get("date"), bookingInfo.get("time"));
                this.view.setError("THE BOOKING WAS CANCELLED");
            } catch (NumberFormatException exc) {
                this.view.setError(exc.getMessage());
            }
            
            return;
        }
        
        if (e.getActionCommand().contains("check availability ")) {
            showBarberAvailability(Integer.parseInt(e.getActionCommand().substring(19)));
            return;
        }
        
        if (e.getActionCommand().contains("book ")) {
            String[] bookInfo = e.getActionCommand().split(" ");
            String bookDate = bookInfo[1];
            String bookTime = bookInfo[2];
            int barberID = Integer.parseInt(bookInfo[3]);
            int customerID = this.connection.getID();
            if (this.connection.getBookingID(bookDate, bookTime, customerID, barberID) == 0) {
                this.connection.createBooking(bookDate, bookTime, customerID, barberID);
                this.connection.removeAvailability(barberID, bookDate, bookTime);
                this.view.setError("BOOKING REQUESTED SUCCESSFULLY");
            } else {
                this.view.setError("YOU HAVE ALREADY REQUESTED THIS BOOKING");
            }
            return;
        }
        
        if (e.getActionCommand().contains("confirm ")) {
            int booking = Integer.parseInt(e.getActionCommand().substring(8));
            this.connection.confirmBooking(booking);
            this.view.setError("BOOKING ACCEPTED");
            return;
        }
        
        switch(e.getActionCommand()) {
            case "create new account":
                changeScreen(this.view.new createChoice());
                break;
                
            case "log in":
                logIn();
                break;
                
            case "log out":
                logOut();
                break;
                
            case "go to create barber":
                changeScreen(this.view.new createBarber());
                break;
                
            case "create barber":
                createBarberAccount();
                break;
                
            case "go to create customer":
                changeScreen(this.view.new createCustomer());
                break;
                
            case "go to review":
                System.out.println("show review page");
                //this.changeScreen(new customerReview(this));
                break;
                
            case "view customer bookings":
                showAllCustomerBookings();
                break;
                
            case "view barber bookings":
                showAllBarberBookings();
                break;
                
            case "customer booking cancel":
                System.out.println("cancel booking");
                break;
                
            case "create customer":
                createCustomerAccount();
                break;
                
            case "back to initial page":
                changeScreen(this.view.new initialPage());
                break;
                
            case "search barber name":
                searchBarberByName();
                break;
                
            case "search barber location":
                searchBarberByLocation();
                break;
                
            case "go to set availability":
                changeScreen(this.view.new availabilityPage());
                break;
                
            case "select date":
                this.view.setPickedDate();
                break;
                
            case "enter barber availability":
                updateBarberAvailability();
                break;
                
            case "back to main barber":
                changeScreen(this.view.new barberMain());
                break;
                
            case "back to main customer":
                changeScreen(this.view.new customerMain());
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
    
    public ArrayList<String[]> getBarberBookings() {
        return this.connection.getAllBarberBookings(this.connection.getID());
    }
    
    public ArrayList<String[]> getCustomerBookings() {
        return this.connection.getAllCustomerBookings(this.connection.getID());
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
    
    public ArrayList<String[]> searchForBarberName() {
        return this.connection.searchForBarberName(this.view.getBarberName());
    }
    
    public ArrayList<String[]> searchForBarberLocation() {
        return this.connection.searchForBarberLocation(this.view.getAllLocationsBox().getSelectedItem().toString());
    }
    
    public int getSessionID() {
        return connection.getID();
    }
    
    public boolean[] checkBarberAvailability(int barber, String date) {
        boolean[] isAvailable = new boolean[48];
        ArrayList<String[]> availability = this.connection.getAvailability(barber, date);
        
        int h = 0;
        String currTime;
        boolean isHalf = false;
        String m = ":00";
        for (int i = 0; i < isAvailable.length; i++) {
            String addZero = "";
            if (h<10) {
                addZero = "0";
            }
            currTime = addZero + String.valueOf(h) + m + ":00";
            String[] dateTime = {date, currTime};
            boolean isIn = false;
            for (int j = 0; j < availability.size(); j++) {
                if (availability.get(j)[0].equals(date) && availability.get(j)[1].equals(currTime)) {
                    isIn = true;
                }
            }
            isAvailable[i] = isIn;
            
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
    
    public String[] getBarber(int id) {
        return this.connection.getBarber(id);
    }
    
    public ArrayList<String[]> getbarberAvailability(int id, String date) {
        return this.connection.getAvailability(id, date);
    }
    
    private void updateBarberAvailability() {
        ArrayList<String[]> currAvailability = this.connection.getAvailability(this.connection.getID(), this.view.getpickedDate());
        HashMap<String, Boolean> availability = this.view.getAvailableCheckBoxSelection();
        int h = 0;
        String m = ":00";
        boolean isHalf = false;
        String currTime;
        for (int i = 0; i < 48; i++) {
            if (h<10) {
                currTime = "0";
            } else {
                currTime = "";
            }
            currTime += String.valueOf(h) + m + ":00";
            boolean isInNew = availability.get(currTime);
            boolean isInOld = false;
            for (int j = 0; j < currAvailability.size(); j++) {
                if (currAvailability.get(j)[0].equals(this.view.getpickedDate()) && currAvailability.get(j)[1].equals(currTime)) {
                    isInOld = true;
                }
            }

            if (isInOld) {
                if (!isInNew) {
                    this.connection.removeAvailability(this.connection.getID(), this.view.getpickedDate(), currTime);
                }
            } else {
                if (isInNew) {
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
    }
    
    private void searchBarberByName() {
        if (this.view.getBarberName().length() < 1) {
            this.view.setError("Please enter a name to search for");
        } else {
            this.view.setError("");
            String n = this.view.getBarberName();
            changeScreen(this.view.new findABarber());
            this.view.setBarberName(n);
            this.view.searchForBarber("name");
        }
    }
    
    private void searchBarberByLocation() {
        changeScreen(this.view.new findABarber());
        this.view.searchForBarber("location");
    }
    
    private void showBarberAvailability(int i) {
        this.view.showBarberAvailability(i);
    }
    
    private void showAllCustomerBookings() {
        changeScreen(this.view.new customerBookings());
    }
    
    private void showAllBarberBookings() {
        changeScreen(this.view.new barberBookings());
    }
    
    private void createCustomerAccount() {
        if (isValidEmailAddress(this.view.getEmailAddress())) {
            this.view.setError("");
        } else {
            this.view.setError("Please insert a valid email address");
            return;
        }
        if (this.view.getPass().length() < 8) {
            this.view.setError("Password too short");
            return;
        } else if (!isValidPassword(this.view.getPass())) {
            this.view.setError("<html>Password must contain a digit, <br />a lowercase and an uppercase letter</html>");
            return;
        } else if (!this.view.getPass().equals(this.view.getConfirmPass())) {
            this.view.setError("Password and confirmation don't match");
            return;
        }

        this.view.setError("");

        if (this.view.getFirstName().length() < 1 || this.view.getLastName().length() < 1) {
            this.view.setError("Please fill in all fields!");
            return;
        } 
        this.view.setError("");
        switch(this.connection.createAccount(this.view.getEmailAddress(), this.view.getPass(), "customer", this.view.getFirstName(), this.view.getLastName(), this.view.getPhoneNumber(), null, null, null)){
            case "done":
                changeScreen(this.view.new initialPage());
                this.view.setError("Account created successfully");
            case "repeated email":
                this.view.setError("<html>An account with this<br /> email address already exists</html>");
                break;
            case "account created":
                this.view.setError("Error adding location to the database");
                break;
            default: 
                this.view.setError("Unexpected error. Please try again.");
                break;
        }

    }
    
    private void createBarberAccount() {
        
        if (!isValidEmailAddress(this.view.getEmailAddress())) {
            this.view.setError("Please insert a valid email address");
            return;
        } else {
            this.view.setError("");
        }
        if (this.view.getPass().length() < 8) {
            this.view.setError("Password too short");
            return;
        } else if (!isValidPassword(this.view.getPass())) {
            this.view.setError("<html>Password must contain a digit, <br />a lowercase and an uppercase letter</html>");
            return;
        } else if (!this.view.getPass().equals(this.view.getConfirmPass())) {
            this.view.setError("Password and confirmation don't match");
            return;
        }

        this.view.setError("");

        if (this.view.getFirstName().length() < 1 || this.view.getLastName().length() < 1 || this.view.getSetLocation().length() < 1 || this.view.getAddress().length() < 1) {
            this.view.setError("Please fill in all fields!");
            return;
        } 
        this.view.setError("");
        switch(this.connection.createAccount(this.view.getEmailAddress(), this.view.getPass(), "barber", this.view.getFirstName(), this.view.getLastName(), this.view.getPhoneNumber(), this.view.getSetLocation(), this.view.getAddress(), this.view.getTown())){
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
    }
    
    private void logIn() {
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
    }
    
    private void logOut() {
        this.connection.logOut();
        changeScreen(this.view.new initialPage());
    }
    
    private void cancelBooking(int booking) {
        try {
            this.connection.cancelBooking(booking);
            this.view.setError("Booking cancelled successfully");
        } catch (Exception except) {
            this.view.setError("<html>Error:<br />" + except.getMessage() + "</html>");
        }
        
    }
}
