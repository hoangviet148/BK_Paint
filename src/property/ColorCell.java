/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author hung
 */
public class ColorCell extends ImageIcon{
    public static final int WIDTH = 25;
    public static final int HEIGHT = 25;
    private Point location;
    private Color color = Color.WHITE;
    public ColorCell(Color color){
        this.color = color;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){
        return color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.fillRect(6, 6, WIDTH+8, HEIGHT+8);
    }
}
