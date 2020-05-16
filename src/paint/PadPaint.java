/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import paint.PaintTool.DrawMode;
import property.ColorDialog;
import property.StrokeState;
import property.TextPanel;
import shape.Bucket;
import shape.Curve;
import shape.DrawType;
import shape.Eraser;
import shape.Line;
import shape.Oval;
import shape.Pencil;
import shape.Picker;
import shape.Polygon;
import shape.Rectangle;
import shape.RightTriangle;
import shape.RoundRect;
import shape.SelectionShape;
import shape.Text;
import shape.Triangle;

/**
 *
 * @author hung
 */
public class PadPaint extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    /**
     * Creates new form PadPaint
     */
    private PaintTool paintTool = new PaintTool();
    private ColorDialog colorChooser = new ColorDialog();
    private Line line;
    private Rectangle rect;
    private Oval oval;
    private Pencil pencil;
    private Eraser eraser;
    private Curve curve;
    private Polygon polygon;
    private Point startPolygon = null;
    private Point endPolygon = null;
    //update by Khanh
    private Bucket bucket;
    private Picker picker;
    private Cursor cursor;
    private Cursor cursorOfPaint;
    private Cursor cursorOfPicker;
    private Cursor cursorOfEraser;
    private Cursor cursorOfBucket;
    private Cursor cursorOfZoom;
    private SelectionShape sel_rect;
    private Point locationEraser = new Point();
    private Text text;
    private TextPanel textPanel = new TextPanel();

    private StrokeState strokeState = new StrokeState();
    private Triangle triangle;
    private RightTriangle rightTriangle;
    private RoundRect roundRect;
    private PaintState paintState = new PaintState();
    private PaintState redoState = new PaintState();
    private BufferedImage buff_img, org_img, cpy_img;
    private Point start, end, temp;
    private Graphics2D g2d, g2;
    private Color strokeColor = Color.BLACK;
    private Color fillColor = Color.WHITE;
    private int width = 0;
    private int height = 0;
    private boolean isSaved = true;
    private double zoom = 1;
    private JLabel lbLocation;
    private JLabel lbSize;
    private int temp1;
    private int temp2;

    private boolean draggingMouse = false;
    private boolean startCurve;
    private boolean isMouseExit;
    private boolean startSelRect = false;

    public void setTextPanel(TextPanel textPanel) {
        this.textPanel = textPanel;
    }

    public void setPaintTool(PaintTool paintTool) {
        this.paintTool = paintTool;
    }

    public void setStrokeState(StrokeState strokeState) {
        this.strokeState = strokeState;
    }

    public void setColorChooser(ColorDialog colorChooser) {
        this.colorChooser = colorChooser;
    }

    public PaintState getListState() {
        return paintState;
    }

    public Cursor setCursor(String path, String nameCursor, int x, int y) {
        Toolkit toolKit = Toolkit.getDefaultToolkit();
        Image image = toolKit.getImage(getClass().getResource(path));

        Point point = new Point(x, y);
        Cursor cursor = toolKit.createCustomCursor(image, point, nameCursor);
        return cursor;
    }

    public boolean isSaving() {
        return isSaved;
    }

    public void setZoom(int z) {
        switch (z / 10) {
            case 2:
                zoom = 1.0 / 8;
                break;
            case 3:
                zoom = 1.0 / 6;
                break;
            case 4:
                zoom = 1.0 / 4;
                break;
            case 5:
                zoom = 1.0 / 2;
                break;
            case 6:
                zoom = 1.0;
                break;
            case 7:
                zoom = 2.0;
                break;
            case 8:
                zoom = 3.0;
                break;
            case 9:
                zoom = 4.0;
                break;
            case 10:
                zoom = 5.0;
                break;

        }
        this.setSize((int) (buff_img.getWidth() * zoom), (int) (buff_img.getHeight() * zoom));                //thay đổi kích thước panel cho phù hợp sau khi zoom
        this.revalidate();
    }

    public double getZoom() {
        return zoom;
    }

    public Point getPoint(Point location) {
        if (location == null) {
            return null;
        }
        Point p = new Point((int) (location.x / zoom), (int) (location.y / zoom));
        return p;
    }

    private boolean testHit(Point p) {
        if (sel_rect != null && sel_rect.getStartLocation() != null && sel_rect.getEndlocation() != null) {

            if (p.x < Math.min(sel_rect.getStartLocation().x, sel_rect.getEndlocation().x)
                    || p.x > Math.max(sel_rect.getStartLocation().x, sel_rect.getEndlocation().x)) {
                return false;
            } else if (p.y < Math.min(sel_rect.getStartLocation().y, sel_rect.getEndlocation().y)
                    || p.y > Math.max(sel_rect.getStartLocation().y, sel_rect.getEndlocation().y)) {

                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean testMousePressed(Point p, Point start, Point end) {
        int a[] = {Math.min(start.x, end.x), Math.min(start.y, end.y), Math.max(start.x, end.x), Math.max(start.y, end.y)};
        if (p.x > a[0] && p.x < a[2] && p.y > a[1] && p.y < a[3]) {
            return true;
        } else {
            return false;
        }
    }

    public PadPaint(int width, int height) {
        initComponents();
        line = new Line();
        rect = new Rectangle();
        oval = new Oval();
        pencil = new Pencil();
        triangle = new Triangle();
        polygon = new Polygon();
        rightTriangle = new RightTriangle();
        roundRect = new RoundRect();
        start = new Point(-1, -1);
        end = new Point(-1, -1);
        this.width = width;
        this.height = height;
        this.setSize(new Dimension(width, height));
        //Khoi tao anh
        org_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2d = (Graphics2D) org_img.getGraphics();
        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        //update by hung
        buff_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) buff_img.getGraphics();
        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(0, 0, width, height);
        g2.dispose();

        paintState.setData(org_img);
        initState();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        cursorOfPaint = setCursor("/icon/cursor/paint.png", "paint", 15, 15);
        cursorOfEraser = setCursor("/icon/cursor/paint.png", "eraser", 16, 16);
        cursorOfPicker = setCursor("/icon/cursor/picker.png", "picker", 7, 22);
        cursorOfBucket = setCursor("/icon/cursor/bucket.png", "bucket", 5, 4);
        cursorOfZoom = setCursor("/icon/cursor/zoom.png", "zoom", 16, 16);

    }

    public void cut() {
        if (sel_rect != null) {
            int[] data = sel_rect.getData();
            int w = sel_rect.getWidth();
            int h = sel_rect.getHeight();
            if (w == 0 || h == 0) {
                return;
            }
            cpy_img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            cpy_img.getRaster().setPixels(0, 0, w, h, data);
            Graphics2D g = (Graphics2D) buff_img.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(sel_rect.getStartOrigin().x, sel_rect.getStartOrigin().y, w, h);
            repaint();
            sel_rect = null;
            g.dispose();
        }

    }

    public void copy() {
        if (sel_rect != null) {
            int[] data = sel_rect.getData();
            int w = sel_rect.getWidth();
            int h = sel_rect.getHeight();
            if (w == 0 || h == 0) {
                return;
            }
            cpy_img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            cpy_img.getRaster().setPixels(0, 0, w, h, data);
        }

    }

    public void paste() {
        if (cpy_img != null) {
            Graphics2D g = (Graphics2D) buff_img.getGraphics();
            g.drawImage(cpy_img, temp.x, temp.y, null);
            g.dispose();
        }
        repaint();
        sel_rect = null;
        cpy_img = null;
    }

    public void delete() {
        if (sel_rect != null) {
            int[] data = sel_rect.getData();
            int w = sel_rect.getWidth();
            int h = sel_rect.getHeight();
            if (w == 0 || h == 0) {
                return;
            }
            Graphics2D g = (Graphics2D) buff_img.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(sel_rect.getStartOrigin().x, sel_rect.getStartOrigin().y, w, h);
            repaint();
            sel_rect = null;
            g.dispose();
        }
    }

    public void toolChange() {
        //Neu polygon chua hoan thanh ma dachon cong cu khac thi se hoan thanh polygon
        if (startPolygon != null) {
            if (!paintTool.getDrawMode().equals(DrawMode.POLYGON)) {

                //Tao mot duong thang moi
                line = new Line();
                //Noi diem cuoi vao diem dau
                line.setPoint(getPoint(end), getPoint(startPolygon));
                line.addDraggedPoint(getPoint(end));
                line.addDraggedPoint(getPoint(startPolygon));
                line.setStrokeColor(colorChooser.getStrokeColor());
                line.setStroke(strokeState.getStroke());
                paintState.addDrawState(line);
                paintState.addDrawStep(PaintState.PAINTTING);
                polygon.addElement(line);
                line.draw(g2d);
            }
            startPolygon = null;
            line = null;
            polygon = null;
            start = null;
            end = null;
        } else if (startCurve == true) {   //hinh curve dang ton tai ma co su thay doi thi se hoan thien curve
            if (paintTool.getDrawMode() == DrawMode.CURVE) { //Neu hinh curve van dang duoc chon, nghia la khong chon tool khac thi se chi them vao ma khong ve
                curve.draw(g2d);
                paintState.addDrawState(curve);
                paintState.addDrawStep(PaintState.PAINTTING);
                curve = null;
                startCurve = false;
                start = null;
                end = null;
            } else {   //Neu chon tool khac thi se ve curve len buffer
                curve.draw(g2d);
                repaint();
                paintState.addDrawState(curve);
                paintState.addDrawStep(PaintState.PAINTTING);
                curve = null;
                startCurve = false;
                start = null;
                end = null;
            }
        } else if (startSelRect == true) {    //Hinh selrect vua moi duoc chon
            if (sel_rect.isCreating()) {  //Neu ma anh da duoc tao thi se luu anh
                sel_rect.setSelected(true);
                sel_rect.draw(g2d);
                paintState.addDrawState(sel_rect);
                paintState.addDrawStep(PaintState.PAINTTING);
                sel_rect = null;
                startSelRect = false;
                start = null;
                end = null;
            }
        }

        repaint();
    }

    public void initState() {
        Pencil pencil = new Pencil();
        pencil.addDraggedPoint(new Point(-1, -1));
        pencil.addDraggedPoint(new Point(-1, -1));
        pencil.setPoint(new Point(-1, -1), new Point(-1, -1));
        BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                1.0f, null, 2.0f);
        paintState.addDrawState(pencil);
        paintState.addDrawStep(PaintState.PAINTTING);
    }

    public BufferedImage getBuffer() {
        return buff_img;
    }

    public void refresh() {
        //Trong qua trinh ve anh goc can co cac tham so de chi ra duoc anh goc bi quay tai mot buoc ve nao do
        g2d.drawImage(org_img, 0, 0, this);
        repaint();

    }

    public void addDrawStep(int drawStep) {
        paintState.addDrawStep(drawStep);
    }

    public void undo() {
        toolChange();
        if (paintState.isEmpty()) {
            return;
        }
        //Lay ra trang thai cuoi de tro lai trang thai truoc do
        int stepState = paintState.removeEndStep();
        redoState.addDrawStep(stepState);
        //lay ra trang thai cua buoc ve cuoi cung
        switch (stepState) {
            case PaintState.ROTATE_RIGHT:
                //Neu anh vua moi duoc quay phai thi se quay trai tro lai
                rotate(-90);
                break;
            case PaintState.ROTATE_LEFT:
                //Neu anh vua moi duoc quay trai thi se quay phai tro lai
                rotate(90);

                break;
            case PaintState.ROTATE_REVERSE:
                //Neu anh vua moi duoc quay nguoc thi quay nguoc tro lai
                rotate(180);
                break;
            case PaintState.V_FLIP:
                //Neu anh vua moi doc lat nguoc thi lat nguoc anh tro lai
                flipping(1);
                break;
            //Neu anh vua moi duoc lat ngang thi lat ngang tro lai
            case PaintState.H_FLIP:
                flipping(2);
                break;
            case PaintState.PAINTTING:
                //Neu la painting thi ve lai tu dau den trang thai truoc do
                //Ve lai anh goc
                //khoi tao mot buffer moi de ve len panel
                buff_img = new BufferedImage(org_img.getWidth(), org_img.getHeight(), BufferedImage.TYPE_INT_RGB);
                g2d = (Graphics2D) buff_img.getGraphics();
                refresh();
                //Xoa trang thai cuoi
                DrawType drawType = paintState.removeEndShape();
                redoState.addDrawState(drawType);
                //Ve lai toan bo trang thai cua anh tu luc dau den luc 
                int shapeIndex = 0;
                for (int i = 0; i < paintState.getDrawStepList().size(); i++) {
                    int inStepState = paintState.getDrawStepList().get(i);
                    //Lay tung trang thia cua buoc ve
                    switch (inStepState) {
                        case PaintState.ROTATE_RIGHT:
                            rotate(90);
                            break;
                        case PaintState.ROTATE_LEFT:
                            rotate(-90);
                            break;
                        case PaintState.ROTATE_REVERSE:
                            rotate(180);
                            break;
                        case PaintState.V_FLIP:
                            flipping(1);
                            break;
                        case PaintState.H_FLIP:
                            flipping(2);
                            break;
                        case PaintState.PAINTTING:
                            //Neu la painting thi se ve lai toan bo anh tu dau
                            DrawType inDrawType = paintState.getListState().get(shapeIndex);
                            if (inDrawType instanceof Line) {
                                Line inLine = (Line) inDrawType;
                                inLine.draw(g2d);
                            } else if (inDrawType instanceof Triangle) {
                                Triangle inTriangle = (Triangle) inDrawType;
                                inTriangle.draw(g2d);
                            } else if (inDrawType instanceof RightTriangle) {
                                RightTriangle inRightTriangle = (RightTriangle) inDrawType;
                                inRightTriangle.draw(g2d);
                            } else if (inDrawType instanceof Rectangle) {
                                Rectangle inRect = (Rectangle) inDrawType;
                                inRect.draw(g2d);
                            } else if (inDrawType instanceof RoundRect) {
                                RoundRect inRoundRect = (RoundRect) inDrawType;
                                inRoundRect.draw(g2d);
                            } else if (inDrawType instanceof Oval) {
                                Oval inOval = (Oval) inDrawType;
                                inOval.draw(g2d);
                            } else if (inDrawType instanceof Polygon) {
                                Polygon inPolygon = (Polygon) inDrawType;
                                inPolygon.draw(g2d);
                            } else if (inDrawType instanceof Curve) {
                                Curve inCurve = (Curve) inDrawType;
                                inCurve.draw(g2d);

                            } else if (inDrawType instanceof SelectionShape) {
                                SelectionShape inselrect = (SelectionShape) inDrawType;
                                inselrect.draw(g2d);
                            } else if (inDrawType instanceof Pencil) {
                                Pencil inPencil = (Pencil) inDrawType;
                                for (int j = 1; j < inPencil.getDraggedPoint().size(); j++) {
                                    inPencil.setPoint(inPencil.getDraggedPoint().get(j - 1), inPencil.getDraggedPoint().get(j));
                                    inPencil.draw(g2d);
                                }

                            } //update by Khanh
                            else if (inDrawType instanceof Bucket) {
                                Bucket inBucket = (Bucket) inDrawType;
                                inBucket.draw(buff_img);
                            }
                            shapeIndex++;
                            break;
                    }
                }

                break;
        }

        repaint();
    }

    public void redo() {
        if (!redoState.isEmpty()) {
            int stepState = redoState.removeEndStep();
            paintState.addDrawStep(stepState);
            DrawType drawType0 = redoState.removeEndShape();
            paintState.addDrawState(drawType0);
            buff_img = new BufferedImage(org_img.getWidth(), org_img.getHeight(), BufferedImage.TYPE_INT_RGB);
            g2d = (Graphics2D) buff_img.getGraphics();
            refresh();
            //Ve lai trang thai truoc do
            int shapeIndex = 0;
            for (int i = 0; i < paintState.getDrawStepList().size(); i++) {
                int inStepState = paintState.getDrawStepList().get(i);
                //Lay tung trang thia cua buoc ve
                switch (inStepState) {
                    case PaintState.ROTATE_RIGHT:
                        rotate(90);
                        break;
                    case PaintState.ROTATE_LEFT:
                        rotate(-90);
                        break;
                    case PaintState.ROTATE_REVERSE:
                        rotate(180);
                        break;
                    case PaintState.V_FLIP:
                        flipping(1);
                        break;
                    case PaintState.H_FLIP:
                        flipping(2);
                        break;
                    case PaintState.PAINTTING:
                        DrawType inDrawType = paintState.getListState().get(shapeIndex);
                        if (inDrawType instanceof Line) {
                            Line inLine = (Line) inDrawType;
                            inLine.draw(g2d);
                        } else if (inDrawType instanceof Triangle) {
                            Triangle inTriangle = (Triangle) inDrawType;
                            inTriangle.draw(g2d);
                        } else if (inDrawType instanceof RightTriangle) {
                            RightTriangle inRightTriangle = (RightTriangle) inDrawType;
                            inRightTriangle.draw(g2d);
                        } else if (inDrawType instanceof Rectangle) {
                            Rectangle inRect = (Rectangle) inDrawType;
                            inRect.draw(g2d);
                        } else if (inDrawType instanceof RoundRect) {
                            RoundRect inRoundRect = (RoundRect) inDrawType;
                            inRoundRect.draw(g2d);
                        } else if (inDrawType instanceof Oval) {
                            Oval inOval = (Oval) inDrawType;
                            inOval.draw(g2d);
                        } else if (inDrawType instanceof Polygon) {
                            Polygon inPolygon = (Polygon) inDrawType;
                            inPolygon.draw(g2d);

                        } else if (inDrawType instanceof SelectionShape) {
                            SelectionShape inselrect = (SelectionShape) inDrawType;
                            inselrect.draw(g2d);
                        } else if (inDrawType instanceof Curve) {
                            Curve inCurve = (Curve) inDrawType;
                            inCurve.draw(g2d);
                        } else if (inDrawType instanceof Pencil) {
                            Pencil inPencil = (Pencil) inDrawType;
                            for (int j = 1; j < inPencil.getDraggedPoint().size(); j++) {
                                inPencil.setPoint(inPencil.getDraggedPoint().get(j - 1), inPencil.getDraggedPoint().get(j));
                                inPencil.draw(g2d);
                            }

                        } //update by Khanh
                        else if (inDrawType instanceof Bucket) {
                            Bucket inBucket = (Bucket) inDrawType;
                            inBucket.draw(buff_img);
                        }
                        shapeIndex++;
                }

                repaint();
            }
        }
    }

    public void setLocationStatus(JLabel lbLocation) {
        this.lbLocation = lbLocation;
    }

    public void setSizeStatus(JLabel lbSize) {
        this.lbSize = lbSize;
    }

    public void setSizeStatusInfo() {
        lbSize.setText(buff_img.getWidth() + ", " + buff_img.getHeight() + "px");
    }

    public void flush() {
        start = null;
        end = null;
        polygon = null;
        curve = null;
        paintState.removeAll();
        initState();
        redoState.removeAll();
        org_img.flush();
        buff_img.flush();
        System.gc();
        org_img = null;
        buff_img = null;
        org_img = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) org_img.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.dispose();
        paintState.setData(org_img);
        isSaved = true;
        refresh();
        repaint();
    }

    public void loadImage(BufferedImage img) {
        //Khi anh vua moi duoc mo thi khong can phai luu
        isSaved = true;
        setZoom(60);
        loadImage((Image) img);
    }

    public void loadImage(java.awt.Image img) {
        if (img != null) {
            flush();
            org_img = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            g2 = (Graphics2D) org_img.getGraphics();
            g2.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), this);
            g2.dispose();
            buff_img = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            g2 = (Graphics2D) buff_img.getGraphics();
            g2.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), this);
            g2.dispose();
            paintState.setData(org_img);
            g2d = (Graphics2D) buff_img.getGraphics();

            this.setSize(new Dimension(org_img.getWidth(), org_img.getHeight()));
            this.setMinimumSize(new Dimension(org_img.getWidth(), org_img.getHeight()));
            this.setMaximumSize(new Dimension(org_img.getWidth(), org_img.getHeight(null)));
            this.revalidate();
            repaint();
        }
    }

    public void saveImage(File f, String extension) {
        try {
            ImageIO.write(buff_img, extension, f);
            isSaved = true;
        } catch (IOException ex) {
            isSaved = false;
        }
    }

    public void flipping(int typeFlip) {
        AffineTransform tx = new AffineTransform();
        if (startSelRect == true) { //Neu dang chon sel_rect
            if (sel_rect.isCreating()) {  //Neu anh da duoc tao
                if (sel_rect.isSelected() == false) {  //Neu anh chua duoc co dinh tren Buffer
                    int w = sel_rect.getWidth();
                    int h = sel_rect.getHeight();
                    if (typeFlip == 1) {   //lat nguoc anh
                        tx = AffineTransform.getScaleInstance(1, -1);
                        tx.translate(0, -h);
                    } else if (typeFlip == 2) { //Lat ngang anh
                        tx = AffineTransform.getScaleInstance(-1, 1);
                        tx.translate(-w, 0);
                    }
                    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    int[] data = sel_rect.getData();
                    BufferedImage tempImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                    tempImage.getRaster().setPixels(0, 0, w, h, data);
                    tempImage = op.filter(tempImage, null);
                    //Thiet lap lai anh cho sel_rect
                    sel_rect.setImage(tempImage);
                }
            }
        } else {
            if (typeFlip == 1) {   //lat nguoc anh
                paintState.addDrawStep(PaintState.V_FLIP);
                tx = AffineTransform.getScaleInstance(1, -1);
                tx.translate(0, -buff_img.getHeight());
            } else if (typeFlip == 2) { //Lat ngang anh
                paintState.addDrawStep(PaintState.H_FLIP);
                tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-buff_img.getWidth(), 0);
            }
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            buff_img = op.filter(buff_img, null);
            g2d = (Graphics2D) buff_img.getGraphics();
        }
        repaint();
    }

    public void rotate(int alpha) {

        if (startSelRect == true) { //Neu dang chon sel_rect
            if (sel_rect.isCreating()) {  //Neu anh da duoc tao
                if (sel_rect.isSelected() == false) {  //Neu anh chua duoc co dinh tren Buffer
                    int w = sel_rect.getWidth();
                    int h = sel_rect.getHeight();
                    AffineTransform tx = new AffineTransform();
                    if (alpha == 90) {
                        tx.translate(h, 0);
                        tx.rotate(alpha * Math.PI / 180.0, 0, 0);
                    } else if (alpha == -90) {
                        tx.translate(0, w);
                        tx.rotate(alpha * Math.PI / 180, 0, 0);
                    } else if (alpha == 180) {
                        tx.translate(w, h);
                        tx.rotate(alpha * Math.PI / 180, 0, 0);
                    }
                    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

                    int[] data = sel_rect.getData();

                    BufferedImage tempImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                    tempImage.getRaster().setPixels(0, 0, w, h, data);
                    tempImage = op.filter(tempImage, null);

                    //Thiet lap lai anh cho sel_rect
                    sel_rect.setImage(tempImage);
                }
            }
        } else {
            AffineTransform tx = new AffineTransform();
            if (alpha == 90) {
                paintState.addDrawStep(PaintState.ROTATE_RIGHT);
                tx.translate(buff_img.getHeight(), 0);
                tx.rotate(alpha * Math.PI / 180.0, 0, 0);
            } else if (alpha == -90) {
                paintState.addDrawStep(PaintState.ROTATE_LEFT);
                tx.translate(0, buff_img.getWidth());
                tx.rotate(alpha * Math.PI / 180, 0, 0);
            } else if (alpha == 180) {
                paintState.addDrawStep(PaintState.ROTATE_REVERSE);
                tx.translate(buff_img.getWidth(), buff_img.getHeight());
                tx.rotate(alpha * Math.PI / 180, 0, 0);
            }
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

            toolChange();
            buff_img = op.filter(buff_img, null);
            this.setSize(new Dimension((int) (buff_img.getWidth() * zoom), (int) (buff_img.getHeight() * zoom)));
            g2d = (Graphics2D) buff_img.getGraphics();
        }
        repaint();
    }

    public void drawZoomImage(Graphics g) {
        if (zoom != 1) {
            return;
        }
        if (end.x - 40 < 0) {
            end.x = 40;
        } else if (end.x + 80 > buff_img.getWidth()) {
            end.x = buff_img.getWidth() - 80;
        }

        if (end.y - 40 < 0) {
            end.y = 40;
        } else if (end.y + 80 > buff_img.getHeight()) {
            end.y = buff_img.getHeight() - 80;
        }
        BufferedImage subImage = buff_img.getSubimage(end.x - 40, (end.y - 40),
                80, 80);
        Graphics2D g2z = (Graphics2D) g;
        g2z.scale(3, 3);
        //Xac dinh vi tri ve anh
        g2z.drawImage(subImage, null, (int) (end.x / 3 - 40), (int) ((end.y - 120) / 3));
        repaint();
    }
    //Lay g2d ve thi se bi, nhung ma lay g2 ve thi se khong bi
