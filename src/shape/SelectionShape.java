/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 *
 * @author Gaara
 */
public class SelectionShape extends Shape implements DrawType {
    private Point startOrigin, endOrigin;
    private boolean isCreating = false;
    private boolean isSelected = false;
    private boolean isDragging = false;
    private boolean fillOrigin = true;
    private Point endTempDrag;
    private int[] data;
    private int w;
    private int h;
    @Override
    public void draw(Graphics2D g2d) {
        Stroke stroke = new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 3f, new float[]{5f}, 1f);
        g2d.setColor(Color.MAGENTA);
        g2d.setStroke(stroke);
        BufferedImage img = null;
        if (startOrigin != null && endOrigin != null) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(Math.min(startOrigin.x, endOrigin.x), Math.min(startOrigin.y, endOrigin.y),
                    Math.abs(startOrigin.x - endOrigin.x), Math.abs(startOrigin.y - endOrigin.y));
            img = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
            img.getRaster().setPixels(0,0,w, h, data);
            if(start!=null)
                g2d.drawImage(img, start.x, start.y, null);
        }
        if (start!=null&&end!=null) {
            if(isCreating==false){
                g2d.drawRect(Math.min(start.x, end.x), Math.min(start.y, end.y),
                Math.abs(start.x - end.x), Math.abs(start.y - end.y));
            }
            else if(!isSelected){
                g2d.drawRect(start.x, start.y, w, h);
            }
        }
        
            
    }
    public void setStart(Point start){
        this.start = start;
    }
    public void setEndTempDragg(Point endTempDrag){
        this.endTempDrag = endTempDrag;
    }
    public Point getStartLocation(){
            return start;
        }
    public Point getEndlocation(){
        return new Point(start.x+w,start.y+h);
    }
    
    
    //check xem có phải hình chữ nhật không

    public boolean checkOverlap() {
        if (start.x == end.x || start.y == end.y) {
            return false;
        } else {
            return true;
        }
    }

    public void setStartOrigin(Point p) {
        this.startOrigin = p;
    }

    public Point getStartOrigin() {
        return startOrigin;
    }

    public void setEndOrigin(Point p) {
        this.endOrigin = p;
    }

    public Point getEndOrigin() {
        return endOrigin;
    }

    public int getWidth(){
        return w;
    }
    public int getHeight(){
        return h;
    }
    public int[] getData(){
        return data;
    }
    
    public void setImage(BufferedImage buff_img) {
        BufferedImage img = new BufferedImage(buff_img.getWidth(),buff_img.getHeight(),BufferedImage.TYPE_INT_RGB);
        w = img.getWidth();
        h = img.getHeight();
        data = new int[w*h*3];
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        g2d.drawImage(buff_img, 0, 0, null);
        g2d.dispose();
        final WritableRaster wr = img.getRaster();
        data = wr.getPixels(0, 0, w, h,data);
        
        
    }

    public boolean isCreating() {
        return isCreating;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void setIsCreating(boolean isCreating) {
        this.isCreating = isCreating;
    }

    public void setIsDragging(boolean isDragging) {
        this.isDragging = isDragging;
    }

    public void setFillOrigin(boolean fillOrigin) {
        this.fillOrigin = fillOrigin;
    }

    public boolean getFillOrigin() {
        return fillOrigin;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
