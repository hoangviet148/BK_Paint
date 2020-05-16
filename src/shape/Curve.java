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
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

/**
 *
 * @author hung
 */
public class Curve extends Shape implements DrawType {
    private int state = 1;
    private ArrayList<Point> list;  //dung thang nay de chua danh sach 4 diem can ve
    private ArrayList<Point> listPointToState1;
    private ArrayList<Point> listPointToState2;
    private ArrayList<Point> listPointToState3;
    public Curve() {
        list = new ArrayList<>();
        listPointToState1 = new ArrayList<>();
        listPointToState2 = new ArrayList<>();
        listPointToState3 = new ArrayList<>();
        Point p;
        //add 4 diem vao cho thang list
        for(int i = 0; i < 4; i++){
            p = new Point(0, 0);
            list.add(p);
        }
        
        //Dat lai trang thai ban dau la mot
        state = 1;
    }
    
    //Dta lai trang thai cua cung ve lai ban dau
     public void resetState(){
        this.state = 1;
    }
    
    public void incState(){
        this.state++;
    }
    
    public void setList(ArrayList<Point> list){
        this.list = list;
    }
    public void setStartPoint(Point start){
        this.start = start;
    }
    
    //That ra minh chi can lay kich thuoc cua tung trang thai, roi dung no de so sanh voi kich thuoc cua cai list diem
    public void addPointToState(Point p){
        switch(state){
            case 1:
                listPointToState1.add(p);
                break;
            case 2:
                listPointToState2.add(p);
                break;
            case 3:
                listPointToState3.add(p);
                break;
        }
    }
    public int getSizeOfStateFirst(){
        return listPointToState1.size();
    }
    public int getSizeOfStateSecond(){
        return listPointToState1.size()+listPointToState2.size();
    }
    
    public int getState(){
        return state;
    }
    
    public void setState(int state){
        this.state = state;
    }
    
    @Override
    public void draw(Graphics2D g2d){
        
        BasicStroke stroke = new BasicStroke(strokeThickness,endStrokeCap,lineStrokeJoin,miterLimit,
        dashArray,dashPhase);
        g2d.setStroke(stroke);
        g2d.setColor(strokeColor);
        if(state == 1){ //Va khi nay thi se thay doi toa do cua thang dau tien
            //Cho toa do thang hai va thang 3 la bang nhau
            list.get(2).setLocation(list.get(3));
         //   //Cho toa do hai thang 1 va 3 la bang nhau
            list.get(1).setLocation(list.get(3));   //nhap ba thang sau lam mot
        }
        else if(state == 2){
            list.get(1).setLocation(list.get(2)); //Nhap thang thu hai va thang thu ba lam mot
        }
        GeneralPath gP = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        gP.moveTo(list.get(0).x, list.get(0).y);  //thang dau tien la thang co dinh, may thang sau moi la may thang dong       
        gP.curveTo(list.get(1).x, list.get(1).y, list.get(2).x, list.get(2).y, list.get(3).x, list.get(3).y); // point 2 + point 3
        g2d.draw(gP);
    }
    
    public ArrayList<Point> getList(){
        return list;
    }
}
