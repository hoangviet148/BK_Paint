/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author hung
 */
public class ImageCell extends ImageIcon {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 125;
    private Image image = null;

    public ImageCell(ImageIcon imageIcon) {
        this.image = imageIcon.getImage().getScaledInstance(WIDTH, HEIGHT,Image.SCALE_SMOOTH);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (image != null) {
            g.drawImage(image, x+1, y+1,image.getWidth(null)-2,image.getHeight(null)-2, null);
        }

    }

    @Override
    public int getIconWidth() {
        return WIDTH;
    }

    @Override
    public int getIconHeight() {
        return HEIGHT;
    }

}
