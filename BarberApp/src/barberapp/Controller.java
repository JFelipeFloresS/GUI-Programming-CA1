package barberapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;

/**
 *
 * @author José Felipe Flores da Silva
 * 2019405
 * 
 */

public class Controller implements ActionListener{
    private final DBConnection connection;
    private final View view;
    
    /**
     * Controller constructor.
     * Starts connection and view.
     */
    public Controller() {
        this.connection = new DBConnection();
        this.view = new View(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().contains("cancel booking ")) {
            cancelBooking(Integer.parseInt(e.getActionCommand().substring(15)));
            return;
        }
        
        if (e.getActionCommand().contains("check availability ")) {
            showBarberAvailability(Integer.parseInt(e.getActionCommand().substring(19)));
            return;
        }
        
        if (e.getActionCommand().contains("book ")) {
            bookAppointment(e.getActionCommand().split(" "));
            return;
        }
        
        if (e.getActionCommand().startsWith("review ")) {
            changeScreen(this.view.new submitReview(Integer.parseInt(e.getActionCommand().substring(7))));
            return;
        }
        
        if (e.getActionCommand().contains("star ")) {
            this.view.starPressed(Integer.parseInt(e.getActionCommand().substring(5)));
            return;
        }
        
        if (e.getActionCommand().contains("submit review ")) {
            submitReview(Integer.parseInt(e.getActionCommand().substring(14)));
            return;
        }
        
        if (e.getActionCommand().contains("update review ")) {
            updateReview(Integer.parseInt(e.getActionCommand().substring(14)));
            return;
        }
        
        if (e.getActionCommand().contains("confirm ")) {
            int booking = Integer.parseInt(e.getActionCommand().substring(8));
            this.connection.confirmBooking(booking);
            this.view.setError("BOOKING ACCEPTED");
            return;
        }
        
        if (e.getActionCommand().startsWith("change status ")) {
            changeStatus(Integer.parseInt(e.getActionCommand().substring(14)));
            return;
        }
        
        if (e.getActionCommand().contains("go to change status ")){
            changeScreen(this.view.new barberViewReview(Integer.parseInt(e.getActionCommand().substring(20))));
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
                
            case "view customer bookings":
                showAllCustomerBookings();
                break;
                
            case "view barber bookings":
                showAllBarberBookings();
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
            
            case "back to barber bookings":
                changeScreen(this.view.new barberBookings());
                break;
                
            default:
                System.out.println(e.getActionCommand());
                break;
        }
    }
      
    /**
     * Checks whether the input email is in the valid format.
     * 
     * @param e email to check
     * @return boolean saying whether the email input is in valid format.
     */
    private boolean isValidEmailAddress(String e) {
        /**
        * code to check if string is a valid email address
        * retrieved from https://stackoverflow.com/questions/18463848/how-to-tell-if-a-random-string-is-an-email-address-or-something-else
        */
       Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
       Matcher mat = pattern.matcher(e);
       
       return mat.matches();
    }
    
    /**
     * Checks whether the input password is in the valid format.
     * 
     * @param p password to check
     * @return boolean saying whether the password is in valid format.
     */
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
    
    /**
     * Removes all panels from the frame and adds a new panel.
     * 
     * @param newPanel panel to be added
     */
    private void changeScreen(JPanel newPanel) {
        this.view.getContentPane().removeAll();
        this.view.add(newPanel);
        this.view.validate();
        this.view.repaint();
    }
    
    /**
     * Gets all upcoming bookings for the logged barber.
     * 
     * @return ArrayList of HashMaps with the keys "id", "date", "time", "customer name", "customer phone", "status" 
     */
    public ArrayList<HashMap<String, String>> getBarberUpcomingBookings() {
        return this.connection.getBarberUpcomingBookings();
    }
    
    /**
     * Gets all bookings for the logged barber.
     * 
     * @return ArrayList of HashMaps with the keys "id", "date", "time", "status", "customer name", "phone"
     */
    public ArrayList<HashMap<String, String>> getBarberBookings() {
        return this.connection.getAllBarberBookings();
    }
    
    /**
     * Gets all bookings for the logged customer.
     * 
     * @return ArrayList of HashMaps with the keys "id", "date", "time", "status", "barber name", "phone", "address", "town", "location"
     */
    public ArrayList<HashMap<String, String>> getCustomerBookings() {
        return this.connection.getAllCustomerBookings();
    }
    
    /**
     * Gets next upcoming booking for the logged customer.
     * 
     * @return HashMap with the keys "id", "date", "time", "status", "name", "phone", "address", "town", "location"
     */
    public HashMap<String, String> getNextCustomerBooking() {
        return this.connection.getNextCustomerBooking();
    }
    
