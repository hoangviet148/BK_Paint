/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author hung
 */
public class Shape implements Serializable {

    protected Color strokeColor;
    protected ArrayList<Point> draggPoint = new ArrayList<Point>();
    protected Point start, end;
    public void setPoint(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public void addDraggedPoint(Point drag) {
        draggPoint.add(drag);
    }

    public ArrayList<Point> getDraggedPoint() {
        return draggPoint;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public Color getStrokeColor() {
        return this.strokeColor;
    }
    
    protected float strokeThickness;
    protected int endStrokeCap;
    protected int lineStrokeJoin;
    protected float dashPhase;
    protected float miterLimit = 1.0f;
    protected float[] dashArray;
    
    //Lay thong tin cua mot stroke
    public void setStroke(BasicStroke stroke){
        strokeThickness = stroke.getLineWidth();
        endStrokeCap = stroke.getEndCap();
        lineStrokeJoin = stroke.getLineJoin();
        dashPhase = stroke.getDashPhase();
     //   miterLimit = stroke.getMiterLimit();
        dashArray = stroke.getDashArray();
        
    }
}
