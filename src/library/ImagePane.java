/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author hung
 */
public class ImagePane extends JPanel {

    private int rows = 3;
    private int cols = 5;
    private int currrow;
    private int currcol;
    private ImageCell[][] imageCells = new ImageCell[rows][cols];
    private ImageIcon[][] images = new ImageIcon[rows][cols];
    private BufferedImage buff_img;
    private Graphics2D g2d, g2;
    private String org_path = "data/Library/";
    private String[] library = new String[7];
    private int specify = -1;
    private BufferedImage selectedImage;
    private Point location;
    private ArrayList<ImageCell> storedImage;
    private boolean[] isloadeds = new boolean[7];   //Su dung mot mang boolean de kiem tra xem anh da duoc load tu file ra chua
    private JProgressBar progressBar;
    /**
     * Creates new form ImagePane
     */
    public ImagePane() {
        initComponents();
        
        this.setSize(new Dimension(cols * ImageCell.WIDTH, rows * ImageCell.HEIGHT));
        System.out.println(getSize().width + " " + getSize().height);
        library[0] = "animals/";
        library[1] = "birthday cake/";
        library[2] = "Cars/";
        library[3] = "fruits/";
        library[4] = "Hello kitty/";
        library[5] = "houses/";
        library[6] = "flowers/";
        storedImage = new ArrayList<>();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                location = e.getPoint();
                setImage();
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if(specify==-1)
                    return;
                location = e.getPoint();
                repaint();
            }
        });
    }
    public void setProgress(JProgressBar progressBar){
        this.progressBar = progressBar;
    }
    public void next() {
        if (specify < 6) {
            specify++;
            refresh();
            repaint();
        }
    }

    public void previous() {
        if (specify > 0) {
            specify--;
            refresh();
            repaint();
        }
    }

    public BufferedImage loadImage(int k){
        BufferedImage image = null;
        
         return image;
    }
    public void refresh() {
       
        //Neu anh da duoc load thi 
        g2 = (Graphics2D) buff_img.getGraphics();
        g2.setColor(new Color(204, 204, 255));
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.dispose();
        
        if(isloadeds[specify]==false){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) { 
                BufferedImage image = null;
                int k = i * cols + j;
               try {
                    image = ImageIO.read(new File(org_path + library[specify] + k + ".png"));
                } catch (IOException ex) {
                    Logger.getLogger(ImagePane.class.getName()).log(Level.SEVERE, null, ex);
                }
                images[i][j] = new ImageIcon(image.getScaledInstance(ImageCell.WIDTH, ImageCell.HEIGHT, Image.SCALE_SMOOTH));

                imageCells[i][j] = new ImageCell(images[i][j]);
                storedImage.add(imageCells[i][j]);
                //Ve image len panel
                imageCells[i][j].paintIcon(null, g2d, j * ImageCell.WIDTH, i * ImageCell.HEIGHT);
            }
        }
        //sau khi loac anh song thi chung to anh da duoc load, khong can phai load lai
        isloadeds[specify]=true;
        }
        else{
            ImageCell imageCell = null;
            for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) { 
                int k = i * cols + j+rows*cols*specify;
                //Ve image len panel
                imageCell = storedImage.get(k);
                imageCell.paintIcon(null, g2d, j * ImageCell.WIDTH, i * ImageCell.HEIGHT);
                BufferedImage image = (BufferedImage)imageCell.getImage();
                images[i][j] = new ImageIcon(image.getScaledInstance(ImageCell.WIDTH, ImageCell.HEIGHT, Image.SCALE_SMOOTH));
            }
        }
        }
        
    }

    public void setImage(){
        int k = currrow*cols+currcol;
        try {
                   selectedImage = ImageIO.read(new File(org_path + library[specify]+ k + ".png"));
                } catch (IOException ex) {
                    Logger.getLogger(ImagePane.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    public BufferedImage getSelectedImage() {
        return selectedImage;
    }

    public void drawToBuffer(int mouseX, int mouseY, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2.setColor(new Color(204, 204, 255));
        g2.fillRect(0, 0, cols * ImageCell.WIDTH, rows * ImageCell.HEIGHT);
        if (mouseX % ImageCell.HEIGHT == 0 || mouseY % ImageCell.WIDTH == 0) {
            return;
        }
        currrow = mouseY / ImageCell.HEIGHT;
        currcol = mouseX / ImageCell.WIDTH;
        if (currcol < 0 || currcol > cols - 1 || currrow < 0 || currrow > rows - 1) {
            return;
        }
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2.drawImage(images[currrow][currcol].getImage(), currcol * ImageCell.WIDTH - 7, currrow * ImageCell.HEIGHT - 7, ImageCell.WIDTH + 20, ImageCell.HEIGHT + 20, null);
        g2.setPaint(Color.BLUE);
        //   g2.fillRect(currcol * ImageCell.WIDTH, currrow * ImageCell.HEIGHT, ImageCell.WIDTH, ImageCell.HEIGHT);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (buff_img == null) {
            buff_img = (BufferedImage) this.createImage(cols * ImageCell.WIDTH, rows * ImageCell.HEIGHT);
            g2d = (Graphics2D) buff_img.getGraphics();
            BufferedImage image = null;
            try {
                    image = ImageIO.read(new File(org_path+"library.jpg"));
                } catch (IOException ex) {
                    Logger.getLogger(ImagePane.class.getName()).log(Level.SEVERE, null, ex);
                }
           
            image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            g2d.drawImage(image,0,0, this);
          //  refresh();
        }
        g.drawImage(buff_img, 0, 0, null);
        if (location != null) {
            drawToBuffer(location.x, location.y, g);
        }
    }

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
            .addGap(0, 505, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