    /**
     * Gets locations from all barbers.
     * 
     * @return String array with unique locations 
     */
    public String[] getLocations() {
        return this.connection.getLocations();
    }
    
    /**
     * Gets logged session first name.
     * 
     * @return session first name
     */
    public String getSessionFirstName() {
        return this.connection.getFirstName();
    }
    
    /**
     * Gets all barbers that have the given first name or second name.
     * 
     * @return ArrayList of HashMaps with the keys "id", "name", "phone", "address", "town", "location"
     */
    public ArrayList<HashMap<String, String>> searchForBarberName() {
        return this.connection.searchForBarberName(this.view.getBarberName());
    }
    
    /**
     * Gets all barbers that have the given location.
     * 
     * @return ArrayList of HashMaps with the keys "id", "name", "phone", "address", "town", "location"
     */
    public ArrayList<HashMap<String, String>> searchForBarberLocation() {
        return this.connection.searchForBarberLocation(this.view.getAllLocationsBox().getSelectedItem().toString());
    }
    
    /**
     * Gets logged session ID.
     * 
     * @return session ID
     */
    public int getSessionID() {
        return connection.getID();
    }
    
    /**
     * Checks barber availability against the database.
     * 
     * @param barber barber ID to be checked
     * @param date date to be checked
     * @return boolean array saying whether the barber is available at all slots throughout the date specified
     */
    public boolean[] checkBarberAvailability(int barber, String date) {
        boolean[] isAvailable = new boolean[48];
        ArrayList<HashMap<String, String>> availability = this.connection.getAvailability(barber, date);
        
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
            
            boolean isIn = false;
            for (int j = 0; j < availability.size(); j++) {
                if (availability.get(j).get("date").equals(date) && availability.get(j).get("time").equals(currTime)) {
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
    
    /**
     * Gets one barber information.
     * 
     * @param id barber to get info ID number
     * @return HashMap with the keys "first name", "last name", "address", "town", "location", "phone"
     */
    public HashMap<String, String> getBarber(int id) {
        return this.connection.getBarber(id);
    }
    
    /**
     * Gets a barber availability.
     * 
     * @param id barber ID
     * @param date if not checking for a specific date set to null
     * @return ArrayList of HashMaps with the keys "date", "time"
     */
    public ArrayList<HashMap<String, String>> getbarberAvailability(int id, String date) {
        return this.connection.getAvailability(id, date);
    }
    
    /**
     * Gets a booking info.
     * 
     * @param b booking ID
     * @return "customer name", "customer phone", "barber name", "barber phone", "address", "town", "location"
     */
    public HashMap<String, String> getBookingInfo(int b) {
        return this.connection.getBookingInfo(b);
    }
    
    /**
     * Gets a booking review.
     * 
     * @param b booking ID
     * @return HashMap with the keys "review", "stars"
     */
    public HashMap<String, String> getBookingReview(int b) {
        return this.connection.getBookingReview(b);
    }
    
    /**
     * Updates an appointment status.
     * 
     * @param s booking ID
     */
    private void changeStatus(int s) {
        this.connection.updateStatus(s, this.view.getStatus());
        changeScreen(this.view.new barberBookings());
        this.view.setError("STATUS UPDATED SUCCESSFULLY");
    }
    
    /**
     * Updates currently logged barber for the date picked based on the availability check boxes.
     */
    private void updateBarberAvailability() {
        ArrayList<HashMap<String, String>> currAvailability = this.connection.getAvailability(this.connection.getID(), this.view.getpickedDate());
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
                if (currAvailability.get(j).get("date").equals(this.view.getpickedDate()) && currAvailability.get(j).get("time").equals(currTime)) {
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
        changeScreen(this.view.new availabilityPage());
        this.view.setError("AVAILABILITY UPDATED SUCCESSFULLY");
    }
    
    /**
     * Search for barber based on the name put into the text field barberName and setBarberName.
     */
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
    
    /**
     * Search for barber based on the location selected from the combo box allLocationsBox. setSelectedLocation.
     */
    private void searchBarberByLocation() {
        String selected = this.view.getSelectedLocation();
        changeScreen(this.view.new findABarber());
        this.view.setSelectedLocation(selected);
        this.view.searchForBarber("location");
    }
    
    /**
     * Shows all slots available for a given barber.
     * 
     * @param i barber ID to show available slots
     */
    private void showBarberAvailability(int i) {
        this.view.showBarberAvailability(i);
    }
    
    /**
     * Changes screen to customerBookings.
     */
    private void showAllCustomerBookings() {
        changeScreen(this.view.new customerBookings());
    }
    
    /**
     * Changes screen to barberBookings.
     */
    private void showAllBarberBookings() {
        changeScreen(this.view.new barberBookings());
    }
    
    /**
     * Creates customer account and changes screen to initialPage.
     */
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
                break;
            case "repeated email":
                changeScreen(this.view.new initialPage());
                this.view.setError("<html>An account with this<br /> email address already exists</html>");
                break;
            case "account created":
                changeScreen(this.view.new initialPage());
                this.view.setError("Error adding location to the database");
                break;
            default: 
                changeScreen(this.view.new initialPage());
                this.view.setError("Unexpected error. Please try again.");
                break;
        }

    }
    
    /**
     * Creates barber account and changes screen to initialPage.
     */
    private void createBarberAccount() {
        
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

        if (this.view.getFirstName().length() < 1 || this.view.getLastName().length() < 1 || this.view.getSetLocation().length() < 1 || this.view.getAddress().length() < 1) {
            this.view.setError("Please fill in all fields!");
            return;
        } 
        this.view.setError("");
        switch(this.connection.createAccount(this.view.getEmailAddress(), this.view.getPass(), "barber", this.view.getFirstName(), this.view.getLastName(), this.view.getPhoneNumber(), this.view.getSetLocation(), this.view.getAddress(), this.view.getTown())){
            case "done":
                changeScreen(this.view.new initialPage());
                this.view.setError("Account created successfully");
                break;
            case "repeated email":
                changeScreen(this.view.new initialPage());
                this.view.setError("<html>An account with this<br /> email address already exists</html>");
                break;
            case "account created":
                changeScreen(this.view.new initialPage());
                this.view.setError("Account created successfully, error adding your address");
                break;
            default: 
                changeScreen(this.view.new initialPage());
                this.view.setError("Unexpected error. Please try again.");
                break;
        }
    }
    
    /**
     * Logs into an account based on the email address and password entered. If successful, changes screen to customerMain or barberMain based on the account type.
     */
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
    
    /**
     * Logs out of the session and changes the screen to the initialPage.
     */
    private void logOut() {
        this.connection.logOut();
        changeScreen(this.view.new initialPage());
    }
    
    /**
     * Cancel given booking.
     * 
     * @param booking booking ID to be cancelled
     */
    private void cancelBooking(int booking) {
        this.connection.cancelBooking(booking);
        this.view.setError("Booking cancelled successfully");
        HashMap<String, String> bookingInfo = this.connection.getBookingInfo(booking);
        this.connection.addAvailability(Integer.parseInt(bookingInfo.get("id")), bookingInfo.get("date"), bookingInfo.get("time"));
        if (this.connection.getType().equals("barber")) {
            changeScreen(this.view.new barberMain());
        } else {
            changeScreen(this.view.new customerMain());
        }
    }
    
    /**
     * Submits review for a booking based on the text area review and the integer stars.
     * 
     * @param booking booking ID to submit a review
     */
    private void submitReview(int booking) {
        this.connection.addReview(booking, this.view.getReview(), this.view.getStars());
        changeScreen(this.view.new customerMain());
        this.view.setError("REVIEW SUBMITTED SUCCESSFULLY");
    }
       
    /**
     * Updates review for a booking based on the text area review and the integer stars.
     * 
     * @param booking booking ID to be updated
     */
    private void updateReview(int booking) {
        this.connection.updateReview(booking, this.view.getReview(), this.view.getStars());
        changeScreen(this.view.new customerMain());
        this.view.setError("REVIEW UPDATED SUCCESSFULLY");
    }
    
    /**
     * Request a booking.
     * 
     * @param bookInfo HashMap with the keys "date", "time", "customer", "barber"
     */
    private void bookAppointment(String[] bookInfo) {
        String bookDate = bookInfo[1];
        String bookTime = bookInfo[2];
        int barberID = Integer.parseInt(bookInfo[3]);
        int customerID = this.connection.getID();
        if (this.connection.getBookingID(bookDate, bookTime, customerID, barberID) == 0) {
            this.connection.createBooking(bookDate, bookTime, barberID);
            this.connection.removeAvailability(barberID, bookDate, bookTime);
            changeScreen(this.view.new customerMain());
            this.view.setError("BOOKING REQUESTED SUCCESSFULLY");
        } else {
            this.view.setError("YOU HAVE ALREADY REQUESTED THIS BOOKING");
        }
    }
    
}
