package barberapp.main;

import barberapp.connection.DBConnection;
import barberapp.views.AvailabilityPage;
import barberapp.views.AdminMain;
import barberapp.views.BarberMain;
import barberapp.views.BarberViewReview;
import barberapp.views.BarberBookings;
import barberapp.views.CustomerBookings;
import barberapp.views.CreateBarber;
import barberapp.views.CreateChoice;
import barberapp.views.CreateCustomer;
import barberapp.views.CustomerMain;
import barberapp.views.FindABarber;
import barberapp.views.InitialPage;
import barberapp.views.SubmitReview;
import com.mysql.cj.util.StringUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author José Felipe Flores da Silva 2019405
 *
 */
public class Controller implements ActionListener {

    private final DBConnection connection;
    public final View view;

    /**
     * Controller constructor. Starts connection and view.
     */
    public Controller() {
        this.connection = new DBConnection(this);
        this.view = new View(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //System.out.println(e.getActionCommand());
        if (e.getActionCommand().contains("cancel booking ")) {
            int booking = Integer.parseInt(e.getActionCommand().substring(15));
            cancelBooking(booking);
            return;
        }

        if (e.getActionCommand().contains("check availability ")) {
            int barberID = Integer.parseInt(e.getActionCommand().substring(19));
            showBarberAvailability(barberID);
            return;
        }

        if (e.getActionCommand().contains("book ")) {
            String[] appointmentInfo = e.getActionCommand().split(" ");
            bookAppointment(appointmentInfo);
            return;
        }

        if (e.getActionCommand().startsWith("review ")) {
            int booking = Integer.parseInt(e.getActionCommand().substring(7));
            changeScreen(new SubmitReview(this, booking));
            return;
        }

        if (e.getActionCommand().contains("star ")) {
            int starPressed = Integer.parseInt(e.getActionCommand().substring(5));
            SubmitReview.starPressed(starPressed);
            return;
        }

        if (e.getActionCommand().contains("submit review ")) {
            int booking = Integer.parseInt(e.getActionCommand().substring(14));
            submitReview(booking);
            return;
        }

        if (e.getActionCommand().contains("update review ")) {
            int booking = Integer.parseInt(e.getActionCommand().substring(14));
            updateReview(booking);
            return;
        }

        if (e.getActionCommand().contains("confirm ")) {
            int booking = Integer.parseInt(e.getActionCommand().substring(8));
            confirmBooking(booking);
            return;
        }

        if (e.getActionCommand().contains("go to change status ")) {
            int booking = Integer.parseInt(e.getActionCommand().substring(20));
            changeScreen(new BarberViewReview(this, booking));
            return;
        }

        if (e.getActionCommand().contains("complete booking ")) {
            int booking = Integer.parseInt(e.getActionCommand().substring(17));
            completeBooking(booking);
            return;
        }

        if (e.getActionCommand().contains("no show booking ")) {
            int booking = Integer.parseInt(e.getActionCommand().substring(16));
            noShowBooking(booking);
            return;
        }

        switch (e.getActionCommand()) {
            case "create new account":
                changeScreen(new CreateChoice(this));
                break;

            case "log in":
                logIn();
                break;

            case "log out":
                logOut();
                break;

            case "go to create barber":
                changeScreen(new CreateBarber(this));
                break;

            case "create barber":
                createBarberAccount();
                break;

            case "go to create customer":
                changeScreen(new CreateCustomer(this));
                break;

            case "view customer bookings":
                changeScreen(new CustomerBookings(this));
                break;

            case "view barber bookings":
                changeScreen(new BarberBookings(this));
                break;

            case "create customer":
                createCustomerAccount();
                break;

            case "back to initial page":
                changeScreen(new InitialPage(this));
                break;

            case "search barber name main":
                String barberNameFromMain = CustomerMain.getBarberName();
                searchBarberByName(barberNameFromMain);
                break;

            case "search barber name find":
                String barberNameFromFind = FindABarber.getBarberName();
                searchBarberByName(barberNameFromFind);
                break;

            case "search barber location main":
                String locationFromMain = CustomerMain.getSelectedLocation();
                searchBarberByLocation(locationFromMain);
                break;

            case "search barber location find":
                String locationFromFind = FindABarber.getSelectedLocation();
                searchBarberByLocation(locationFromFind);
                break;

            case "go to set availability":
                changeScreen(new AvailabilityPage(this));
                break;

            case "select date":
                AvailabilityPage.setPickedDate(this);
                break;

            case "enter barber availability":
                updateBarberAvailability();
                break;

            case "back to main barber":
                changeScreen(new BarberMain(this));
                break;

            case "back to main customer":
                changeScreen(new CustomerMain(this));
                break;

            case "back to barber bookings":
                changeScreen(new BarberBookings(this));
                break;

            default:
                System.out.println("Invalid button.");
                break;
        }
    }

    /**
     * Checks whether the input email is in the valid format.
     *
     * @param e email to dateToCheck
     * @return <code>true</code>: valid <br> <code>false</code>: invalid
     */
    private boolean isValidEmailAddress(String e) {
        /**
         * code to check if string is a valid email address retrieved from
         * https://stackoverflow.com/questions/18463848/how-to-tell-if-a-random-string-is-an-email-address-or-something-else
         */
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(e);

        return mat.matches();
    }

    /**
     * Checks whether the input password is in the valid format.
     *
     * @param p password to dateToCheck
     * @return <code>true</code>: valid <br> <code>false</code>: invalid
     */
    private boolean isValidPassword(String p) {
        /**
         * manipulating regex retrieved from
         * https://www.geeksforgeeks.org/check-if-a-string-contains-uppercase-lowercase-special-characters-and-numeric-values/#:~:text=Traverse%20the%20string%20character%20by,it%20is%20a%20lowercase%20letter.
         */
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher mat = pattern.matcher(p);

        return mat.matches();
    }

    /**
     * Removes all panels from the frame and adds a new panel.
     *
     * @param newPanel panel to be swapped onto the frame
     */
    public void changeScreen(JPanel newPanel) {
        this.view.getContentPane().removeAll();
        this.view.add(newPanel);
        this.view.validate();
        this.view.repaint();
    }

    /**
     * Gets all upcoming bookings for the logged barber.
     *
     * @return <code>ArrayList</code> of <code>HashMap</code> with the keys:
     * <br> "id", "date", "time", "customer name", "customer phone", "status"
     */
    public ArrayList<HashMap<String, String>> getBarberUpcomingBookings() {
        return this.connection.getBarberUpcomingBookings();
    }

    /**
     * Gets all bookings for the logged barber.
     *
     * @return <code>ArrayList</code> of <code>HashMap</code> with the keys:
     * <br> "id", "date", "time", "status", "customer name", "phone"
     */
    public ArrayList<HashMap<String, String>> getBarberBookings() {
        return this.connection.getAllBarberBookings();
    }

    /**
     * Gets all bookings for the logged customer.
     *
     * @return <code>ArrayList</code> of <code>HashMap</code> with the keys:
     * <br> "id", "date", "time", "status", "barber name", "phone", "address",
     * "town", "location"
     */
    public ArrayList<HashMap<String, String>> getCustomerBookings() {
        return this.connection.getAllCustomerBookings();
    }

    /**
     * Gets next upcoming booking for the logged customer.
     *
     * @return <code>HashMap</code> with the keys: <br> "id", "date", "time",
     * "status", "name", "phone", "address", "town", "location"
     */
    public HashMap<String, String> getNextCustomerBooking() {
        return this.connection.getNextCustomerBooking();
    }

    /**
     * Gets locations from all barbers.
     *
     * @return <code>ArrayList</code> with unique locations
     */
    public ArrayList<String> getLocations() {
        return this.connection.getLocations();
    }

    /**
     * Gets logged session first name.
     *
     * @return current session's first name
     */
    public String getSessionFirstName() {
        return this.connection.getFirstName();
    }

    /**
     * Gets all barbers that have the given first name or second name.
     *
     * @return <code>ArrayList</code> of <code>HashMap</code> with the keys:
     * <br> "id", "name", "phone", "address", "town", "location"
     */
    public ArrayList<HashMap<String, String>> searchForBarberName() {
        if (FindABarber.getBarberName() != null) {
            return this.connection.searchForBarberName(FindABarber.getBarberName());
        } else {
            return this.connection.searchForBarberName(CustomerMain.getBarberName());
        }
    }

    /**
     * Gets all barbers that are in the given location.
     *
     * @return <code>ArrayList</code> of <code>HashMap</code> with the keys:
     * <br> "id", "name", "phone", "address", "town", "location"
     */
    public ArrayList<HashMap<String, String>> searchForBarberLocation() {
        if (FindABarber.getAllLocationsBox() != null) {
            return this.connection.searchForBarberLocation(FindABarber.getAllLocationsBox().getSelectedItem().toString());
        } else {
            return this.connection.searchForBarberLocation(CustomerMain.getAllLocationsBox().getSelectedItem().toString());
        }
    }

    /**
     * Gets logged session ID.
     *
     * @return current session's ID
     */
    public int getSessionID() {
        return connection.getID();
    }

    /**
     * Checks barber availability against the database.
     *
     * @param barber barber ID to be checked
     * @param date date to be checked
     * @return <code>boolean[]</code> containing whether the barber is available
     * at all slots throughout the date specified
     */
    public boolean[] checkBarberAvailability(int barber, String date) {
        boolean[] isAvailable = new boolean[48];
        ArrayList<HashMap<String, String>> availability = this.connection.getAvailability(barber, date);

        /**
         * fixing the hours and minutes of the current time
         */
        int h = 0;
        String currTime;
        boolean isHalf = false;
        String m = ":00";
        for (int i = 0; i < isAvailable.length; i++) {
            String addZero = "";
            if (h < 10) { // adds zero to the beginning of the string if the hour is less than 2 digits long
                addZero = "0";
            }
            currTime = addZero + String.valueOf(h) + m + ":00";

            boolean isIn = false; // boolean to dateToCheck whether the current time is in the database
            for (int j = 0; j < availability.size(); j++) {
                if (availability.get(j).get("date").equals(date) && availability.get(j).get("time").equals(currTime)) {
                    isIn = true; // if the current time is in the array list, it means it's in the database
                }
            }
            isAvailable[i] = isIn; // updates the boolean array

            isHalf = !isHalf;
            if (isHalf) {
                m = ":30"; // if the next loop is half, the minutes will turn into 30
            } else {
                m = ":00"; // otherwise, the minutes will turn into 00 and the hour will increase by 1
                h++;
            }
        }
        return isAvailable;
    }

    /**
     * Gets one barber information.
     *
     * @param id barber to get info ID number
     * @return <code>HashMap</code> with the keys: <br> "first name", "last
     * name", "address", "town", "location", "phone"
     */
    public HashMap<String, String> getBarber(int id) {
        return this.connection.getBarber(id);
    }

    /**
     * Gets a barber availability.
     *
     * @param id barber ID
     * @param date if not checking for a specific date set to null
     * @return <code>ArrayList</code> of <code>HashMap</code> with the keys:
     * <br> "date", "time"
     */
    public ArrayList<HashMap<String, String>> getbarberAvailability(int id, String date) {
        return this.connection.getAvailability(id, date);
    }

    /**
     * Gets a booking info.
     *
     * @param b booking ID
     * @return <code>HashMap</code> with the keys: <br> "customer name",
     * "customer phone", "barber name", "barber phone", "address", "town",
     * "location"
     */
    public HashMap<String, String> getBookingInfo(int b) {
        return this.connection.getBookingInfo(b);
    }

    /**
     * Gets a booking review.
     *
     * @param b booking ID
     * @return <code>HashMap</code> with the keys: <br> "review", "stars"
     */
    public HashMap<String, String> getBookingReview(int b) {
        return this.connection.getBookingReview(b);
    }

    /**
     * Gets how many accounts there are.
     *
     * @return number of accounts
     */
    public int getAccountsCount() {
        return this.connection.getAccountsCount();
    }

    /**
     * Gets how many barbers there are.
     *
     * @return number of barbers
     */
    public int getBarbersCount() {
        return this.connection.getBarbersCount();
    }

    /**
     * Gets how many customers there are.
     *
     * @return number of customers
     */
    public int getCustomersCount() {
        return this.connection.getCustomersCount();
    }

    /**
     * Gets how many appointments there are.
     *
     * @return number of appointments
     */
    public int getTotalAppointmentsCount() {
        return this.connection.getTotalAppointmentsCount();
    }

    /**
     * Gets how many appointments of a specific status there are.
     *
     * @param status status of appointment to get the number of: upcoming,
     * cancelled, completed, no show, requested
     * @return number of appointments with the given status
     */
    public int getAppointmentsCount(String status) {
        return this.connection.getAppointmentsCount(status);
    }

    /**
     * Updates currently logged barber for the date picked based on the
 availability dateToCheck boxes.
     */
    private void updateBarberAvailability() {
        /**
         * retrieving availability from the database and from the check box
         * selection
         */
        ArrayList<HashMap<String, String>> currAvailability = this.connection.getAvailability(this.connection.getID(), AvailabilityPage.getpickedDate());
        HashMap<String, Boolean> availability = AvailabilityPage.getAvailableCheckBoxSelection();

        /**
         * fixing the hours and minutes for the current time
         */
        int h = 0;
        String m = ":00";
        boolean isHalf = false;
        String currTime;
        for (int i = 0; i < 48; i++) {
            if (h < 10) { // adds a zero to the beginning of the string if the hour is less than 2 digits long
                currTime = "0";
            } else {
                currTime = "";
            }

            currTime += String.valueOf(h) + m + ":00";
            boolean isInNew = availability.get(currTime);
            boolean isInPrevious = false;
            for (int j = 0; j < currAvailability.size(); j++) {
                if (currAvailability.get(j).get("date").equals(AvailabilityPage.getpickedDate()) && currAvailability.get(j).get("time").equals(currTime)) {
                    isInPrevious = true; // if it is in the database, it is in previous
                }
            }

            if (isInPrevious && !isInNew) { // if it is in previous and it isn't in new, remove availability
                this.connection.removeAvailability(this.connection.getID(), AvailabilityPage.getpickedDate(), currTime);
            } else if (!isInPrevious && isInNew) { // if it is in new and wasn't in previous, add availability
                this.connection.addAvailability(this.connection.getID(), AvailabilityPage.getpickedDate(), currTime);
            }

            if (isHalf) {
                m = ":00";
                h++;
            } else {
                m = ":30";
            }
            isHalf = !isHalf;
        }
        changeScreen(new AvailabilityPage(this));
        JOptionPane.showMessageDialog(this.view, "Availability updated successfully!");
    }

    /**
     * Search for barber based on the name put into the text field barberName
     * and setBarberName.
     */
    private void searchBarberByName(String name) {
        if (name.length() < 1) {
            JOptionPane.showMessageDialog(this.view, "Please enter a name to search for");
        } else {
            String n = name;
            changeScreen(new FindABarber(this));
            FindABarber.setBarberName(n);
            FindABarber.searchForBarber("name", this);
        }
    }

    /**
     * Search for barber based on the location selected from the combo box
     * allLocationsBox
     *
     * @param selected selected location
     */
    private void searchBarberByLocation(String selected) {
        changeScreen(new FindABarber(this));
        FindABarber.setSelectedLocation(selected);
        FindABarber.searchForBarber("location", this);
    }

    /**
     * Shows all slots available for a given barber.
     *
     * @param barberID barber ID to show available slots
     */
    private void showBarberAvailability(int barberID) {
        FindABarber.showBarberAvailability(barberID, this);
    }

    /**
     * Creates customer account and changes screen to InitialPage.
     */
    private void createCustomerAccount() {
        if (!isValidEmailAddress(CreateCustomer.getEmailAddress())) { // warns wrong email format
            JOptionPane.showMessageDialog(this.view, "Please insert a valid email address", "Invalid email address", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (CreateCustomer.getPass().length() < 8) { // warns password is too short
            JOptionPane.showMessageDialog(this.view, "Your password is too short", "Invalid password", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!isValidPassword(CreateCustomer.getPass())) { // warns invalid password format
            JOptionPane.showMessageDialog(this.view, "Your password must contain a digit, a lowercase and an uppercase letter", "Invalid password", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!CreateCustomer.getPass().equals(CreateCustomer.getConfirmPass())) { // warns no match between password and password confirmation
            JOptionPane.showMessageDialog(this.view, "Your password and confirmation password don't match", "Invalid password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (CreateCustomer.getFirstName().length() < 1 || CreateCustomer.getLastName().length() < 1) { // warns not all fields have been filled in
            JOptionPane.showMessageDialog(this.view, "Please fill in all fields!", "Incomplete fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        switch (this.connection.createAccount(CreateCustomer.getEmailAddress(), CreateCustomer.getPass(), "customer", CreateCustomer.getFirstName(), CreateCustomer.getLastName(), CreateCustomer.getPhoneNumber(), null, null, null)) {
            case "done": // informs account has been created successfully
                changeScreen(new InitialPage(this));
                JOptionPane.showMessageDialog(this.view, "Account created successfully, welcome to find a barber!", "Account created", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "repeated email": // warns there is already an account with that email
                changeScreen(new InitialPage(this));
                JOptionPane.showMessageDialog(this.view, "An account with this email address already exists", "Account already exists", JOptionPane.WARNING_MESSAGE);
                break;
            case "account created": // warns there has been an error adding location to the database, but the account was created successfully
                changeScreen(new InitialPage(this));
                JOptionPane.showMessageDialog(this.view, "Error adding location to the database, account created successfully.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                changeScreen(new InitialPage(this));
                JOptionPane.showMessageDialog(this.view, "Unexpected error. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }

    }

    /**
     * Creates barber account and changes screen to InitialPage.
     */
    private void createBarberAccount() {

        if (!isValidEmailAddress(CreateBarber.getEmailAddress())) { // warns invalid email format
            JOptionPane.showMessageDialog(this.view, "Please insert a valid email address.", "Invalid email address", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (CreateBarber.getPass().length() < 8) { // warns short password
            JOptionPane.showMessageDialog(this.view, "Your password is too short", "Invalid password", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!isValidPassword(CreateBarber.getPass())) { // warns invalid password format
            JOptionPane.showMessageDialog(this.view, "Your password must contain a digit, a lowercase and an uppercase letter", "Invalid password", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!CreateBarber.getPass().equals(CreateBarber.getConfirmPass())) { // warns mismatch between password and password confirmation
            JOptionPane.showMessageDialog(this.view, "Your password and confirmation password don't match", "Invalid password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // warns incomplete fields
        if (CreateBarber.getFirstName().length() < 1 || CreateBarber.getLastName().length() < 1 || CreateBarber.getSetLocation().length() < 1 || CreateBarber.getAddress().length() < 1) {
            JOptionPane.showMessageDialog(this.view, "Please fill in all fields!", "Incomplete fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!isValidD(CreateBarber.getSetLocation())) { // warns incorrect location format
            JOptionPane.showMessageDialog(this.view, "Incorrect location format. The location should be in the format \"D1\", \"D12\"...", "Incorrect format!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        switch (this.connection.createAccount(CreateBarber.getEmailAddress(), CreateBarber.getPass(), "barber", CreateBarber.getFirstName(), CreateBarber.getLastName(), CreateBarber.getPhoneNumber(), CreateBarber.getSetLocation(), CreateBarber.getAddress(), CreateBarber.getTown())) {
            case "done": // informs accounts has been created successfully
                changeScreen(new InitialPage(this));
                JOptionPane.showMessageDialog(this.view, "Account created successfully, welcome to find a barber!", "Account created", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "repeated email": // warns there is an account with the email attempted
                changeScreen(new InitialPage(this));
                JOptionPane.showMessageDialog(this.view, "An account with this email address already exists", "Account already exists", JOptionPane.WARNING_MESSAGE);
                break;
            case "account created": // warns an error adding the location to the database
                changeScreen(new InitialPage(this));
                JOptionPane.showMessageDialog(this.view, "Error adding location to the database, account created successfully.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                changeScreen(new InitialPage(this));
                JOptionPane.showMessageDialog(this.view, "Unexpected error. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    /**
     * Check whether it is a valid location format.
     *
     * @param location input to be checked
     * @return <code>true</code>: valid location <br> <code>false</code>:
     * invalid
     */
    private boolean isValidD(String location) {
        return location.startsWith("D") && StringUtils.isStrictlyNumeric(location.substring(1));
    }

    /**
     * Logs into an account based on the email address and password entered. If
     * successful, changes screen to customerMain or barberMain based on the
     * account type.
     */
    private void logIn() {
        if (this.connection.checkCredentials(InitialPage.getEmailAddress(), InitialPage.getPass())) {
            this.connection.logIn(InitialPage.getEmailAddress());
            switch (this.connection.getType()) {
                case "customer":
                    changeScreen(new CustomerMain(this));
                    break;
                case "barber":
                    changeScreen(new BarberMain(this));
                    break;
                case "admin": // email: admin password: Pass123!
                    changeScreen(new AdminMain(this));
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(this.view, "Email or password incorrect, please try again! Don't have an account? Create one now!", "Email or password incorrect", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Logs out of the session and changes the screen to the InitialPage.
     */
    private void logOut() {
        this.connection.logOut();
        changeScreen(new InitialPage(this));
    }

    /**
     * Confirms a request for a booking.
     * 
     * @param booking booking ID
     */
    public void confirmBooking(int booking) {
        this.connection.confirmBooking(booking);
        changeScreen(new BarberMain(this));
        JOptionPane.showMessageDialog(this.view, "Booking accepted.");
        this.connection.updateBookingViewed(booking, "accepted by barber once");
    }

    /**
     * Cancel given booking.
     *
     * @param booking booking ID to be cancelled
     */
    private void cancelBooking(int booking) {
        int response = JOptionPane.showConfirmDialog(this.view, "Are you sure you want to cancel this appointment?", "Cancel appointment", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            this.connection.cancelBooking(booking);
            JOptionPane.showMessageDialog(this.view, "Booking cancelled successfully");
            this.connection.updateBookingViewed(booking, "cancelled by " + this.connection.getType() + " once");
            HashMap<String, String> bookingInfo = this.connection.getBookingInfo(booking);
            this.connection.addAvailability(Integer.parseInt(bookingInfo.get("barber id")), bookingInfo.get("date"), bookingInfo.get("time"));
            if (this.connection.getType().equals("barber")) {
                changeScreen(new BarberMain(this));
            } else {
                changeScreen(new CustomerMain(this));
            }
        }
    }

    /**
     * Submits review for a booking based on the text area review and the
     * integer stars.
     *
     * @param booking booking ID to submit a review
     */
    private void submitReview(int booking) {
        this.connection.addReview(booking, SubmitReview.getReview(), SubmitReview.getStars());
        this.connection.updateBookingViewed(booking, "reviewed by customer once");
        changeScreen(new CustomerMain(this));
        JOptionPane.showMessageDialog(this.view, "Review submitted successfully");
    }

    /**
     * Updates review for a booking based on the text area review and the
     * integer stars.
     *
     * @param booking booking ID to be updated
     */
    private void updateReview(int booking) {
        this.connection.updateReview(booking, SubmitReview.getReview(), SubmitReview.getStars());
        changeScreen(new CustomerMain(this));
        JOptionPane.showMessageDialog(this.view, "Review updated successfully");
    }

    /**
     * Requests a booking.
     *
     * @param bookInfo bookInfo[0]: "book "<br>bookInfo[1]: booking date<br>bookInfo[2]: booking time<br>bookInfo[3]: barber ID
     */
    private void bookAppointment(String[] bookInfo) {
        String bookDate = bookInfo[1];
        String bookTime = bookInfo[2];
        int barberID = Integer.parseInt(bookInfo[3]);
        int customerID = this.connection.getID();
        int r = JOptionPane.showConfirmDialog(this.view, "Are you sure you want to book an appointment on " + bookDate + " at " + bookTime + "?", "Confirm appointment", JOptionPane.YES_NO_OPTION);

        if (r == JOptionPane.YES_OPTION) { // if confirmed by customer, dateToCheck whether there's an appointment with the date, time, customer and barber
            if (this.connection.getBookingID(bookDate, bookTime, customerID, barberID) == 0) { // creates appointment if there isn't one
                this.connection.createBooking(bookDate, bookTime, barberID);
                this.connection.removeAvailability(barberID, bookDate, bookTime);
                changeScreen(new CustomerMain(this));
                JOptionPane.showMessageDialog(this.view, "Booking requested successfully");
            } else { // warns customer otherwise
                JOptionPane.showMessageDialog(this.view, "You have alread requested this appointment", "Already requested", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Updates an appointment status to "completed".
     *
     * @param id booking ID
     */
    private void completeBooking(int booking) {
        this.connection.updateStatus(booking, "completed");
        this.connection.updateBookingViewed(booking, "completed by barber");
        changeScreen(new BarberBookings(this));
    }

    /**
     * Updates an appointment status to "no show".
     *
     * @param id booking ID
     */
    private void noShowBooking(int booking) {
        this.connection.updateStatus(booking, "no show");
        this.connection.updateBookingViewed(booking, "no show by customer");
        changeScreen(new BarberBookings(this));
    }

    /**
     * Checks whether a date and time is older than the current date and time.
     *
     * @param date string with the date to be checked
     * @param time string with the time to be checked
     * @return boolean: <code>true</code>: old <br> <code>false</code>: future
     */
    public boolean isOld(String date, String time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        try {
            Date today = new SimpleDateFormat("yyyy-MM-dd").parse(df.format(now));
            Date dateToCheck = new SimpleDateFormat("yyyy-MM-dd").parse(date + time);

            if (today.after(dateToCheck)) {
                return true;
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this.view, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }

        return false;
    }

    /**
     * Updates whether it was seen by barber or service provider.
     *
     * @param booking booking ID
     * @param viewed new status
     */
    public void updateBookingViewed(int booking, String viewed) {
        this.connection.updateBookingViewed(booking, viewed);
    }
}
