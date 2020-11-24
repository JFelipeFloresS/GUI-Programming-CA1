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
public class Stars {

    /**
     * @return scaled image of an unselected star
     */
    public Image unselectedStar() {
        Image star = null;
        try {
            star = ImageIO.read(getClass().getResource("unselectedStar.png"));
            star = star.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        return star;
    }

    /**
     * @return scaled image of an selected star
     */
    public Image selectedStar() {
        Image star = null;
        try {
            star = ImageIO.read(getClass().getResource("selectedStar.png"));
            star = star.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        return star;
    }
}
