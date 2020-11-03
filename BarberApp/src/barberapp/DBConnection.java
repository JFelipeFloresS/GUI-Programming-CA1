package barberapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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
    private final Controller controller;
    
    public DBConnection(Controller controller) {
        // initialise variables
        this.controller = controller;
        initialise();
        logIn("jimmijoey@gmail.com");
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
    
    public int getID() {
        return this.id;
    }
    
    public String[] getLocations() {
        ArrayList<String> locations = new ArrayList<>();
        String[] l;
        
        String query = "SELECT Location FROM Barber_Location";
        
        // open connection
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password); // create statement
            Statement stmt = conn.createStatement(); // execute query
            ResultSet rs = stmt.executeQuery(query)){
            
            // put id and email into hashmap
            while (rs.next()) {
                if (!locations.contains(rs.getString("Location"))) {
                    locations.add(rs.getString("Location"));
                }
            }
        
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }
        l = new String[locations.size()];
        for (int i = 0; i < l.length; i++) {
            l[i] = locations.get(i);
        }
        return l;
    }
    
    public String[] getBarber(int id) {
        String[] barber = new String[6];
        
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();){
            
            String query = "SELECT First_Name, Last_Name, Address, Town, Location, Phone FROM Accounts, Barber_Location WHERE Accounts.Account_ID=" + id + " AND Barber_Location.Account_ID=" + id + ";";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                barber[0] = rs.getString("First_Name");
                barber[1] = rs.getString("Address");
                barber[2] = rs.getString("Town");
                barber[3] = rs.getString("Location");
                barber[4] = rs.getString("Phone");
                barber[5] = rs.getString("Last_Name");
            }
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        return barber;
    }
    
    public String[] getCustomer(int id){
        String[] customer = new String[2];
        
        String query = "SELECT First_Name, Phone FROM Accounts WHERE Account_ID=" + id + ";";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            
            while (rs.next()) {
                customer[0] = rs.getString("First_Name");
                customer[1] = rs.getString("Phone");
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        return customer;
    }
    
    public HashMap<String, String> getNextCustomerBooking() {
        // hashmap to store customer's bookings
        ArrayList<String[]> customerBookings = new ArrayList<>();
        
        String query = "SELECT Booking_ID, Barber, Booking_Date, Booking_Time, Booking_Status FROM Bookings WHERE Customer=" + this.id + " AND Booking_Status!='cancelled' ORDER BY Booking_Date, Booking_Time;";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            
            while(rs.next()) {
                String[] booking_info = new String[9];
                String[] barber = getBarber(rs.getInt("Barber"));
                booking_info[0] = rs.getString("Booking_Date");
                booking_info[1] = rs.getString("Booking_Time").substring(0, 5);
                booking_info[2] = rs.getString("Booking_ID");
                booking_info[3] = barber[0]; // barber's name
                booking_info[4] = barber[1]; // address
                booking_info[5] = barber[2]; // town
                booking_info[6] = barber[3]; // location
                booking_info[7] = barber[4]; // phone
                booking_info[8] = rs.getString("Booking_Status");
                customerBookings.add(booking_info); // stores barber and date infos into array that is added to an array list
            }
            rs.close();
            stmt.close();
            conn.close();
            
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
        next.put("location", customerBookings.get(0)[6]);
        next.put("phone", customerBookings.get(0)[7]);
        next.put("status", customerBookings.get(0)[8]);
        
        return next;
    }
    
    public ArrayList<String[]> getBarberUpcomingBookings() {
        ArrayList<String[]> upcoming = new ArrayList<>();
        String[] currCustomer;
        String currDate, currTime, currStatus;
        int currID;
        
        String bookingQ = "SELECT * FROM Bookings WHERE Barber=" + this.id + " AND Booking_Status!='cancelled' ORDER BY Booking_Date, Booking_Time;";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(bookingQ);){
            
            int c = 0;
            while(rs.next()) {
                upcoming.add(new String[6]);
                currID = rs.getInt("Booking_ID");
                currCustomer = getCustomer(rs.getInt("Customer"));
                currDate = rs.getString("Booking_Date");
                currTime = rs.getString("Booking_Time").substring(0, 5);
                currStatus = rs.getString("Booking_Status");
                
                upcoming.get(c)[0] = currDate; // date
                upcoming.get(c)[1] = currTime; // time
                upcoming.get(c)[2] = String.valueOf(currID); // booking ID
                upcoming.get(c)[3] = currCustomer[0]; // customer name
                upcoming.get(c)[4] = currCustomer[1]; // customer phone
                upcoming.get(c)[5] = currStatus; // booking status
                
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
        
        String query = "SELECT * FROM Bookings WHERE Barber=" + id + " ORDER BY Booking_Date DESC, Booking_Time DESC;";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            
            while(rs.next()) {
                String currID = rs.getString("Booking_ID");
                String currDate = rs.getString("Booking_Date");
                String currTime = rs.getString("Booking_Time");
                String currStatus = rs.getString("Booking_Status");
                String[] currCustomer = getCustomer(rs.getInt("Customer"));
                
                all.add(new String[]{currID, currDate, currTime, currStatus, currCustomer[0], currCustomer[1]});
                
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
        
        String query = "SELECT * FROM Bookings WHERE Customer=" + id + " ORDER BY Booking_Date DESC, Booking_Time DESC;";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            
            while(rs.next()) {
                String currID = rs.getString("Booking_ID");
                String currDate = rs.getString("Booking_Date");
                String currTime = rs.getString("Booking_Time");
                String currStatus = rs.getString("Booking_Status");
                String currBarber[] = getBarber(rs.getInt("Barber"));
                
                all.add(new String[]{currID, currDate, currTime, currStatus, currBarber[0], currBarber[1], currBarber[2], currBarber[3], currBarber[4], currBarber[5]});
                
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
        String query = "INSERT INTO Booking_Review(Booking_ID, Review, Stars) VALUES(?, ?, ?);";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            PreparedStatement stmt = conn.prepareStatement(query);){
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
        String query = "UPDATE Booking_Review SET Review='" + review + "', Stars=" + stars + " WHERE Booking_ID=" + id + ";";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();){
            
            stmt.executeQuery(query);
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }
    
    public void cancelBooking(int id) {
        String statusQ = "UPDATE Bookings SET Booking_Status=? WHERE Booking_ID=?;";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            PreparedStatement stmt = conn.prepareStatement(statusQ);){
            
            stmt.setString(1, "cancelled");
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }
    
    public void addAvailability(int id, String date, String time) {
        String query = "INSERT INTO Barber_Availability(Account_ID, Available_Date, Available_Time) VALUES(" + id + ", '" + date + "', '" + time + "');";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            PreparedStatement stmt = conn.prepareStatement(query);){
            
            stmt.execute();
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }
    
    public void removeAvailability(int id, String date, String time) {
        String query = "DELETE FROM Barber_Availability WHERE Account_ID=" + id + " AND Available_Date='" + date + "' AND Available_Time='" + time + "';";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            PreparedStatement stmt = conn.prepareStatement(query);){
            
            stmt.execute();
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }
    
    public ArrayList<String[]> getAvailability(int barber, String dateToCheck) {
        ArrayList<String[]> available = new ArrayList<>();
        
        String query = "SELECT Available_Date, Available_Time FROM Barber_Availability WHERE Account_ID=" + barber;
        if (dateToCheck != null) {
            query += " AND Available_Date='" + dateToCheck + "'";
        }
        query += " ORDER BY Available_Date, Available_Time;";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();){
            
            while(rs.next()) {
                String date = rs.getString("Available_Date");
                String time = rs.getString("Available_Time");
                String[] dateTime = {date, time};
                available.add(dateTime);
            }
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        return available;
    }
    
    private HashMap<Integer, String> getEmails() {
        // initialise a hashmap to store emails
        HashMap<Integer, String> emails = new HashMap<>();
        
        String query = "SELECT Account_ID, Email FROM Accounts";
        
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password); // open connection
            Statement stmt = conn.createStatement(); // create statement
            ResultSet rs = stmt.executeQuery(query);){  // execute query
            
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
        
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            
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
        String query = "SELECT First_Name, Last_Name, Type FROM Accounts WHERE Account_ID=" + this.id + ";";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            
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
            if (em.equalsIgnoreCase(user)) {
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
            if (e.equalsIgnoreCase(email)) {
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
    
    public void createBooking(String date, String time, int customer, int barber) {
        String query = "INSERT INTO Bookings(Booking_Date, Booking_Time, Booking_Status, Customer, Barber)"
                + "VALUES('" + date + "', '" + time + "', 'requested', " + customer + ", " + barber + ");";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();){
            
            stmt.execute(query);
            
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }
    
    public void confirmBooking(int id) {
        String query = "UPDATE Bookings SET Booking_Status='upcoming' WHERE Booking_ID=" + id + ";";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();){
            
            stmt.executeUpdate(query);
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }
    }
    
    public int getBookingID(String date, String time, int customer, int barber) {
        int booking = 0;
        
        String query = "SELECT Booking_ID FROM Bookings WHERE (Booking_Date='" + date + "' AND Booking_Time='" + time + "' AND Booking_Status!='cancelled' AND Customer=" + customer + " AND Barber=" + barber + ");";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            
            while(rs.next()) {
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
    
    public HashMap<String, String> getBookingInfo(int id) {
        HashMap<String, String> info = null;
        
        String query = "SELECT * FROM Bookings WHERE Booking_ID=" + id + ";";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            info = new HashMap<>();
            
            while(rs.next()) {
                String[] c = getCustomer(rs.getInt("Customer"));
                String[] b = getBarber(rs.getInt("Barber"));
                
                info.put("date", rs.getString("Booking_Date"));
                info.put("time", rs.getString("Booking_Time"));
                info.put("status", rs.getString("Booking_Status"));
                info.put("customer name", c[0]);
                info.put("customer phone", c[1]);
                info.put("barber name", b[0]);
                info.put("barber last name", b[5]);
                info.put("barber phone", b[4]);
                info.put("address", b[1]);
                info.put("town", b[2]);
                info.put("location", b[3]);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        return info;
    }
    
    public ArrayList<String[]> searchForBarberName(String search) {
        ArrayList<String[]> results = new ArrayList<>();
        String[] fullName = null;
        String name1 = null;
        String name2 = null;
        search = search.toLowerCase();
        
        if (search.contains(" ")) {
            fullName = search.split(" ");
            name1 = fullName[0];
            name2 = fullName[1];
        }
        
        String query;
        if (fullName != null) {
            query = "SELECT * FROM Accounts WHERE Type='barber' AND (lower(First_Name)='" + name1 + "' OR lower(Last_Name)='" + name1 + "' OR lower(First_Name)='" + name2 + "' OR lower(Last_Name)='" + name2 + "')";
        } else {
            query = "SELECT * FROM Accounts WHERE Type='barber' AND (lower(First_Name)='" + search + "' OR lower(Last_Name)='" + search + "')";
        }
        
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            
            while(rs.next()) {
                String[] bLocation = getBarber(rs.getInt("Account_ID"));
                String i = rs.getString("Account_ID");
                String fn = rs.getString("First_Name");
                String ln = rs.getString("Last_Name");
                String p = rs.getString("Phone");
                String[] result = {i, fn, ln, p, bLocation[1], bLocation[2], bLocation[3]};
                results.add(result);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        return results;
    }
    
    public ArrayList<String[]> searchForBarberLocation(String l) {
        ArrayList<String[]> results = new ArrayList<>();
        
        String query = "SELECT * FROM Barber_Location WHERE Barber_Location.Location='" + l + "'";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            
            while(rs.next()) {
                String[] ba = getBarber(rs.getInt("Account_ID"));
                String i = rs.getString("Account_ID");
                String a = rs.getString("Address");
                String t = rs.getString("Town");
                String lo = rs.getString("Location");
                String[] result = {i, ba[0], ba[5], ba[4], a, t, lo};
                results.add(result);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            handleExceptions(se);
        }
        
        return results;
    }
    
    public String createAccount(String email, String pass, String type, String firstName, String lastName, String phone, String location, String address, String town) {
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
        
        // creates new account inserting email into accounts
        String accountQ = "INSERT INTO Accounts(Email, First_Name, Last_Name, Pass, Type, Phone) VALUES(?, ?, ?, ?, ?, ?);";
        try (Connection conn = DriverManager.getConnection(this.dbServer, this.user, this.password);){
            
            PreparedStatement stmt = conn.prepareStatement(accountQ);
            stmt.setString(1, email);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, pass);
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
    
    /*
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
}
