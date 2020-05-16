/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author hung
 */
public class Pencil extends Shape implements DrawType {

    @Override
    public void draw(Graphics2D g2d) {
        BasicStroke stroke = null;
        if(miterLimit>=1.0f)
             stroke = new BasicStroke(strokeThickness,endStrokeCap,lineStrokeJoin,miterLimit,
        dashArray,dashPhase);
        if (stroke != null) {
            g2d.setStroke(stroke);
        }
        g2d.setColor(strokeColor);
        if (start != null && end != null) {
            g2d.drawLine(start.x, start.y, end.x, end.y);
        }

    }

    @Override
    public Color getStrokeColor() {
        return strokeColor;
    }

}