//<editor-fold defaultstate="collapsed" desc="paint">

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        if (buff_img == null) {
            buff_img = (BufferedImage) createImage(getSize().width, getSize().height);
            g2d = (Graphics2D) buff_img.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            refresh();
        }
        setSizeStatusInfo();
        g2.scale(zoom, zoom);
        g2.drawImage(buff_img, null, 0, 0);
        if (start != null && end != null) {
            switch (paintTool.getDrawMode()) {
                case LINE:
                    line.draw(g2);
                    break;
                case TRIANGLE:
                    triangle.draw(g2);
                    break;
                case RIGHTTRIANGLE:
                    rightTriangle.draw(g2);
                    break;
                case RECT:
                    rect.draw(g2);
                    break;
                case ROUNDRECT:
                    roundRect.draw(g2);
                    break;
                case OVAL:
                    oval.draw(g2);
                case PENCIL:
                    pencil.draw(g2);
                    break;
                case BUCKET:
                    bucket.draw(buff_img);
                    break;
                case CURVE:
                    if (curve != null) {
                        curve.draw(g2);
                    }
                    break;
                case POLYGON:
                    line.draw(g2);
                    break;
                case SEL:
                    if (sel_rect != null) {
                        sel_rect.draw(g2);
                    }
                    break;
                case TEXT:
                    if (text != null) {
                        text.draw(g2, g2d);
                    }
                    break;
                case ZOOM:
                    drawZoomImage(g);
                    break;
            }

        };

    }
