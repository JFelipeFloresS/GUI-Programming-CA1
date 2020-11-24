/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class Globals {

    // window size
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;

    // fonts
    public static final Font TITLE_FONT = new Font("Tahoma", Font.BOLD, 46);
    public static final Font BODY_FONT = new Font("Tahoma", Font.PLAIN, 26);
    public static final Font SMALL_FONT = new Font("Tahoma", Font.PLAIN, 16);
    public static final Font LARGE_FONT = new Font("Tahoma", Font.PLAIN, 36);

    /**
     * Creates border.
     *
     * @param colour colour of border
     * @param width width of border
     * @return border
     */
    public static Border border(Color colour, int width) {
        return BorderFactory.createLineBorder(colour, width);
    }

    // dimensions
    public static final Dimension REGULAR_BUTTON_DIMENSION = new Dimension((int) WINDOW_WIDTH / 6, (int) WINDOW_HEIGHT / 12);
    public static final Dimension LOGIN_PANEL_DIMENSION = new Dimension((int) 2 * WINDOW_WIDTH / 3, (int) WINDOW_HEIGHT / 4);
    public static final Dimension TOP_PANEL_DIMENSION = new Dimension(WINDOW_WIDTH, (int) WINDOW_HEIGHT / 8);
    public static final Dimension BOTTOM_PANEL_DIMENSION = new Dimension(WINDOW_WIDTH, (int) 7 * WINDOW_HEIGHT / 8);
    public static final Dimension LEFT_PANEL_DIMENSION = new Dimension((int) WINDOW_WIDTH / 4, WINDOW_HEIGHT);
    public static final Dimension RIGHT_PANEL_DIMENSION = new Dimension((int) 3 * WINDOW_WIDTH / 4, WINDOW_HEIGHT);
    public static final Dimension PADDING_Y1 = new Dimension(WINDOW_WIDTH, (int) WINDOW_HEIGHT / 10);
    public static final Dimension PADDING_Y2 = new Dimension(WINDOW_WIDTH, (int) WINDOW_HEIGHT / 8);
    public static final Dimension PADDING_Y3 = new Dimension(WINDOW_WIDTH, (int) WINDOW_HEIGHT / 6);
    public static final Dimension PADDING_Y4 = new Dimension(WINDOW_WIDTH, (int) WINDOW_HEIGHT / 4);
    public static final Dimension PADDING_Y5 = new Dimension(WINDOW_WIDTH, (int) WINDOW_HEIGHT / 3);
    public static final Dimension PADDING_X1 = new Dimension((int) WINDOW_WIDTH / 10, WINDOW_HEIGHT);
    public static final Dimension PADDING_X2 = new Dimension((int) WINDOW_WIDTH / 8, WINDOW_HEIGHT);
    public static final Dimension PADDING_X3 = new Dimension((int) WINDOW_WIDTH / 6, WINDOW_HEIGHT);
    public static final Dimension PADDING_X4 = new Dimension((int) WINDOW_WIDTH / 4, WINDOW_HEIGHT);
    public static final Dimension PADDING_X5 = new Dimension((int) WINDOW_WIDTH / 3, WINDOW_HEIGHT);

    // colours
    public static final Color WHITE = new Color(250, 250, 250);
    public static final Color BLUE = new Color(133, 133, 233);
    public static final Color DARKBLUE = new Color(85, 85, 185);
    public static final Color TEXTFIELDCOLOUR = new Color(233, 233, 233);
    public static final Color DEFAULTCOLOUR = new Color(238, 238, 238);

}
