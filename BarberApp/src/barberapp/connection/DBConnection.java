package barberapp.connection;

import barberapp.main.Controller;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author José Felipe Flores da Silva 2019405
 *
 */
public class DBConnection {

    // state global variables
    private String dbServer;
    private String user;
    private String password;
    private String success;
    private Session session;
    private int id;
    private final Controller controller;

    /**
     * Initialise variables and sets session to null.
     *
     * @param controller controller that initialises the connection
     */
    public DBConnection(Controller controller) {
        initialise();
        this.session = null;
        this.controller = controller;
    }

    /**
     * sets the server, the user and the password for the database
     */
    private void initialise() {
        this.dbServer = "jdbc:mysql://apontejaj.com:3306/Felipe_2019405?useSSL=false";
        this.user = "Felipe_2019405";
        this.password = "2019405";
    }

    /**
     * @return session's first name
     */
    public String getFirstName() {
        return this.session.getFirstName();
    }

    /**
     * @return session's last name
     */
    public String getLastName() {
        return this.session.getLastName();
    }

    /**
     * @return session's type (barber or customer)
     */
    public String getType() {
        return this.session.getType();
    }

    /**
     * @return session's ID
     */
    public int getID() {
        return this.session.getID();
    }

    /**
     * Gets locations from all barbers.
     *
     * @return Array list with unique locations
     */
    public ArrayList<String> getLocations() {
        ArrayList<String> locations = new ArrayList<>();

        String query = "SELECT DISTINCT Location FROM Barber_Location ORDER BY Location";

        // open connection
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password); // create statement
                Statement stmt = conn.createStatement(); // execute query
                ResultSet rs = stmt.executeQuery(query)) { // creates result set

            // put id and email into array list
            while (rs.next()) {
                if (!locations.contains(rs.getString("Location"))) {
                    locations.add(rs.getString("Location"));
                }
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }

        return locations;
    }

    /**
     * Gets one barber information.
     *
     * @param id - barber to get info ID number
     * @return <code>HashMap</code> with the keys: <br> "first name", "last name", "address",
     * "town", "location", "phone"
     */
    public HashMap<String, String> getBarber(int id) {

        HashMap<String, String> barber = new HashMap<>();

        String query = "SELECT First_Name, Last_Name, Address, Town, Location, Phone FROM Accounts, Barber_Location WHERE Accounts.Account_ID=" + id + " AND Barber_Location.Account_ID=" + id + ";";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                barber.put("first name", rs.getString("First_Name"));
                barber.put("last name", rs.getString("Last_Name"));
                barber.put("address", rs.getString("Address"));
                barber.put("town", rs.getString("Town"));
                barber.put("location", rs.getString("Location"));
                barber.put("phone", rs.getString("Phone"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return barber;
    }

    /**
     * Gets one customer information.
     *
     * @param id - customer to get info ID number
     * @return <code>HashMap</code> with the keys: <br> "first name", "last name", "phone"
     */
    public HashMap<String, String> getCustomer(int id) {
        HashMap<String, String> customer = new HashMap<>();

        String query = "SELECT First_Name, Last_Name, Phone FROM Accounts WHERE Account_ID=" + id + ";";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                customer.put("first name", rs.getString("First_Name"));
                customer.put("last name", rs.getString("Last_Name"));
                customer.put("phone", rs.getString("Phone"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return customer;
    }

    /**
     * Gets next upcoming booking for the logged customer.
     *
     * @return <code>HashMap</code> with the keys: <br> "id", "date", "time", "status", "name",
     * "phone", "address", "town", "location"
     */
    public HashMap<String, String> getNextCustomerBooking() {
        HashMap<String, String> customerBookings = new HashMap<>();

        String query = "SELECT Booking_ID, Barber, Booking_Date, Booking_Time, Booking_Status, Viewed FROM Bookings WHERE Customer=" + this.session.getID() + " AND (Booking_Status='upcoming' OR Booking_Status='requested') ORDER BY Booking_Date, Booking_Time LIMIT 1;";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                HashMap<String, String> barber = getBarber(rs.getInt("Barber"));

                customerBookings.put("id", rs.getString("Booking_ID"));
                customerBookings.put("date", rs.getString("Booking_Date"));
                customerBookings.put("time", rs.getString("Booking_Time").substring(0, 5));
                customerBookings.put("status", rs.getString("Booking_Status"));
                customerBookings.put("name", barber.get("first name") + " " + barber.get("last name"));
                customerBookings.put("phone", barber.get("phone"));
                customerBookings.put("address", barber.get("address"));
                customerBookings.put("town", barber.get("town"));
                customerBookings.put("location", barber.get("location"));
                customerBookings.put("viewed", barber.get("Viewed"));

                if (rs.getString("Viewed").contains("once") && rs.getString("Viewed").toLowerCase().contains("barber")) {
                    // if the booking confirmation hadn't been seen by the customer, alert him and update the seen status
                    if (rs.getString("Viewed").toLowerCase().contains("accepted")) {
                        JOptionPane.showMessageDialog(this.controller.view, "Your appointment has been confirmed by the barber!");
                        updateBookingViewed(rs.getInt("Booking_ID"), "accepted by barber");
                    }
                }

            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return customerBookings;
    }

    /**
     * Gets all upcoming bookings for the logged barber
     *
     * @return <code>ArrayList</code> of <code>HashMap</code> with the keys: <br> "id", "date", "time",
     * "customer name", "customer phone", "status"
     */
    public ArrayList<HashMap<String, String>> getBarberUpcomingBookings() {
        ArrayList<HashMap<String, String>> upcoming = new ArrayList<>();
        HashMap<String, String> currCustomer;
        String currDate, currTime, currStatus, currViewed;
        int currID;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime today = LocalDateTime.now();

        /**
         * gets bookings that haven't been cancelled and will occur later than
         * the time the barber opened their home page
         */
        String bookingQ = "SELECT * FROM Bookings WHERE Barber=" + this.session.getID() + " AND (Booking_Status='requested' OR Booking_Status='upcoming') AND Booking_Date >='" + String.valueOf(dtf.format(today)) + "' ORDER BY Booking_Date, Booking_Time;";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(bookingQ);) {

            int c = 0;
            while (rs.next()) {
                upcoming.add(new HashMap<>());
                currID = rs.getInt("Booking_ID");
                currCustomer = getCustomer(rs.getInt("Customer"));
                currDate = rs.getString("Booking_Date");
                currTime = rs.getString("Booking_Time").substring(0, 5);
                currStatus = rs.getString("Booking_Status");
                currViewed = rs.getString("Viewed");

                upcoming.get(c).put("date", currDate); // date
                upcoming.get(c).put("time", currTime); // time
                upcoming.get(c).put("id", String.valueOf(currID)); // booking ID
                upcoming.get(c).put("customer name", currCustomer.get("first name") + " " + currCustomer.get("last name")); // customer name
                upcoming.get(c).put("customer phone", currCustomer.get("phone")); // customer phone
                upcoming.get(c).put("status", currStatus); // booking status
                upcoming.get(c).put("viewed", currViewed); // booking status

                if (currViewed.toLowerCase().contains("customer") && currViewed.toLowerCase().contains("once")) {
                    /**
                     * if the requested hadn't been seen by the barber, alert
                     * them and update the seen status
                     */
                    if (currViewed.contains("requested")) {
                        JOptionPane.showMessageDialog(this.controller.view, "A new booking was requested!");
                        updateBookingViewed(currID, "requested by customer");
                    }
                }

                c++;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return upcoming;
    }

    /**
     * Gets all bookings for the logged barber.
     *
     * @return <code>ArrayList</code> of <code>HashMap</code> with the keys: <br> "id", "date", "time",
     * "status", "customer name", "phone"
     */
    public ArrayList<HashMap<String, String>> getAllBarberBookings() {
        ArrayList<HashMap<String, String>> all = new ArrayList<>();

        String query = "SELECT * FROM Bookings WHERE Barber=" + this.session.getID() + " ORDER BY Booking_Date DESC, Booking_Time DESC;";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            int c = 0;
            while (rs.next()) {
                String currID = rs.getString("Booking_ID");
                String currDate = rs.getString("Booking_Date");
                String currTime = rs.getString("Booking_Time");
                String currStatus = rs.getString("Booking_Status");
                String currViewed = rs.getString("Viewed");
                HashMap<String, String> currCustomer = getCustomer(rs.getInt("Customer"));

                all.add(new HashMap<>());

                all.get(c).put("id", currID);
                all.get(c).put("date", currDate);
                all.get(c).put("time", currTime);
                all.get(c).put("status", currStatus);
                all.get(c).put("viewed", currViewed);
                all.get(c).put("customer name", currCustomer.get("first name") + " " + currCustomer.get("last name"));
                all.get(c).put("phone", currCustomer.get("phone"));

                if (currViewed.toLowerCase().contains("customer") && currViewed.toLowerCase().contains("once")) {
                    /**
                     * alerts the barber of any updates on requests, cancelling
                     * or review if they hadn't seen the updates
                     */
                    String cust = "Your appointment with " + currCustomer.get("first name") + " " + currCustomer.get("last name") + " on " + currDate + " at " + currTime + " has been";
                    if (currViewed.toLowerCase().contains("requested")) {
                        JOptionPane.showMessageDialog(this.controller.view, cust + " requested.");
                        updateBookingViewed(Integer.parseInt(currID), "requested by customer");
                    } else if (currViewed.toLowerCase().contains("cancelled")) {
                        JOptionPane.showMessageDialog(this.controller.view, cust + " cancelled.");
                        updateBookingViewed(Integer.parseInt(currID), "cancelled by customer");
                    } else if (currViewed.toLowerCase().contains("reviewed")) {
                        JOptionPane.showMessageDialog(this.controller.view, cust + " reviewed.");
                        updateBookingViewed(Integer.parseInt(currID), "reviewed by customer");
                    }
                }

                c++;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return all;
    }

    /**
     * Gets all bookings for the logged customer.
     *
     * @return <code>ArrayList</code> of <code>HashMap</code> with the keys: <br> "id", "date", "time",
     * "status", "barber name", "phone", "address", "town", "location"
     */
    public ArrayList<HashMap<String, String>> getAllCustomerBookings() {
        ArrayList<HashMap<String, String>> all = new ArrayList<>();

        String query = "SELECT * FROM Bookings WHERE Customer=" + this.session.getID() + " ORDER BY Booking_Date DESC, Booking_Time DESC;";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            int c = 0;
            while (rs.next()) {
                String currID = rs.getString("Booking_ID");
                String currDate = rs.getString("Booking_Date");
                String currTime = rs.getString("Booking_Time");
                String currStatus = rs.getString("Booking_Status");
                String currViewed = rs.getString("Viewed");
                HashMap<String, String> currBarber = getBarber(rs.getInt("Barber"));

                all.add(new HashMap<>());

                all.get(c).put("id", currID);
                all.get(c).put("date", currDate);
                all.get(c).put("time", currTime);
                all.get(c).put("status", currStatus);
                all.get(c).put("currViewed", currViewed);
                all.get(c).put("barber name", currBarber.get("first name") + " " + currBarber.get("last name"));
                all.get(c).put("phone", currBarber.get("phone"));
                all.get(c).put("address", currBarber.get("address"));
                all.get(c).put("town", currBarber.get("town"));
                all.get(c).put("location", currBarber.get("location"));

                if (currViewed.toLowerCase().contains("barber") && currViewed.toLowerCase().contains("once")) {
                    /**
                     * alert the customer of any updates
                     */
                    String b = "Your appointment with " + currBarber.get("first name") + " " + currBarber.get("last name") + " on " + currDate + " at " + currTime + " has been";
                    if (currViewed.toLowerCase().contains("cancelled")) {
                        JOptionPane.showMessageDialog(this.controller.view, b + " cancelled.");
                        updateBookingViewed(Integer.parseInt(currID), "cancelled by barber");
                    } else if (currViewed.toLowerCase().contains("accepted")) {
                        JOptionPane.showMessageDialog(this.controller.view, b + " confirmed.");
                        updateBookingViewed(Integer.parseInt(currID), "accepted by barber");
                    }
                }

                c++;
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return all;
    }

    /**
     * Inserts a new review into the database.
     *
     * @param id - booking ID
     * @param review - review written by customer
     * @param stars - number of stars given by customer
     */
    public void addReview(int id, String review, int stars) {

        String query = "INSERT INTO Booking_Review(Booking_ID, Review, Stars) VALUES(?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                PreparedStatement stmt = conn.prepareStatement(query);) { // creates prepared statement

            /**
             * sets the variables for the prepared statement
             */
            stmt.setInt(1, id);
            stmt.setString(2, review);
            stmt.setInt(3, stars);

            stmt.execute(); // executes the prepared statement

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }
    }

    /**
     * Update a review previously inserted into the database.
     *
     * @param id - booking UD
     * @param review - new review written by customer
     * @param stars - new number of stars given by customer
     */
    public void updateReview(int id, String review, int stars) {

        String query = "UPDATE Booking_Review SET Review='" + review + "', Stars=" + stars + " WHERE Booking_ID=" + id + ";";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();) {

            stmt.execute(query);

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }

    /**
     * Cancels an appointment.
     *
     * @param id - booking ID to be cancelled
     */
    public void cancelBooking(int id) {

        String statusQ = "UPDATE Bookings SET Booking_Status=? WHERE Booking_ID=?;";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                PreparedStatement stmt = conn.prepareStatement(statusQ);) { // creates prepared statement

            /**
             * sets the prepared statement variables
             */
            stmt.setString(1, "cancelled");
            stmt.setInt(2, id);

            stmt.executeUpdate(); // executes the update from the prepared statement

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }
    }

    /**
     * Inserts new available slot into the database.
     *
     * @param id - barber ID
     * @param date - date of available slot
     * @param time - time of available slot
     */
    public void addAvailability(int id, String date, String time) {

        String query = "INSERT INTO Barber_Availability(Account_ID, Available_Date, Available_Time) VALUES(" + id + ", '" + date + "', '" + time + "');";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();) {

            stmt.execute(query);

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }
    }

    /**
     * Deletes availability slot from the database.
     *
     * @param id - barber ID
     * @param date - date of unavailable slot
     * @param time - time of unavailable slot
     */
    public void removeAvailability(int id, String date, String time) {

        String query = "DELETE FROM Barber_Availability WHERE Account_ID=" + id + " AND Available_Date='" + date + "' AND Available_Time='" + time + "';";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();) {

            stmt.execute(query);

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }

    /**
     * Updates an appointment status.
     *
     * @param id - booking ID
     * @param status - new status
     */
    public void updateStatus(int id, String status) {

        String query = "UPDATE Bookings SET Booking_Status='" + status.toLowerCase() + "' WHERE Booking_ID=" + id + ";";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();) {

            stmt.executeUpdate(query);

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }
    }

    /**
     * Updates viewed column.
     *
     * @param id appointment to update
     * @param viewed new viewed to update
     */
    public void updateBookingViewed(int id, String viewed) {

        String query = "UPDATE Bookings SET Viewed=? WHERE Booking_ID=?";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, viewed);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }
    }

    /**
     * Gets a barber availability
     *
     * @param barber - barber ID
     * @param dateToCheck - if not checking for a specific date set to null
     * @return <code>ArrayList</code> of <code>HashMaps</code> with the keys: <br> "date", "time"
     */
    public ArrayList<HashMap<String, String>> getAvailability(int barber, String dateToCheck) {

        ArrayList<HashMap<String, String>> available = new ArrayList<>();

        String query = "SELECT Available_Date, Available_Time FROM Barber_Availability WHERE Account_ID=" + barber;

        /**
         * if date to check is not null, check only that date, otherwise check
         * from today's date
         */
        if (dateToCheck != null) {
            query += " AND Available_Date='" + dateToCheck + "'";
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime today = LocalDateTime.now();
            query += " AND Available_Date >='" + String.valueOf(dtf.format(today)) + "'";
        }

        query += " ORDER BY Available_Date, Available_Time;";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();) {

            int c = 0;
            while (rs.next()) {
                String date = rs.getString("Available_Date");
                String time = rs.getString("Available_Time");

                available.add(new HashMap<>());

                available.get(c).put("date", date);
                available.get(c).put("time", time);

                c++;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return available;
    }

    /**
     * Gets all e-mails from accounts.
     *
     * @return <code>HashMap</code> <br> key: Account ID <br> value: e-mail
     */
    private HashMap<Integer, String> getEmails() {
        HashMap<Integer, String> emails = new HashMap<>(); // initialise a hashmap to store emails

        String query = "SELECT Account_ID, Email FROM Accounts";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password); // open connection
                Statement stmt = conn.createStatement(); // create statement
                ResultSet rs = stmt.executeQuery(query);) {  // execute query

            // put id and email into hashmap
            while (rs.next()) {
                emails.put(rs.getInt("Account_ID"), rs.getString("Email"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return emails;
    }

    /**
     * Starts a new session.
     *
     * @param id user ID to start the session
     */
    private void setSession(int id) {

        String firstName = null, lastName = null, type = null;

        String query = "SELECT First_Name, Last_Name, Type FROM Accounts WHERE Account_ID=" + id + ";";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                firstName = rs.getString("First_Name");
                lastName = rs.getString("Last_Name");
                type = rs.getString("Type");
            }

            rs.close();
            stmt.close();
            conn.close();

            this.session = new Session(id, firstName, lastName, type); // sets this session with the data retrieved from the account on the database

        } catch (SQLException se) {
            handleExceptions(se);
        }

    }

    /**
     * Checks the email and password input in the login page against the
     * database
     *
     * @param user - email input by user
     * @param pass - password input by user
     * @return <code>true</code>: valid <br> <code>false</code>: invalid
     */
    public boolean checkCredentials(String user, String pass) {
        id = -1; // set id to -1

        // if email input by user is in the database return the account id
        this.getEmails().forEach((Integer i, String em) -> {
            if (em.equalsIgnoreCase(user)) {
                id = i;
            }
        });

        // if it isn't in the database credentials are false
        if (id == -1) {
            return false;
        } else {
            return checkPassword(id, pass); // if email is in database, check pasword and return whether it's a match
        }

    }

    /**
     * Logs into the system. Sets the session's id and redirect to setSession()
     *
     * @param email - email to log in with
     */
    public void logIn(String email) {
        id = -1;
        getEmails().forEach((i, e) -> {
            if (e.equalsIgnoreCase(email)) {
                id = i;
            }
        });

        setSession(id);
    }

    /**
     * Sets the session to null.
     */
    public void logOut() {
        this.session = null;
        this.id = -1;
    }

    /**
     * Inserts a new booking into the database.
     *
     * @param date - date of booking
     * @param time - time of booking
     * @param barber - barber ID
     */
    public void createBooking(String date, String time, int barber) {

        String query = "INSERT INTO Bookings(Booking_Date, Booking_Time, Booking_Status, Customer, Barber, Viewed) VALUES('" + date + "', '" + time + "', 'requested', " + this.getID() + ", " + barber + ", 'requested by customer once');";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();) {

            stmt.execute(query);

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }
    }

    /**
     * Confirms a requested appointment
     *
     * @param id - booking ID
     */
    public void confirmBooking(int id) {

        String query = "UPDATE Bookings SET Booking_Status='upcoming', Viewed='confirmed by barber once' WHERE Booking_ID=" + id + ";";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();) {

            stmt.executeUpdate(query);

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }
    }

    /**
     * Gets a booking ID
     *
     * @param date - booking date
     * @param time - booking time
     * @param customer - customer ID
     * @param barber - barber ID
     * @return booking ID
     */
    public int getBookingID(String date, String time, int customer, int barber) {
        int booking = 0;

        String query = "SELECT Booking_ID FROM Bookings WHERE (Booking_Date='" + date + "' AND Booking_Time='" + time + "' AND Booking_Status!='cancelled' AND Customer=" + customer + " AND Barber=" + barber + ");";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                booking = rs.getInt("Booking_ID");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return booking;
    }

    /**
     * Gets a booking review.
     *
     * @param b - booking ID
     * @return <code>HashMap</code> with the keys: <br> "review", "stars"
     */
    public HashMap<String, String> getBookingReview(int b) {

        HashMap<String, String> review = new HashMap<>();

        String query = "SELECT * FROM Booking_Review WHERE Booking_ID=" + b + ";";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                review.put("review", rs.getString("Review"));
                review.put("stars", rs.getString("Stars"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return review;
    }

    /**
     * Gets a booking info.
     *
     * @param id - booking ID
     * @return <code>HashMap</code> with the keys: <br> "date", "time", "status", "customer id", "customer name",
     * "customer phone", "barber id", "barber name", "barber phone", "address", "town",
     * "location"
     */
    public HashMap<String, String> getBookingInfo(int id) {

        HashMap<String, String> info = null;

        String query = "SELECT * FROM Bookings WHERE Booking_ID=" + id + ";";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            info = new HashMap<>();

            while (rs.next()) {
                HashMap<String, String> customer = getCustomer(rs.getInt("Customer"));
                HashMap<String, String> barber = getBarber(rs.getInt("Barber"));

                info.put("date", rs.getString("Booking_Date"));
                info.put("time", rs.getString("Booking_Time"));
                info.put("status", rs.getString("Booking_Status"));
                info.put("customer id", rs.getString("Customer"));
                info.put("customer name", customer.get("first name") + " " + customer.get("last name"));
                info.put("customer phone", customer.get("phone"));
                info.put("barber id", rs.getString("Barber"));
                info.put("barber name", barber.get("first name") + " " + barber.get("last name"));
                info.put("barber phone", barber.get("phone"));
                info.put("address", barber.get("address"));
                info.put("town", barber.get("town"));
                info.put("location", barber.get("location"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return info;
    }

    /**
     * Gets all barbers that have the given first name or second name.
     *
     * @param search - name to look for
     * @return <code>ArrayList</code> of <code>HashMap</code> with the keys: <br> "id", "name", "phone",
     * "address", "town", "location"
     */
    public ArrayList<HashMap<String, String>> searchForBarberName(String search) {

        ArrayList<HashMap<String, String>> results = new ArrayList<>();
        String[] fullName = null;
        String name1 = null;
        String name2 = null;
        search = search.toLowerCase();

        if (search.trim().contains(" ")) { // if user searches for two names, breaks the search into two
            fullName = search.split(" ");
            name1 = fullName[0];
            name2 = fullName[1];
        }

        String query;
        /**
         * if there's more than one word in the search, check for both words on
         * both first name and last name fields
         */
        if (fullName != null) {
            query = "SELECT * FROM Accounts WHERE Type='barber' AND (lower(First_Name)='" + name1 + "' OR lower(Last_Name)='" + name1 + "' OR lower(First_Name)='" + name2 + "' OR lower(Last_Name)='" + name2 + "')";
        } else {
            query = "SELECT * FROM Accounts WHERE Type='barber' AND (lower(First_Name)='" + search + "' OR lower(Last_Name)='" + search + "')";
        }

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            int c = 0;
            while (rs.next()) {
                HashMap<String, String> bLocation = getBarber(rs.getInt("Account_ID"));

                String i = rs.getString("Account_ID");
                String fn = rs.getString("First_Name");
                String ln = rs.getString("Last_Name");
                String p = rs.getString("Phone");

                results.add(new HashMap<>());

                results.get(c).put("id", i);
                results.get(c).put("name", fn + " " + ln);
                results.get(c).put("phone", p);
                results.get(c).put("address", bLocation.get("address"));
                results.get(c).put("town", bLocation.get("town"));
                results.get(c).put("location", bLocation.get("location"));

                c++;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return results;
    }

    /**
     * Gets all barbers that have the given location.
     *
     * @param l - location to search for
     * @return <code>ArrayList</code> of <code>HashMap</code> with the keys: <br> "id", "name", "phone",
     * "address", "town", "location"
     */
    public ArrayList<HashMap<String, String>> searchForBarberLocation(String l) {

        ArrayList<HashMap<String, String>> results = new ArrayList<>();

        String query = "SELECT * FROM Barber_Location WHERE Barber_Location.Location='" + l + "'";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            int c = 0;
            while (rs.next()) {

                HashMap<String, String> ba = getBarber(rs.getInt("Account_ID"));

                String i = rs.getString("Account_ID");
                String a = rs.getString("Address");
                String t = rs.getString("Town");
                String lo = rs.getString("Location");

                results.add(new HashMap<>());

                results.get(c).put("id", i);
                results.get(c).put("address", a);
                results.get(c).put("town", t);
                results.get(c).put("location", lo);
                results.get(c).put("name", ba.get("first name") + " " + ba.get("last name"));
                results.get(c).put("phone", ba.get("phone"));

                c++;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return results;
    }

    /**
     * Inserts new account into the database.
     *
     * @param email - e-mail address
     * @param pass - password
     * @param type - account type (barber or customer)
     * @param firstName - first name
     * @param lastName - last name
     * @param phone - phone number
     * @param location - location (D1, D2, D3...)
     * @param address - full address
     * @param town - town
     * @return message to customer
     */
    public String createAccount(String email, String pass, String type, String firstName, String lastName, String phone, String location, String address, String town) {
        success = null; // set string success to null
        id = -1;

        // when creating account if the email is already in the database return message repeated email
        this.getEmails().forEach((i, em) -> {
            if (em.equals(email)) {
                success = "repeated email";
            }
        });

        if (success != null && success.equals("repeated email")) { // if there's an account with the given email address, return an error message
            return success;
        }

        String salt = createSalt(); // creates a secure random salt
        String p = generateHash(pass, salt); // generates the hash using the salt previously created

        // creates new account inserting email into accounts
        String accountQ = "INSERT INTO Accounts(Email, First_Name, Last_Name, Pass, Type, Phone) VALUES(?, ?, ?, ?, ?, ?);";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);) {

            PreparedStatement stmt = conn.prepareStatement(accountQ);
            stmt.setString(1, email);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, p);
            stmt.setString(5, type);
            stmt.setString(6, phone);

            stmt.execute();
            success = "account created";
            stmt.close();

            // gets account id using the email address
            this.getEmails().forEach((i, em) -> {
                if (em.equals(email)) {
                    id = i;
                }
            });

            String saltQ = "INSERT INTO salt(Account_ID, salt) VALUES(?, ?)";
            stmt = conn.prepareStatement(saltQ);
            stmt.setInt(1, id);
            stmt.setString(2, salt);

            stmt.execute(); // inserts salt into the database
            success = "password salted and hashed";
            stmt.close();

            if (type.equals("barber")) {
                // if account is for a barber, insert the location into the database
                String locationQ = "INSERT INTO Barber_Location(Account_ID, Address, Town, Location) VALUES (?, ?, ?, ?);";
                stmt = conn.prepareStatement(locationQ);
                stmt.setInt(1, id);
                stmt.setString(2, address);
                stmt.setString(3, town);
                stmt.setString(4, location);

                stmt.execute();
                success = "done";
            } else {
                success = "done";
            }

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return success;
    }

    /**
     * Gets how many accounts there are.
     *
     * @return number of accounts
     */
    public int getAccountsCount() {

        int i = -1;
        String query = "SELECT DISTINCT COUNT(*) AS c FROM Accounts WHERE Type!='admin';";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                i = rs.getInt("c");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return i;
    }

    /**
     * Gets how many barbers there are.
     *
     * @return number of barbers
     */
    public int getBarbersCount() {

        int i = -1;
        String query = "SELECT DISTINCT COUNT(*) AS c FROM Accounts WHERE Type='barber';";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                i = rs.getInt("c");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return i;
    }

    /**
     * Gets how many customers there are.
     *
     * @return number of customers
     */
    public int getCustomersCount() {
        int i = -1;
        String query = "SELECT DISTINCT COUNT(*) AS c FROM Accounts WHERE Type='customer';";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                i = rs.getInt("c");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return i;
    }

    /**
     * Gets how many appointments there are.
     *
     * @return number of appointments
     */
    public int getTotalAppointmentsCount() {
        int i = -1;
        String query = "SELECT DISTINCT COUNT(*) AS c FROM Bookings;";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                i = rs.getInt("c");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return i;
    }

    /**
     * Gets how many appointments of a specific status there are.
     *
     * @param status status of appointment to get the number of
     * @return number of appointments with the given status
     */
    public int getAppointmentsCount(String status) {
        int i = -1;
        String query = "SELECT DISTINCT COUNT(*) AS c FROM Bookings WHERE Booking_Status='" + status + "';";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                i = rs.getInt("c");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return i;
    }

    /**
     * Handles SQL Exceptions.
     *
     * @param se - SQLExecption
     */
    private void handleExceptions(SQLException se) {
        System.out.println("SQL Exception:");

        // Loop through the SQL Exceptions
        while (se != null) {
            System.out.println("State  : " + se.getSQLState());
            System.out.println("Message: " + se.getMessage());
            System.out.println("Error  : " + se.getErrorCode());

            se = se.getNextException();
        }
    }

    /**
     * Generates hashed password.
     *
     * @param data String input by user as password
     * @param salt salt created for the given account
     * @return hashed and salted password
     */
    public String generateHash(String data, String salt) {

        String hashText = "";

        try {

            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            byte[] hash = digest.digest((data + salt).getBytes());

            BigInteger num = new BigInteger(1, hash);

            hashText = num.toString();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hashText;
    }

    /**
     * Generates a secure random salt.
     *
     * @return salt
     */
    public String createSalt() {
        byte[] bytes = new byte[32];

        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);

        BigInteger num = new BigInteger(1, bytes);

        String saltText = num.toString();

        return saltText;
    }

    /**
     * Checks if the password input is valid.
     *
     * @param id Account_ID
     * @param pass password input
     * @return <code>true</code>: valid password <br> <code>false</code>: invalid password
     */
    public boolean checkPassword(int id, String pass) {

        String sql = "SELECT salt FROM salt WHERE Account_ID=" + id + ";";

        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password)) {
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            String salt = "";
            while (rs.next()) {
                salt = rs.getString("salt");
            }

            rs.close();
            stmt.close();

            String inputPass = generateHash(pass, salt);

            String passQ = "SELECT Pass FROM Accounts WHERE Account_ID=" + id + ";";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(passQ);

            String DBPass = "";
            while (rs.next()) {
                DBPass = rs.getString("pass");
            }

            rs.close();
            stmt.close();
            conn.close();

            return inputPass.equals(DBPass); // returns whether the password input matches the password in the DB

        } catch (SQLException se) {
            handleExceptions(se);
        }

        return false;
    }

    /**
     * Used this to update the accounts that were created before i salted or hashed passwords.
     */
    public void updatePasswords() {
        
        ArrayList<String[]> ids = new ArrayList<>();
        String sql = "SELECT Account_ID, Pass FROM Accounts";
        
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String[] line = {rs.getString("Account_ID"), rs.getString("Pass")};
                ids.add(line); // gets all ids and passwords
            }
            
        } catch (SQLException se) {
            handleExceptions(se);
        }

        for (int i = 0; i < ids.size(); i++) {
            updateP(ids.get(i)); // updates each one of them
        }
    }

    /**
     * updates password with salt.
     *
     * @param m <code>String[0]</code>: Account_ID <br> <code>String[1]</code>: old password
     */
    public void updateP(String[] m) {
        
        int num = Integer.parseInt(m[0]);
        String oldPass = m[1];

        System.out.println("ID: " + num);
        System.out.println("Old pass: " + oldPass);

        String insertSalt = "SELECT salt FROM salt WHERE Account_ID=?";
        
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password)) {
            
            PreparedStatement stmt = conn.prepareStatement(insertSalt);
            stmt.setInt(1, num);

            ResultSet rs = stmt.executeQuery();

            String salt = "";
            while (rs.next()) {
                salt = rs.getString("salt"); // gets the account's salt
            }
            System.out.println("Salt: " + salt);

            stmt.close();

            String newPass = generateHash(oldPass, salt); // hashes the password w salt

            System.out.println("New pass: " + newPass);

            String update = "UPDATE Accounts SET Pass=? WHERE Account_ID=?";
            stmt = conn.prepareStatement(update);
            stmt.setString(1, newPass);
            stmt.setInt(2, num);

            stmt.executeUpdate(); // updates the password 

            stmt.close();

            conn.close();

        } catch (SQLException se) {
            handleExceptions(se);
        }
    }

}
