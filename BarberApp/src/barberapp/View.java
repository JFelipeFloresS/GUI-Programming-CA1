/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barberapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author José Felipe Flores da Silva 2019405
 *
 */
public class View extends JFrame {

    Controller controller;

    /**
     * View constructor.
     *
     * @param controller the controller that calls the view
     */
    public View(Controller controller) {
        this.controller = controller;
        windowSetup();

    }

    /**
     * Initialise JFrame and sets the page to initialPage.
     */
    private void windowSetup() {
        //initialise JFrame
        this.setVisible(true);
        this.setSize(Globals.windowWidth, Globals.windowHeight);
        this.setTitle("Find A Barber");
        this.setResizable(false);

        //initialise main panel
        //this.add(new initialPage());
        this.add(new InitialPage(this.controller));

        //finalise JFrame
        getContentPane().setFont(Globals.bodyFont);
        getContentPane().setForeground(Color.black);
        this.validate();
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Sets standard parameters to components. Concept retrieved from
     * https://stackoverflow.com/questions/27774581/change-background-color-of-components-with-reference-to-color-variable-java
     *
     * @param panel the panel to standardise the children from
     * @param buttonStandardSize whether the buttons should be set to standard
     * size
     * @param controller controller to be set to children
     */
    public static void standardiseChildren(JPanel panel, boolean buttonStandardSize, Controller controller) {
        if (panel.getBackground().equals(Globals.DEFAULTCOLOUR)) {
            panel.setBackground(Globals.WHITE);
        }
        Component[] component = panel.getComponents();
        for (Component c : component) {
            if (c instanceof JButton) {
                if (((JButton) c).getActionCommand().contains("star")) {
                    ((JButton) c).addActionListener(controller);
                    ((JButton) c).setCursor(new Cursor(Cursor.HAND_CURSOR));
                    c.setBackground(null);
                    ((JButton) c).setBorder(null);
                } else {
                    c.setFont(Globals.bodyFont);
                    c.setBackground(Globals.BLUE);
                    ((JButton) c).setBorder(Globals.border(Globals.DARKBLUE, 2));
                    c.setForeground(Globals.WHITE);

                    if (buttonStandardSize) {
                        c.setPreferredSize(Globals.regularButtonDimension);
                    }
                }
                ((JButton) c).addActionListener(controller);
                ((JButton) c).setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else if (c instanceof JPanel) {
                standardiseChildren((JPanel) c, buttonStandardSize, controller);
            } else if (c instanceof JLabel) {
                c.setFont(Globals.bodyFont);
                c.setForeground(Color.black);
            } else if (c instanceof JTextField) {
                c.setBackground(Globals.TEXTFIELDCOLOUR);
                ((JTextField) c).setBorder(Globals.border(Globals.DARKBLUE, 1));
                c.setFont(Globals.bodyFont);
            } else if (c instanceof JTextArea) {
                c.setBackground(Globals.TEXTFIELDCOLOUR);
                ((JTextArea) c).setBorder(Globals.border(Globals.DARKBLUE, 1));
                c.setFont(Globals.bodyFont);
            } else if (c instanceof JComboBox) {
                c.setBackground(Globals.WHITE);
                ((JComboBox) c).setBorder(Globals.border(Globals.DARKBLUE, 1));
                c.setFont(Globals.bodyFont);
                ((JComboBox) c).setUI(ColorArrowUI.createUI(((JComboBox) c)));
            } else if (c instanceof JCheckBox) {
                c.setBackground(Globals.WHITE);
                c.setFont(Globals.bodyFont);
            } else if (c instanceof JScrollPane) {
                ((JScrollPane) c).getVerticalScrollBar().setBackground(Globals.DARKBLUE);
                ((JScrollPane) c).getVerticalScrollBar().setUI(new standardScrollBar());
                ((JScrollPane) c).getHorizontalScrollBar().setUI(new standardScrollBar());
            }
        }
    }

    /**
     * Code to change colour of combo box retrieved from
     * http://www.java2s.com/Tutorial/Java/0240__Swing/CustomizingaJComboBoxLookandFeel.htm
     */
    public static class ColorArrowUI extends BasicComboBoxUI {

        public static ComboBoxUI createUI(JComponent c) {
            return new ColorArrowUI();
        }

        @Override
        protected JButton createArrowButton() {
            return new BasicArrowButton(
                    BasicArrowButton.SOUTH,
                    Globals.BLUE, Globals.DARKBLUE,
                    Globals.WHITE, Globals.DARKBLUE
            );
        }

    }

    /**
     * Hiding buttons from scrollbar code retrieved from
     * https://stackoverflow.com/questions/7633354/how-to-hide-the-arrow-buttons-in-a-jscrollbar
     */
    public static class standardScrollBar extends BasicScrollBarUI {

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return invisibleButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return invisibleButton();
        }

        private JButton invisibleButton() {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(0, 0));
            btn.setMaximumSize(new Dimension(0, 0));
            btn.setMinimumSize(new Dimension(0, 0));
            return btn;
        }
    }

    /**
     * Changes the image of the stars and the integer stars based on what star
     * has been pressed.
     *
     * @param n star number
     */
    public void starPressed(int n) {
        Image selectedStar = new Stars().selectedStar();
        Image unselectedStar = new Stars().unselectedStar();
        switch (n) {
            case 5:
                Globals.star5.setIcon(new ImageIcon(selectedStar));
                Globals.star4.setIcon(new ImageIcon(selectedStar));
                Globals.star3.setIcon(new ImageIcon(selectedStar));
                Globals.star2.setIcon(new ImageIcon(selectedStar));
                Globals.star1.setIcon(new ImageIcon(selectedStar));
                break;
            case 4:
                Globals.star5.setIcon(new ImageIcon(unselectedStar));
                Globals.star4.setIcon(new ImageIcon(selectedStar));
                Globals.star3.setIcon(new ImageIcon(selectedStar));
                Globals.star2.setIcon(new ImageIcon(selectedStar));
                Globals.star1.setIcon(new ImageIcon(selectedStar));
                break;
            case 3:
                Globals.star5.setIcon(new ImageIcon(unselectedStar));
                Globals.star4.setIcon(new ImageIcon(unselectedStar));
                Globals.star3.setIcon(new ImageIcon(selectedStar));
                Globals.star2.setIcon(new ImageIcon(selectedStar));
                Globals.star1.setIcon(new ImageIcon(selectedStar));
                break;
            case 2:
                Globals.star5.setIcon(new ImageIcon(unselectedStar));
                Globals.star4.setIcon(new ImageIcon(unselectedStar));
                Globals.star3.setIcon(new ImageIcon(unselectedStar));
                Globals.star2.setIcon(new ImageIcon(selectedStar));
                Globals.star1.setIcon(new ImageIcon(selectedStar));
                break;
            case 1:
                Globals.star5.setIcon(new ImageIcon(unselectedStar));
                Globals.star4.setIcon(new ImageIcon(unselectedStar));
                Globals.star3.setIcon(new ImageIcon(unselectedStar));
                Globals.star2.setIcon(new ImageIcon(unselectedStar));
                Globals.star1.setIcon(new ImageIcon(selectedStar));
                break;
        }
        Globals.stars = n;
    }
}
