package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import property.TextPanel;

/**
 *
 * @author Khanh
 */
public class Text extends Shape implements DrawType {

    private boolean isOpaque;
    private TextPanel textPanel = new TextPanel();
    private Color fillColor = Color.WHITE;
    BasicStroke STROKE_1 = new BasicStroke(1f);
    BasicStroke STROKE_2 = new BasicStroke(1f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_ROUND, 1.0f, new float[]{2f, 0f, 2f}, 2f);
    private Color color;
    private Color textColor;
    private static final int EDGE_SQUARE = 4;
    private String string = "";
    private JTextPane area;
    private Point start;
    private Point end;
    private boolean isCreated = false;
    private Font font;

    public Text() {
        this.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, new float[]{2f, 0f, 2f}, 2f));
        this.setTextColor(Color.BLACK);
        this.setColor(Color.BLUE);
    }
    
    public void setFillColor(Color fillColor){
        this.fillColor = fillColor;
    }
    
    public Color getFillColor(){
        return this.fillColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getTextColor() {
        return this.textColor;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setArea(JPanel panel) {
        area = new JTextPane();
        int[] a = {Math.min(start.x, end.x), Math.min(start.y, end.y), Math.max(start.x, end.x), Math.max(start.y, end.y)};
        area.setBounds(a[0] + 1, a[1] + 1, a[2] - a[0] - 1, a[3] - a[1] - 1);
        panel.add(area);
    }

    public void removeArea(JPanel panel) {
        panel.remove(area);
    }

    public void setIsCreated(boolean isCreated) {
        this.isCreated = isCreated;
    }

    public boolean getIsCreated() {
        return this.isCreated;
    }

    public void setString() {
        this.string = area.getText();
    }

    public String getString() {
        return string;
    }

    public boolean equals(String s) {
        if (string.equals(s) == true) {
            return true;
        } else {
            return false;
        }
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public JTextPane getArea() {
        return area;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public void setFontArea() {
        area.setFont(this.font);
    }

    public Font getFontArea() {
        return this.font;
    }

    public void setIsOpaque(boolean isOpaque) {
        this.isOpaque = isOpaque;
    }

    public boolean getIsOpaque() {
        return this.isOpaque;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return this.font;
    }

    public boolean checkOverlap() {
        if (start.x == end.x || start.y == end.y) {
            return true;
        } else {
            return false;
        }
    }

    public void draw(Graphics2D g2, Graphics g2d) {
        int[] a = {Math.min(start.x, end.x), Math.min(start.y, end.y), Math.max(start.x, end.x), Math.max(start.y, end.y)};
        int[] b = a;

        if (isCreated == false) {
            BasicStroke stroke = new BasicStroke(strokeThickness,endStrokeCap,lineStrokeJoin,dashPhase,
        dashArray,miterLimit);
            g2.setStroke(stroke);
            if (start != null && end != null) {

                g2.setColor(color);

                //draw rect...
                g2.setStroke(STROKE_1);
                g2.drawRect(a[0] - EDGE_SQUARE / 2, a[1] - EDGE_SQUARE / 2, EDGE_SQUARE, EDGE_SQUARE);
                g2.drawRect(a[2] - EDGE_SQUARE / 2, a[1] - EDGE_SQUARE / 2, EDGE_SQUARE, EDGE_SQUARE);
                g2.drawRect(a[2] - EDGE_SQUARE / 2, a[3] - EDGE_SQUARE / 2, EDGE_SQUARE, EDGE_SQUARE);
                g2.drawRect(a[0] - EDGE_SQUARE / 2, a[3] - EDGE_SQUARE / 2, EDGE_SQUARE, EDGE_SQUARE);
                g2.drawRect((a[0] + a[2]) / 2 - EDGE_SQUARE / 2, a[1] - EDGE_SQUARE / 2, EDGE_SQUARE, EDGE_SQUARE);
                g2.drawRect(a[2] - EDGE_SQUARE / 2, (a[1] + a[3]) / 2 - EDGE_SQUARE / 2, EDGE_SQUARE, EDGE_SQUARE);
                g2.drawRect((a[0] + a[2]) / 2 - EDGE_SQUARE / 2, a[3] - EDGE_SQUARE / 2, EDGE_SQUARE, EDGE_SQUARE);
                g2.drawRect(a[0] - EDGE_SQUARE / 2, (a[1] + a[3]) / 2 - EDGE_SQUARE / 2, EDGE_SQUARE, EDGE_SQUARE);

                g2.setStroke(STROKE_2);
                g2.drawLine(a[0] + EDGE_SQUARE / 2, a[1], (a[0] + a[2]) / 2 - EDGE_SQUARE / 2, a[1]);
                g2.drawLine((a[0] + a[2]) / 2 + EDGE_SQUARE / 2, a[1], a[2] - EDGE_SQUARE / 2, a[1]);
                g2.drawLine(a[2], a[1] + EDGE_SQUARE / 2, a[2], (a[1] + a[3]) / 2 - EDGE_SQUARE / 2);
                g2.drawLine(a[2], (a[1] + a[3]) / 2 + EDGE_SQUARE / 2, a[2], a[3] - EDGE_SQUARE / 2);
                g2.drawLine(a[2] - EDGE_SQUARE / 2, a[3], (a[0] + a[2]) / 2 + EDGE_SQUARE / 2, a[3]);
                g2.drawLine((a[0] + a[2]) / 2 - EDGE_SQUARE / 2, a[3], a[0] + EDGE_SQUARE / 2, a[3]);
                g2.drawLine(a[0], a[3] - EDGE_SQUARE / 2, a[0], (a[1] + a[3]) / 2 + EDGE_SQUARE / 2);
                g2.drawLine(a[0], (a[1] + a[3]) / 2 - EDGE_SQUARE / 2, a[0], a[1] + EDGE_SQUARE / 2);
            }
        }

        if (string.equals("") == false) {

            if (isOpaque) {
                g2d.setFont(font);
                g2d.setColor(fillColor);
                g2d.fillRect(a[0], a[1], a[2] - a[0], a[3] - a[1]);
                g2d.setColor(textColor);
                for (String line : string.split("\n")) {
                    g2d.drawString(line, start.x, start.y += g2d.getFontMetrics().getHeight());
                }
            } else {
                g2d.setFont(font);
                g2d.setColor(textColor);
                for (String line : string.split("\n")) {
                    g2d.drawString(line, a[0], a[1] += g2d.getFontMetrics().getHeight());
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d) {

    }
}
