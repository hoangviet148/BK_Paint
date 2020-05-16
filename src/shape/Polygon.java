/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

/**
 *
 * @author hung
 */

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class Polygon extends Shape implements DrawType {
    private ArrayList<Line> listLine = new ArrayList<>();
    public ArrayList<Line> getListLine(){
        return listLine;
    }
    //them mot duong thang moi vao danh sach
    public void addElement(Line line){
        listLine.add(line);
    }
    
    
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(strokeColor);
        for(int i=0;i<listLine.size();i++){
            listLine.get(i).draw(g2d);
        }
    }
}
