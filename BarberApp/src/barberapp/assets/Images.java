/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.assets;

import barberapp.main.View;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class Images {

    /**
     * @param size size desired in pixels
     * 
     * @return scaled image of an unselected star
     */
    public Image unselectedStar(int size) {
        Image star = null;
        try {
            star = ImageIO.read(getClass().getResource("unselectedStar.png"));
            star = star.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        return star;
    }

    /**
     * @param size size desired in pixels
     * 
     * @return scaled image of a selected star
     */
    public Image selectedStar(int size) {
        Image star = null;
        try {
            star = ImageIO.read(getClass().getResource("selectedStar.png"));
            star = star.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        return star;
    }
    
    /**
     * @param size size desired in pixels
     * 
     * @return scaled image of a calendar
     */
    public Image calendarImage(int size) {
        Image calendar = null;
        try {
            calendar = ImageIO.read(getClass().getResource("calendar.png"));
            calendar = calendar.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        return calendar;
    }
    
    /**
     * @param size size desired in pixels
     * 
     * @return scaled image of logo
     */
    public Image logo(int size) {
        Image logo = null;
        try {
            logo = ImageIO.read(getClass().getResource("find_a_logo.png"));
            logo = logo.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return logo;
    }
}