//</editor-fold>

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        lbLocation.setText("");
        if (paintTool.getDrawMode() == DrawMode.ZOOM && startPolygon == null) {
            start = null;
            end = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        //Xóa hết hình tồn tại troang redoStack
        if (!redoState.isEmpty()) {
            redoState.removeAll();
        }
        start = e.getPoint();
        temp = e.getPoint();

        switch (paintTool.getDrawMode()) {
            //<editor-fold defaultstate="collapsed" desc="#">
            case LINE:
                line = new Line();
                line.setStroke(strokeState.getStroke());
                //set màu cho stroke
                line.setStrokeColor(colorChooser.getStrokeColor());
                //Thêm điểm đầu vào danh sách điểm di chuột
                line.addDraggedPoint(getPoint(start));
                //Thêm điểm vào để vẽ
                line.setPoint(getPoint(start), getPoint(start));
            case RECT:
                rect = new Rectangle();
                rect.setStroke(strokeState.getStroke());
                rect.setStrokeColor(colorChooser.getStrokeColor());
                rect.setFillColor(colorChooser.getFillColor());
                rect.addDraggedPoint(getPoint(start));
                rect.setPoint(getPoint(start), getPoint(start));
                break;
            case ROUNDRECT:
                roundRect = new RoundRect();
                roundRect.setStroke(strokeState.getStroke());
                roundRect.setStrokeColor(colorChooser.getStrokeColor());
                roundRect.setFillColor(colorChooser.getFillColor());
                roundRect.addDraggedPoint(getPoint(start));
                roundRect.setPoint(getPoint(start), getPoint(start));
                break;
            case TRIANGLE:
                triangle = new Triangle();
                triangle.setStroke(strokeState.getStroke());
                triangle.setStrokeColor(colorChooser.getStrokeColor());
                triangle.setFillColor(colorChooser.getFillColor());
                triangle.addDraggedPoint(getPoint(start));
                triangle.setPoint(getPoint(start), getPoint(start));
                break;
            case RIGHTTRIANGLE:

                rightTriangle = new RightTriangle();
                rightTriangle.setStroke(strokeState.getStroke());
                rightTriangle.setStrokeColor(colorChooser.getStrokeColor());
                rightTriangle.setFillColor(colorChooser.getFillColor());
                rightTriangle.addDraggedPoint(getPoint(start));
                rightTriangle.setPoint(getPoint(start), getPoint(start));
                break;
            case OVAL:
                oval = new Oval();
                oval.setStroke(strokeState.getStroke());
                oval.setStrokeColor(colorChooser.getStrokeColor());
                oval.setFillColor(colorChooser.getFillColor());
                oval.setPoint(getPoint(start), getPoint(start));
                oval.addDraggedPoint(getPoint(start));
                break;
            case PENCIL:
                pencil = new Pencil();
                pencil.setStroke(strokeState.getStroke());
                pencil.setStrokeColor(colorChooser.getStrokeColor());
                pencil.setPoint(getPoint(start), getPoint(start));
                pencil.addDraggedPoint(getPoint(start));
                pencil.draw(g2d);
                break;
            case BUCKET:
                bucket = new Bucket();
                bucket.setStart(getPoint(start));
                bucket.setArrPoint(getPoint(start));
                bucket.setColor(colorChooser.getFillColor());
                bucket.draw(buff_img);
                paintState.addDrawState(bucket);
                break;
            case ERASER:
                eraser = new Eraser();
                eraser.setStroke(strokeState.getStroke());
                eraser.setStrokeColor(colorChooser.getFillColor());
                eraser.setPoint(getPoint(start), getPoint(start));
                eraser.addDraggedPoint(getPoint(start));
                eraser.draw(g2d);
                locationEraser.move((int) (e.getPoint().x / zoom), (int) (e.getPoint().y / zoom));
                break;

            case POLYGON:
                draggingMouse = false;
                if (polygon == null) { //Neu polygon chua duoc khoi tao thi tao moi

                    polygon = new Polygon();

                    startPolygon = e.getPoint();//set luôn điểm đầu
                }

                //  polygon.setStrokeColor(colorChooser.getStrokeColor());
                //   polygon.addElement(line);
                line = new Line();
                line.setStrokeColor(colorChooser.getStrokeColor());

                line.setStroke(strokeState.getStroke());

                if (end != null) {  //Da ton tai hinh tren panel 
                    //thi toa do ban dau khong phai la toa do vua nhan
                    //ma la toa do da tha chuot truoc do
                    //Lưu lại vị tri vừa nhấn chuột
                    Point agent = new Point(start.x, start.y);
                    //end la diem dau, start la diem cuoi
                    start = end;
                    line.setPoint(getPoint(start), getPoint(agent));
                    //Lay toa do vua tha chuot lan truoc lam toa do dau
                    line.addDraggedPoint(getPoint(end));
                    //them diem dau cua duong ve truoc
                    line.addDraggedPoint(agent);
                } else {    //tool Polygon vua moi duoc chon
                    line.setPoint(getPoint(start), getPoint(start));
                    //them diem dau cua duong ve truoc
                    line.addDraggedPoint(start);
                }
                //   line.addDraggedPoint(getPoint(start));
                break;
            //</editor-fold>
            case CURVE:

                if (curve == null) {
                    //Neu cung chua duoc tao thi tao mot cung moi
                    curve = new Curve();
                    startCurve = true;
                    curve.setStroke(strokeState.getStroke());
                    curve.setStrokeColor(colorChooser.getStrokeColor());
                    curve.setStartPoint(getPoint(start));
                }
                if (curve.getState() == 1) {
                    //Neu cung chi vua moi duoc tao thi se set lai diem dau tien trong danh sach diem
                    curve.getList().get(0).setLocation(getPoint(e.getPoint()));
                    //Them diem vao danh sach
                } else if (curve.getState() == 2) {
                    curve.getList().get(2).setLocation(getPoint(e.getPoint()));

                } else if (curve.getState() == 3) {
                    curve.getList().get(2).setLocation(getPoint(e.getPoint()));
                }
                curve.addDraggedPoint(getPoint(start));
                curve.addPointToState(getPoint(start));
                break;
            //<editor-fold defaultstate="collapsed" desc="#">
            case SEL:
                if (sel_rect != null) {
                    if (sel_rect.isCreating()) {   //Neu anh da duoc khoi tao
                        if (!testHit(getPoint(getPoint(start)))) {
                            sel_rect.setSelected(true);
                            end = null;
                            start = null;
                            sel_rect.draw(g2d);
                            paintState.addDrawState(sel_rect);
                            paintState.addDrawStep(PaintState.PAINTTING);
                            repaint();
                            sel_rect = null;
                            startSelRect = false;
                        } else {
                            temp1 = sel_rect.getStartLocation().x - getPoint(start).x;
                            temp2 = sel_rect.getStartLocation().y - getPoint(start).y;
                            sel_rect.addDraggedPoint(getPoint(new Point(start.x - temp1, start.y - temp2)));
                            return;
                        }
                    }
                }
                if (sel_rect == null) {
                    sel_rect = new SelectionShape();
                    startSelRect = true;    //Danh dau la da duoc tao
                    //    sel_rect.setPoint(getPoint(start), getPoint(start));
                    //Them vao toa do goc cho anh
                    start = e.getPoint();
                    sel_rect.setStartOrigin(getPoint(start));
                    sel_rect.setStart(getPoint(start));
                }
                break;

            case TEXT:
                if (text != null) {
                    text.setIsCreated(true);
                    if (testMousePressed(text.getStart(), text.getEnd(), e.getPoint()) == false) {
                        if (text.checkOverlap() == false) {
                            text.setString();
                            if (text.getString().equals("") == false) {
                                repaint();
                            }
                        }
                        text.removeArea(this);
                    }
                    return;
                } else {
                    text = new Text();
                    text.setStart(e.getPoint());
                    text.setIsCreated(false);
                    return;
                }
            //</editor-fold>

        }

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (paintTool.getDrawMode()) {
            //<editor-fold defaultstate="collapsed" desc="#">
            case LINE:
                paintState.addDrawState(line);
                line.draw(g2d);
                break;
            case RECT:
                paintState.addDrawState(rect);
                rect.draw(g2d);
                break;
            case ROUNDRECT:
                paintState.addDrawState(roundRect);
                roundRect.draw(g2d);
                break;
            case TRIANGLE:
                paintState.addDrawState(triangle);
                triangle.draw(g2d);
                break;
            case RIGHTTRIANGLE:
                paintState.addDrawState(rightTriangle);
                rightTriangle.draw(g2d);
                break;
            case OVAL:
                paintState.addDrawState(oval);
                oval.draw(g2d);
                break;
            case ERASER:
                paintState.addDrawState(eraser);
                eraser.draw(g2d);
                locationEraser.move((int) (e.getPoint().x / zoom), (int) (e.getPoint().y / zoom));
                break;
            case PENCIL:
                pencil.setPoint(pencil.getDraggedPoint().get(0), pencil.getDraggedPoint().get(0));
                paintState.addDrawState(pencil);
                pencil.draw(g2d);
                break;

            case PICKER:
                picker = new Picker();
                picker.setColor(new Color(buff_img.getRGB(start.x, start.y)));
                colorChooser.setColorPicker(picker.getColor());
                break;
            case POLYGON:

                end = e.getPoint();
                paintState.addDrawState(line);
                paintState.addDrawStep(PaintState.PAINTTING);
                //Neu ma khong keo chuot thi se set diem cho line o day
                if (draggingMouse == false) {
                    line.setPoint(getPoint(start), getPoint(end));
                    line.addDraggedPoint(getPoint(end));
                }

                line.draw(g2d);
                return; //Tranh lam cho end = null
            case ZOOM:
                return;
            //</editor-fold>
            case CURVE:
                if (curve != null) {
                    end = e.getPoint();
                    if (curve.getState() == 1) {
                        curve.getList().get(3).setLocation(getPoint(e.getPoint()));
                        curve.incState();
                        curve.addDraggedPoint(getPoint(end));
                        curve.addPointToState(getPoint(end));
                        return;
                    } else if (curve.getState() == 2) {
                        curve.getList().get(1).setLocation(getPoint(e.getPoint()));
                        curve.getList().get(2).setLocation(getPoint(e.getPoint()));
                        curve.incState();
                        curve.addDraggedPoint(getPoint(end));
                        curve.addPointToState(getPoint(end));
                        return;
                    } else if (curve.getState() == 3) {
                        paintState.addDrawState(curve);
                        curve.getList().get(2).setLocation(getPoint(e.getPoint()));
                        curve.draw(g2d);
                        curve.addDraggedPoint(getPoint(end));
                        curve.addPointToState(getPoint(end));   //Phan them buoc ve se cho xuong phia duoi
                        curve = null;
                        startCurve = false;
                    }

                }
                break;
            //<editor-fold defaultstate="collapsed" desc="#">    
            case SEL:
                if (sel_rect != null) {
                    //Neu anh da duoc chon thi se ve len buffer
                    if (!sel_rect.isCreating()) //Neu anh da duoc tao thi se kiem tra xem anh da duoc chon hay chua                           
                    {
                        if (sel_rect.isDragging()) {
                          
                            sel_rect.setEndOrigin(getPoint(end));
                           
                            
                            //set lai la da tao anh
                            sel_rect.setIsCreating(true);
                            //Tao anh moi dua theo diem dau va diem cuoi
                            sel_rect.setImage(buff_img.getSubimage(Math.min(sel_rect.getStartOrigin().x, sel_rect.getEndOrigin().x),
                                    Math.min(sel_rect.getStartOrigin().y, sel_rect.getEndOrigin().y),
                                    Math.abs(sel_rect.getStartOrigin().x - sel_rect.getEndOrigin().x),
                                    Math.abs(sel_rect.getStartOrigin().y - sel_rect.getEndOrigin().y)));

                            sel_rect.setIsDragging(false);
                        }
                    }

                }
                return;
            case TEXT:
                if (text != null) {
                    Font font = textPanel.getFont();
                    text.setEnd(e.getPoint());
                    text.setArea(this);
                    text.getArea().setFont(font);
                    text.getArea().setOpaque(textPanel.getIsOpaque());
                    text.setIsOpaque(textPanel.getIsOpaque());
                    text.getArea().setForeground(colorChooser.getStrokeColor());
                    text.setTextColor(colorChooser.getStrokeColor());
                    text.setFont(font);
                    text.setFillColor(colorChooser.getFillColor());
                    System.out.println("released: " + text.getEnd().x + ", " + text.getEnd().y);
                    repaint();
                    if (text.getIsCreated() == true) {
                        text.removeArea(this);
                        text = null;
                    }
                }
                return;
        }//</editor-fold>
        paintState.addDrawStep(PaintState.PAINTTING);
        start = null;
        end = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //chỉ khi dragged chuột thì mới cập nhật hình lên bufer==>Lúc này mới ccaafn phải lưu ảnh
        isSaved = false;
        lbLocation.setText(getPoint(e.getPoint()).x + ", " + getPoint(e.getPoint()).y + "px");
        end = e.getPoint();
        switch (paintTool.getDrawMode()) {
            //<editor-fold defaultstate="collapsed" desc="#">
            case LINE:
                line.setPoint(getPoint(start), getPoint(end));
                line.addDraggedPoint(getPoint(end));
                break;
            case RECT:
                rect.setPoint(getPoint(start), getPoint(end));
                rect.addDraggedPoint(getPoint(end));
                break;
            case ROUNDRECT:
                roundRect.setPoint(getPoint(start), getPoint(end));
                roundRect.addDraggedPoint(getPoint(end));
                break;
            case TRIANGLE:
                triangle.setPoint(getPoint(start), getPoint(end));
                triangle.addDraggedPoint(getPoint(end));
                break;
            case RIGHTTRIANGLE:
                rightTriangle.setPoint(getPoint(start), getPoint(end));
                rightTriangle.addDraggedPoint(getPoint(end));
                break;
            case OVAL:
                oval.setPoint(getPoint(start), getPoint(end));
                oval.addDraggedPoint(getPoint(end));
                break;
            case ERASER:
                eraser.setPoint(getPoint(start), getPoint(end));
                eraser.addDraggedPoint(getPoint(end));
                start = end;
                eraser.draw(g2d);
                locationEraser.move((int) (e.getPoint().x / zoom), (int) (e.getPoint().y / zoom));
                break;
            case PENCIL:
                pencil.setPoint(getPoint(start), getPoint(end));
                pencil.addDraggedPoint(getPoint(end));
                start = end;
                pencil.draw(g2d);
                break;
            case POLYGON:
                draggingMouse = true;
                line.setPoint(getPoint(start), getPoint(end));
                line.addDraggedPoint(getPoint(end));
                break;
            case CURVE:
                if (curve != null) {
                    if (curve.getState() == 1) {
                        curve.getList().get(3).setLocation(getPoint(e.getPoint()));
                    } else if (curve.getState() == 2) {
                        curve.getList().get(2).setLocation(getPoint(e.getPoint()));
                    } else if (curve.getState() == 3) {
                        curve.getList().get(2).setLocation(getPoint(e.getPoint()));
                    }
                    curve.addDraggedPoint(getPoint(end));
                    curve.addPointToState(getPoint(end));

                }
                break;
            case SEL:
                if (sel_rect != null) {
                    if (sel_rect.isCreating()) {
                        sel_rect.setStart(getPoint(new Point(end.x + temp1, end.y + temp2)));
                        sel_rect.addDraggedPoint(getPoint(new Point(end.x + temp1, end.y + temp2)));

                    } else {
                        sel_rect.setPoint(getPoint(new Point(Math.min(start.x,end.x),Math.min(start.y, end.y))),
                                getPoint(new Point(Math.max(start.x,end.x),Math.max(start.y,end.y))));
                        sel_rect.setIsDragging(true);
                    }
                }

                break;

            case TEXT:
                if (text != null) {
                    text.setEnd(getPoint(e.getPoint()));

                }
                repaint();
                return;
        }//</editor-fold>
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        lbLocation.setText(getPoint(e.getPoint()).x + ", " + getPoint(e.getPoint()).y + "px");

        if (paintTool.getDrawMode() == DrawMode.ZOOM) {
            setCursor(cursorOfZoom);
            end = getPoint(e.getPoint());
            start = end;
            repaint();
        } else if (paintTool.getDrawMode() == DrawMode.PICKER) {
            setCursor(cursorOfPicker);
        } else if (paintTool.getDrawMode() == DrawMode.ERASER) {
            isMouseExit = false;
            locationEraser.move((int) (e.getPoint().x / zoom), (int) (e.getPoint().y / zoom));
            repaint();
            setCursor(cursorOfEraser);
        } else if (paintTool.getDrawMode() == DrawMode.SEL) {
            if (testHit(getPoint(e.getPoint())) == true) {
                setCursor(new Cursor(Cursor.MOVE_CURSOR));
            } else {
                setCursor(cursorOfPaint);
            }
        } else if (paintTool.getDrawMode() == DrawMode.BUCKET) {
            setCursor(cursorOfBucket);
        } else {
            setCursor(cursorOfPaint);
        }
    }
}
