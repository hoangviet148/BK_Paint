/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

/**
 *
 * @author hung
 */
public class Eraser extends Shape implements DrawType {

    private float sizeEraser;

    @Override
    public void draw(Graphics2D g2d) {
        this.setSizeEraser(strokeThickness + 8f);
        BasicStroke stroke = new BasicStroke(sizeEraser);
        g2d.setStroke(stroke);
        g2d.setColor(strokeColor);
        if (start != null && end != null) {
            g2d.drawLine(start.x, start.y, end.x, end.y);
        }

    }
    
    public float getSizeEraser() {
        return sizeEraser;
    }

    public void setSizeEraser(float sizeEraser) {
        this.sizeEraser = sizeEraser;
    }
}
