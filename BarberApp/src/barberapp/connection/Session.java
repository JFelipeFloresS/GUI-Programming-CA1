/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.connection;

/**
 *
 * @author José Felipe Flores da Silva 2019405
 *
 */
public class Session {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String type;

    /**
     * Session constructor.
     *
     * @param id user ID
     * @param firstName user first name
     * @param lastName user last name
     * @param type user type (barber or customer)
     */
    public Session(int id, String firstName, String lastName, String type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

    /**
     * @return session ID
     */
    public int getID() {
        return this.id;
    }

    /**
     * @return session first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return session last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @return session type
     */
    public String getType() {
        return this.type;
    }
}
