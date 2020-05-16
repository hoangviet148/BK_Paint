/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import java.util.ArrayList;
import shape.DrawType;

/**
 *
 * @author hung
 */
public class PaintState implements Serializable{
    public static final int ROTATE_RIGHT=1,ROTATE_LEFT = 2,ROTATE_REVERSE = 3,V_FLIP = 4,H_FLIP = 5,PAINTTING = 6;
    private ArrayList<Integer> drawStepList;
    private ArrayList<DrawType> listShape;
    private DrawType drawType;
    private int[] data = null;  //Lấy dữ liệu ảnh gốc
    private int w,h;
    public PaintState(){
        listShape = new ArrayList<>();
        drawStepList = new ArrayList<>();
        listShape.add(drawType);
    }
    public void addDrawStep(int drawStep){
        drawStepList.add(drawStep);
    }
    public ArrayList<Integer> getDrawStepList(){
        return drawStepList;
    }
    public void addDrawState(DrawType drawType){
        listShape.add(drawType);
    }
    public ArrayList<DrawType> getListState(){
        return listShape;
    }
    public DrawType removeEndShape(){
        if(listShape.isEmpty())
            return null;
        return listShape.remove(listShape.size()-1);
    }
    public int removeEndStep(){
        if(drawStepList.isEmpty())
            return -1;
        return drawStepList.remove(drawStepList.size()-1);
    }
    public boolean isEmpty(){
        return drawStepList.isEmpty();
    }
    public void removeAll(){
        listShape = new ArrayList<>();
        drawStepList = new ArrayList<>();
        System.gc();
    }
    
    public void setData(BufferedImage buff_img){
        w = buff_img.getWidth();
        h = buff_img.getHeight();
        data = new int[w*h*3];
        final WritableRaster wr = buff_img.getRaster();
        data = wr.getPixels(0, 0, w, h,data);
    }
    public int[] getData(){
        return data;
    }
    public int getWidth(){
        return w;
    }
    public int getHeight(){
        return h;
    }
}