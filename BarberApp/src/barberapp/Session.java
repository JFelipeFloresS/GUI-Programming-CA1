/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp;

/**
 *
 * @author José Felipe Flores da Silva
 * 2019405
 * 
 */
public class Session {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String type;
    
    public Session(int id, String firstName, String lastName, String type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }
    
    public int getID() {
        return this.id;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public String getType() {
        return this.type;
    }
}
