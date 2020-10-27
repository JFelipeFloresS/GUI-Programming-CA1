package barberapp;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class DBConnection {
    // state global variables
    private String dbServer;
    private String user;
    private String password;
    private String name;
    private String lastName;
    private int id;
    private String success;
    private String type;
    private Controller controller;
    
    public DBConnection(Controller controller) {
        // initialise variables
        this.controller = controller;
        initialise();
    }
    
    private void initialise() {
        this.dbServer = "jdbc:mysql://apontejaj.com:3306/Felipe_2019405?useSSL=false";
        this.user = "Felipe_2019405";
        this.password = "2019405";
    }
    
    public String getFirstName() {
        return this.name;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String[] getLocations() {
        ArrayList<String> locations = new ArrayList<>();
        String[] l = null;
        
        String query = "SELECT Location FROM Barber_Location";
        
        try {
            // open connection
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            
            // create statement
            Statement stmt = conn.createStatement();
            
            // execute query
            ResultSet rs = stmt.executeQuery(query);
            
            // put id and email into hashmap
            while (rs.next()) {
                if (!locations.contains(rs.getString("Location"))) {
                    locations.add(rs.getString("Location"));
                }
            }
        
            rs.close();
            conn.close();
            stmt.close();
            l = new String[locations.size()];
            for (int i = 0; i < l.length; i++) {
                l[i] = locations.get(i);
            }
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
        return l;
    }
    
    public String[] getBarber(int id) {
        String[] barber = new String[3];
        
        try {
            String query = "SELECT First_Name, Address, Town FROM Accounts, Barber_Location WHERE Accounts.Account_ID=" + id + " AND Barber_Location.Account_ID=" + id + ";";
            
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                barber[0] = rs.getString("First_Name");
                barber[1] = rs.getString("Address");
                barber[2] = rs.getString("Town");
            }
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        return barber;
    }
    
    public String getCustomer(int id){
        String customer = null;
        
        try {
            String query = "SELECT First_Name FROM Accounts WHERE Account_ID=" + id + ";";
            
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            customer = rs.getString("FirstName");
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        return customer;
    }
    
    public HashMap<String, String> getNextCustomerBooking() {
        DateTimeFormatter dater = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter timer = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime today = LocalDateTime.now();
        
        // hashmap to store customer's bookings
        ArrayList<String[]> customerBookings = new ArrayList<>();
        
        try {
            
            String query = "SELECT Booking_ID, Barber, Booking_Date, Booking_Time FROM Bookings WHERE Customer=" + this.id + " AND Booking_Date>=" + dater.format(today) + ";";
            
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                String[] booking_info = new String[5];
                String[] barber = getBarber(rs.getInt("Barber"));
                booking_info[0] = rs.getString("Booking_Date");
                booking_info[1] = rs.getString("Booking_Time").substring(0, 5);
                booking_info[2] = rs.getString("Booking_ID");
                booking_info[3] = barber[0]; // barber's name
                booking_info[4] = barber[1]; // address
                booking_info[5] = barber[2]; // town
                customerBookings.add(booking_info); // stores barber and date infos into array that is added to an array list
            }
            rs.close();
            stmt.close();
            conn.close();
            
            /**
             * Sorting array list of arrays
             * code retrieved from https://stackoverflow.com/questions/4699807/sort-arraylist-of-array-in-java
             */
            customerBookings.sort((Comparator.comparing(a -> a[1])));
            customerBookings.sort((Comparator.comparing(a -> a[0])));
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        HashMap<String, String> next = new HashMap<>();
        next.put("date", customerBookings.get(0)[0]);
        next.put("time", customerBookings.get(0)[1]);
        next.put("id", customerBookings.get(0)[2]);
        next.put("name", customerBookings.get(0)[3]);
        next.put("address", customerBookings.get(0)[4]);
        next.put("town", customerBookings.get(0)[5]);
        
        return next;
    }
    
    public ArrayList<String[]> getBarberUpcomingBookings() {
        ArrayList<String[]> upcoming = new ArrayList<>();
        String currCustomer, currDate, currTime;
        int currID;
        
        try {
            String bookingQ = "SELECT * FROM Bookings WHERE Barber=" + this.id + " AND Booking_Status='upcoming' ORDER BY Booking_Time ASC, Booking_Date DESC;";
            
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(bookingQ);
            
            int c = 0;
            while(rs.next()) {
                upcoming.add(new String[4]);
                currID = rs.getInt("Booking_ID");
                currCustomer = getCustomer(rs.getInt("Customer"));
                currDate = rs.getString("Booking_Date");
                currTime = rs.getString("Booking_Time").substring(0, 5);
                
                upcoming.get(c)[0] = currDate;
                upcoming.get(c)[1] = currTime;
                upcoming.get(c)[2] = String.valueOf(currID);
                upcoming.get(c)[3] = currCustomer;
                
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
    
    public ArrayList<String[]> getAllBarberBookings(int id) {
        ArrayList<String[]> all = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM Bookings WHERE Barber=" + id + " ORDER BY Booking_Time ASC, Booking_Date DESC;";
            
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                String currID = rs.getString("Booking_ID");
                String currDate = rs.getString("Booking_Date");
                String currTime = rs.getString("Booking_Time");
                String currStatus = rs.getString("Booking_Status");
                String currCustomer = getCustomer(rs.getInt("Customer"));
                
                all.add(new String[]{currID, currDate, currTime, currStatus, currCustomer});
                
            }
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        return all;
    }
    
    public ArrayList<String[]> getAllCustomerBookings(int id) {
        ArrayList<String[]> all = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM Bookings WHERE Customer=" + id + " ORDER BY Booking_Time ASC, Booking_Date DESC;";
            
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                String currID = rs.getString("Booking_ID");
                String currDate = rs.getString("Booking_Date");
                String currTime = rs.getString("Booking_Time");
                String currStatus = rs.getString("Booking_Status");
                String currBarber = getCustomer(rs.getInt("Customer"));
                
                all.add(new String[]{currID, currDate, currTime, currStatus, currBarber});
                
            }
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        return all;
    }
    
    public void addReview(int id, String review, int stars) {
        try {
            String query = "INSERT INTO Booking_Review(Booking_ID, Review, Stars) VALUES(?, ?, ?);";
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.setString(2, review);
            stmt.setInt(3, stars);
            
            stmt.execute();
            
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }
    
    public void updateReview(int id, String review, int stars) {
        try {
            String query = "UPDATE Booking_Review SET Review='" + review + "', Stars=" + stars + " WHERE Booking_ID=" + id + ";";
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            
            stmt.executeQuery(query);
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }
    
    public void cancelBooking(int id) {
        try {
            String statusQ = "UPDATE Bookings SET Booking_Status=? WHERE id=?;";
            
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            
            PreparedStatement stmt = conn.prepareStatement(statusQ);
            stmt.setString(1, "cancelled");
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }
    
    private HashMap<Integer, String> getEmails() {
        // initialise a hashmap to store emails
        HashMap<Integer, String> emails = new HashMap<>();
        
        String query = "SELECT Account_ID, Email FROM Accounts";
        
        try {
            // open connection
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            
            // create statement
            Statement stmt = conn.createStatement();
            
            // execute query
            ResultSet rs = stmt.executeQuery(query);
            
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
    
    private HashMap<Integer, String> getPasswords() {
        // initialise hashmap to store id and passwords
        HashMap<Integer, String> passwords = new HashMap<>();
        
        String query = "SELECT Account_ID, Pass FROM Accounts";
        
        try {
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            // put id and password into hashmap
            while (rs.next()) {
                passwords.put(rs.getInt("Account_ID"), rs.getString("Pass"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        return passwords;
    }
    
    private void setSession() {
        try {
            String query = "SELECT First_Name, Last_Name, Type FROM Accounts WHERE Account_ID=" + this.id + ";";
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                this.name = rs.getString("First_Name");
                this.lastName = rs.getString("Last_Name");
                this.type = rs.getString("Type");
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }
    
    public boolean checkCredentials(String user, String pass) {
        // set id to -1
        id = -1;
        
        // if email input by user is in the database return the account id
        this.getEmails().forEach((Integer i, String em) -> {
            if (em.equals(user)) {
                id = i;
            }
        });
        
        // if it isn't in the database credentials are false
        if (id == -1) {
            return false;
        } else {
            return this.getPasswords().get(id).equals(pass); // if email is in database, check pasword and return whether it's a match
        }
        
    }
    
    public void logIn(String email) {
        this.id = -1;
        getEmails().forEach((i, e) -> {
            if (e.equals(email)) {
                this.id = i;
            }
        });
        
        setSession();
    }
    
    public void logOut() {
        this.name = null;
        this.lastName = null;
        this.type = null;
        this.id = -1;
    }
        
    public String createAccount(String email, String pass, String type, String firstName, String lastName, String location, String address, String town) {
        // set string success to null
        success = null;
        id = -1;
        
        // when creating account if the email is already in the database return message repeated email
        this.getEmails().forEach((i, em) -> {
            if (em.equals(email)) {
                success = "repeated email";
            }
        });
        
        if (success != null) {
            return success;
        }
        PreparedStatement stmt;
        try {
            Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            
            // creates new account inserting email into accounts
            String accountQ = "INSERT INTO Accounts(Email, First_Name, Last_Name, Pass, Type) VALUES(?, ?, ?, ?, ?);";
            stmt = conn.prepareStatement(accountQ);
            stmt.setString(1, email);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, pass);
            stmt.setString(5, type);
            
            stmt.execute();
            success = "account created";
            stmt.close();
            
            // gets account id using the email address
            this.getEmails().forEach((i, em) -> {
                if (em.equals(email)) {
                    id = i;
                }
            });
            
                        
            if (type.equals("barber")) {
                // sets account type using account id
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
            success = se.getMessage();
        }
        
        return success;
    }
    
    private String hashNsalt(String pass) {
        String password = null;
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[11];
            random.nextBytes(salt);

            KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 88);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            password = hash.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch(Exception except) {
            System.out.println(except.getMessage());
        }
        
        return password;
    }
    
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
}
