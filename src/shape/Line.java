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
public class Line extends Shape implements DrawType {
    

    @Override
    public void draw(Graphics2D g2d) {
         BasicStroke stroke = new BasicStroke(strokeThickness,endStrokeCap,lineStrokeJoin,miterLimit,
        dashArray,dashPhase);
        g2d.setStroke(stroke);
        g2d.setColor(strokeColor);
        g2d.drawLine(start.x, start.y, end.x, end.y);
    }
}
