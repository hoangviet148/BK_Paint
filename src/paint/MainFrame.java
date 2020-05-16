/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import library.Library;

/**
 *
 * @author Khanh
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public static boolean isSaved;
    private BufferedImage buff_img = null;
    private String fileName = "";
    private File fileImage = null;
    private PadPaint padPaint = null;
    private final JFileChooser fc = new JFileChooser();
    private JPanel backgroundPanel = new JPanel();

    public MainFrame() {
        initComponents();
        bCopy.setIcon(new ImageIcon(getImageIcon("/icon/copy.png")));
        bCut.setIcon(new ImageIcon(getImageIcon("/icon/cut.png")));
        bUndo.setIcon(new ImageIcon(getImageIcon("/icon/undo.png")));
        bRedo.setIcon(new ImageIcon(getImageIcon("/icon/redo.png")));
        bCut.setIcon(new ImageIcon(getImageIcon("/icon/cut.png")));
        bZoomadd.setIcon(new ImageIcon(getImageIcon("/icon/zoomplus.png")));
        bZoomMinus.setIcon(new ImageIcon(getImageIcon("/icon/zoomMinus.png")));
        bZoomorg.setIcon(new ImageIcon(getImageIcon("/icon/zoomorg.png")));
        bReplay.setIcon(new ImageIcon(getImageIcon("/icon/replay.png")));
        bLibrary.setIcon(new ImageIcon(getImageIcon("/icon/library.png")));
        bZoomOut.setIcon(new ImageIcon(getImageIcon("/icon/zoomout.png")));
        bZoomIn.setIcon(new ImageIcon(getImageIcon("/icon/zoomin.png")));
        newFile.setIcon(new ImageIcon(getImageIcon("/icon/new.png")));
        openFile.setIcon(new ImageIcon(getImageIcon("/icon/open.png")));
        saveFile.setIcon(new ImageIcon(getImageIcon("/icon/save.png")));
        saveAsFile.setIcon(new ImageIcon(getImageIcon("/icon/saveAs.png")));
        exitFile.setIcon(new ImageIcon(getImageIcon("/icon/exit.png")));
        

        //update by hung
        padPaint = new PadPaint(909, 439);
        backgroundPanel.setLayout(null);
        backgroundPanel.setBackground(new Color(204, 204, 255));
        buff_img = padPaint.getBuffer();
        backgroundPanel.setPreferredSize(new Dimension(buff_img.getWidth() + 120, buff_img.getHeight() + 50));
        backgroundPanel.add(padPaint);
        scrollPane.setViewportView(backgroundPanel);
        padPaint.setColorChooser(colorDialog1);
        padPaint.setPaintTool(paintTool);
        padPaint.setStrokeState(strokeState);
        padPaint.setTextPanel(textPanel1);

        padPaint.flush();

        padPaint.setLocationStatus(lbLocation);
        padPaint.setSizeStatus(lbSize);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setTitle("BKPaint");
        this.setIconImage(getImageIcon("/icon/icon_image.png"));
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                if (padPaint.isSaving() == false) {
                    Object[] option = {"Save", "Don't Save", "Cancel"};
                    int specify = JOptionPane.showOptionDialog(null, "Do you want to save file ?", "BKPaint", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, rootPane);
                    if (specify != JOptionPane.CANCEL_OPTION && specify != JOptionPane.CLOSED_OPTION) {
                        if (specify == JOptionPane.YES_OPTION) {
                            saveImageToFile();
                            //phai luu duoc thi moi thoat
                            if (padPaint.isSaving()) {
                                System.exit(0);
                            }
                        } else {
                            System.exit(0);
                        }

                    } else {
                        return;
                    }
                } else {
                    System.exit(0);
                }

            }
        });
        paintTool.addPropertyChangeListener("tool change", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                padPaint.toolChange();
            }

        });
    }

    public Image getImageIcon(String path) {
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource(path));
        return image;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        cbTransform = new javax.swing.JComboBox();
        bCopy = new OvalButton("");
        bCut = new OvalButton("");
        jButton2 = new javax.swing.JButton();
        bUndo = new OvalButton("");
        bRedo = new OvalButton("");
        paintTool = new paint.PaintTool();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        colorDialog1 = new property.ColorDialog();
        strokeState = new property.StrokeState();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        bReplay = new JButton();
        bLibrary = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        bZoomadd = new javax.swing.JButton();
        bZoomMinus = new javax.swing.JButton();
        bZoomorg = new javax.swing.JButton();
        textPanel1 = new property.TextPanel();
        sZoom = new javax.swing.JSlider();
        bZoomOut = new OvalButton("");
        bZoomIn = new OvalButton("");
        lbLocation = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        lbZoomInfo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbSize = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        miNew = new javax.swing.JMenu();
        newFile = new javax.swing.JMenuItem();
        openFile = new javax.swing.JMenuItem();
        saveFile = new javax.swing.JMenuItem();
        saveAsFile = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitFile = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(233, 247, 247));

        jPanel4.setBackground(new java.awt.Color(233, 247, 247));

        jPanel6.setBackground(new java.awt.Color(233, 247, 247));

        jButton1.setText("Paste");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cbTransform.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rotate", "Rotate right 90", "Rotate left 90", "Rotate 180", "Flip vertical", "Flip horizontal" }));
        cbTransform.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTransformItemStateChanged(evt);
            }
        });
        cbTransform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTransformActionPerformed(evt);
            }
        });

        bCopy.setPreferredSize(new Dimension(39,39));
        bCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCopyActionPerformed(evt);
            }
        });

        bCut.setPreferredSize(new Dimension(39,39));
        bCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCutActionPerformed(evt);
            }
        });

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        bUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUndoActionPerformed(evt);
            }
        });

        bRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRedoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(bCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(bCut, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbTransform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(bUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bRedo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bCut, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cbTransform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jButton1)
                        .addGap(6, 6, 6)
                        .addComponent(jButton2))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bRedo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        paintTool.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paintToolMouseClicked(evt);
            }
        });
        paintTool.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                paintToolPropertyChange(evt);
            }
        });

        colorDialog1.setBackground(new java.awt.Color(233, 247, 247));

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paintTool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(strokeState, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(350, 350, 350)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(colorDialog1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paintTool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(strokeState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(colorDialog1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane2.addTab("Home", jPanel4);

        jPanel1.setBackground(new java.awt.Color(233, 247, 247));

        bReplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bReplayActionPerformed(evt);
            }
        });

        bLibrary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLibraryActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(233, 247, 247));

        bZoomadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bZoomaddActionPerformed(evt);
            }
        });

        bZoomMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bZoomMinusActionPerformed(evt);
            }
        });

        bZoomorg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bZoomorgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(bZoomadd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(bZoomMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(bZoomorg, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bZoomadd, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bZoomMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bZoomorg, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        textPanel1.setBackground(new java.awt.Color(233, 247, 247));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(textPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(312, 312, 312)
                .addComponent(bReplay, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(bLibrary, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(textPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(bReplay, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(bLibrary, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jTabbedPane2.addTab("Option", jPanel1);

        sZoom.setMajorTickSpacing(10);
        sZoom.setMinimum(20);
        sZoom.setMinorTickSpacing(10);
        sZoom.setValue(60);
        sZoom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sZoomStateChanged(evt);
            }
        });

        bZoomOut.setPreferredSize(new Dimension(20,20));
        bZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bZoomOutActionPerformed(evt);
            }
        });

        bZoomIn.setPreferredSize(new Dimension(20,20));
        bZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bZoomInActionPerformed(evt);
            }
        });

        lbZoomInfo.setText("100%");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/location.png"))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/size.png"))); // NOI18N

        jMenuBar1.setMargin(new java.awt.Insets(0, 0, 0, 500));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(20, 21));

        miNew.setText("File");

        newFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newFile.setText("New");
        newFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileActionPerformed(evt);
            }
        });
        miNew.add(newFile);

        openFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openFile.setText("Open");
        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileActionPerformed(evt);
            }
        });
        miNew.add(openFile);

        saveFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveFile.setText("Save");
        saveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileActionPerformed(evt);
            }
        });
        miNew.add(saveFile);

        saveAsFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveAsFile.setText("Save As");
        saveAsFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsFileActionPerformed(evt);
            }
        });
        miNew.add(saveAsFile);
        miNew.add(jSeparator1);

        exitFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exitFile.setText("Exit");
        exitFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitFileActionPerformed(evt);
            }
        });
        miNew.add(exitFile);

        jMenuBar1.add(miNew);

        jMenu4.setText("Help");

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setText("Help");
        jMenu4.add(jMenuItem10);

        jMenuItem11.setText("About");
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbZoomInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bZoomOut, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sZoom, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(bZoomIn, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bZoomIn, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bZoomOut, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbZoomInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sZoom, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileActionPerformed
        openAnImage();
    }//GEN-LAST:event_openFileActionPerformed

    public void openAnImage() {
        JFileChooser filechooser = new JFileChooser("Open A File");
        int result = 0;
        FileNameExtensionFilter bitmap = new FileNameExtensionFilter("Bitmap Files (*.bmp;*.dib)", "bmp", "dib");
        filechooser.setFileFilter(bitmap);
        FileNameExtensionFilter jpeg = new FileNameExtensionFilter("JPEG (*.jpg;*.jpeg;*.jpe;*jfif)", "jpg", "jpeg", "jpe", "jfif");
        filechooser.setFileFilter(jpeg);
        FileNameExtensionFilter gif = new FileNameExtensionFilter("GIF (*.gif)", "gif");
        filechooser.setFileFilter(gif);
        FileNameExtensionFilter tiff = new FileNameExtensionFilter("TIFF (*.tif;*.tiff)", "tif", "tiff");
        filechooser.setFileFilter(tiff);
        FileNameExtensionFilter png = new FileNameExtensionFilter("PNG (*.png)", "png");
        filechooser.setFileFilter(png);
        FileNameExtensionFilter ico = new FileNameExtensionFilter("ICO (*.ico)", "ico");
        filechooser.setFileFilter(ico);
        FileNameExtensionFilter allFile = new FileNameExtensionFilter("All Picture Files", "bmp", "dib", "jpg", "jpeg", "jpe", "jfif", "gif", "tif", "tiff", "png", "ico");
        filechooser.setFileFilter(allFile);

        result = filechooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = filechooser.getSelectedFile();
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(file.getPath()));
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (img != null) {
                if (padPaint.isSaving() == false) {
                    Object[] option = {"Save", "Don't Save", "Cancel"};
                    int specify = JOptionPane.showOptionDialog(this, "Do you want to save file ?", "BKPaint", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, rootPane);
                    if (specify != JOptionPane.CANCEL_OPTION && specify != JOptionPane.CLOSED_OPTION) {
                        if (specify == JOptionPane.YES_OPTION) {
                            saveImageToFile();
                        }
                    } else {
                        return;
                    }

                }
                padPaint.loadImage(img);
                //update by hung
                backgroundPanel.setPreferredSize(new Dimension(img.getWidth() + 20, img.getHeight() + 20));
            }
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        padPaint.paste();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void newFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileActionPerformed
        if (padPaint.isSaving() == false) {
            Object[] option = {"Save", "Don't Save", "Cancel"};
            int specify = JOptionPane.showOptionDialog(this, "Do you want to save file ?", "BKPaint", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, rootPane);
            if (specify != JOptionPane.CANCEL_OPTION && specify != JOptionPane.CLOSED_OPTION) {
                if (specify == JOptionPane.YES_OPTION) {
                    if (saveImageToFile()) {
                        padPaint.flush();
                    } else {
                        return;
                    }
                }

                padPaint.flush();
            } else {
                return;
            }
        } else if (padPaint.isSaving() == true) {
            padPaint.flush();
        }

    }//GEN-LAST:event_newFileActionPerformed

    private void saveFileActionPerformed(java.awt.event.ActionEvent evt) {
        if (padPaint.isSaving() == false) {
            saveImageToFile();
        }
    }

    public boolean saveImageToFile() {
        JFileChooser saveFile = new JFileChooser("Save File");
        int result = 0;
        FileFilter filler;
        BufferedImage img = null;

        FileNameExtensionFilter jpeg = new FileNameExtensionFilter("JPEG (*.jpg;*.jpeg;*.jpe;*jfif)", "jpg", "jpeg", "jpe", "jfif");
        saveFile.setFileFilter(jpeg);
        FileNameExtensionFilter gif = new FileNameExtensionFilter("GIF (*.gif)", "gif");
        saveFile.setFileFilter(gif);

        FileNameExtensionFilter png = new FileNameExtensionFilter("PNG (*.png)", "png");
        saveFile.setFileFilter(png);

        File demo = new File("Untitled.png");
        saveFile.setSelectedFile(demo);
        result = saveFile.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {                              //nếu ấn Ok trong save file
            filler = saveFile.getFileFilter();                                  //lấy đường dẫn hiện tại của bộ lọc
            fileImage = saveFile.getCurrentDirectory();                         //lấy đường dẫn hiện tại
            fileName = fileImage.getPath() + "\\" + saveFile.getSelectedFile().getName();  //đường dẫn hiện tại + tên file
            fileImage = new File(fileName);                                              //tạo file để lưu

            String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            if (fileImage.exists() == true) {
                int r = JOptionPane.showConfirmDialog(this, saveFile.getSelectedFile().getName() + " already exists." + "\nDo you want to replace it?", "hello", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (r == JOptionPane.YES_OPTION) {

                    padPaint.saveImage(fileImage, extension);
                    return true;
                }
            } else {
                padPaint.saveImage(fileImage, extension);
                return true;
            }
        }
        return false;
    }

    private class OvalButton extends JButton {

        private Color startColor = new Color(204,204,255);
        private Color endColor = new Color(204,204,255);
        private Color rollOverColor = new Color(204,204,255);
        private Color pressedColor = new Color(204,204,255);
        ;
	private GradientPaint GP;

        /**
         * Constructor takes String argument
         *
         * @param text
         */
        public OvalButton(String text) {
            super();
            setText(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFont(new Font("Thoma", Font.BOLD, 12));
            setForeground(Color.WHITE);
            setFocusable(false);

        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            int h = getHeight();
            int w = getWidth();
            ButtonModel model = getModel();

            if (!model.isEnabled()) {
                setForeground(new Color(204,204,255));
                GP = new GradientPaint(0, 0, new Color(204,204,255), 0, h, new Color(204,204,255), true);
            } else {
                setForeground(Color.WHITE);
                if (model.isRollover()) {
                    GP = new GradientPaint(0, 0, rollOverColor, 0, h, rollOverColor,
                            true);

                } else {
                    GP = new GradientPaint(0, 0, startColor, 0, h, endColor, true);
                }
            }
            g2d.setPaint(GP);
            GradientPaint p1;
            GradientPaint p2;

            if (model.isPressed()) {
                GP = new GradientPaint(0, 0, pressedColor, 0, h, pressedColor, true);
                g2d.setPaint(GP);
                p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1,
                        new Color(204,204,255));
                p2 = new GradientPaint(0, 1, new Color(204,204,255), 0, h - 1,
                        new Color(204,204,255));
            } else {
                p1 = new GradientPaint(0, 0, new Color(204,204,255), 0, h - 1,
                        new Color(0, 0, 0));
                p2 = new GradientPaint(0, 1, new Color(204,204,255), 0,
                        h - 1, new Color(204,204,255));
                GP = new GradientPaint(0, 0, startColor, 0, h, endColor, true);
            }

            g2d.fillOval(0, 0, h, h);
            g2d.setPaint(p1);
            g2d.drawOval(0, 0, h - 1, h - 1);
            g2d.setPaint(p2);
            g2d.drawOval(0, 0, h - 1, h - 1);
            g2d.dispose();

            super.paintComponent(g);
        }
    }

    private void bZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bZoomInActionPerformed
        sZoom.setValue(sZoom.getValue() + 10);
    }//GEN-LAST:event_bZoomInActionPerformed

    private void bZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bZoomOutActionPerformed
        sZoom.setValue(sZoom.getValue() - 10);
    }//GEN-LAST:event_bZoomOutActionPerformed

    private void exitFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitFileActionPerformed
        if (padPaint.isSaving() == false) {
            Object[] option = {"Save", "Don't Save", "Cancel"};
            int specify = JOptionPane.showOptionDialog(null, "Do you want to save file ?", "BKPaint", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, rootPane);
            if (specify != JOptionPane.CANCEL_OPTION && specify != JOptionPane.CLOSED_OPTION) {
                if (specify == JOptionPane.YES_OPTION) {
                    saveImageToFile();
                    //phai luu duoc thi moi thoat
                    if (padPaint.isSaving()) {
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }

            } else {
                return;
            }
        } else {
            System.exit(0);
        }
    }//GEN-LAST:event_exitFileActionPerformed

    private void saveAsFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsFileActionPerformed
        saveImageToFile();
    }//GEN-LAST:event_saveAsFileActionPerformed
//update by hung
    private void cbTransformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTransformActionPerformed
        if (cbTransform.getSelectedItem() == cbTransform.getItemAt(1)) {
            padPaint.rotate(90);
            
        } else if (cbTransform.getSelectedItem() == cbTransform.getItemAt(2)) {
            padPaint.rotate(-90);
            
        } else if (cbTransform.getSelectedItem() == cbTransform.getItemAt(3)) {
            padPaint.rotate(180);
            
        } else if (cbTransform.getSelectedItem() == cbTransform.getItemAt(4)) {
            padPaint.flipping(1);
            
        } else if (cbTransform.getSelectedItem() == cbTransform.getItemAt(5)) {
            padPaint.flipping(2);
            
        }
        buff_img = padPaint.getBuffer();
        backgroundPanel.setPreferredSize(new Dimension(padPaint.getWidth() + 30, padPaint.getHeight() + 30));
        cbTransform.setSelectedIndex(0);
    }//GEN-LAST:event_cbTransformActionPerformed

    private void cbTransformItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTransformItemStateChanged

    }//GEN-LAST:event_cbTransformItemStateChanged

    private void sZoomStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sZoomStateChanged
        padPaint.setZoom(sZoom.getValue());
        int zoom = (int) (padPaint.getZoom() * 100);
        String zoomInfo = zoom + "%";
        lbZoomInfo.setText(zoomInfo);
        backgroundPanel.setPreferredSize(new Dimension(padPaint.getWidth() + 30, padPaint.getHeight() + 30));

    }//GEN-LAST:event_sZoomStateChanged

    private void paintToolPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_paintToolPropertyChange
        System.out.println("tool change");
    }//GEN-LAST:event_paintToolPropertyChange

    private void paintToolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paintToolMouseClicked

    }//GEN-LAST:event_paintToolMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        padPaint.paste();
    }//GEN-LAST:event_jButton1MouseClicked

    private void bUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUndoActionPerformed
        padPaint.undo();        // TODO add your handling code here:
    }//GEN-LAST:event_bUndoActionPerformed

    private void bRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRedoActionPerformed
        padPaint.redo();    }//GEN-LAST:event_bRedoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        padPaint.delete();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void bCutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCutActionPerformed
        padPaint.cut();
    }//GEN-LAST:event_bCutActionPerformed

    private void bCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCopyActionPerformed
        padPaint.copy();
    }//GEN-LAST:event_bCopyActionPerformed

    private void bZoomorgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bZoomorgActionPerformed
        sZoom.setValue(60);
    }//GEN-LAST:event_bZoomorgActionPerformed

    private void bZoomMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bZoomMinusActionPerformed
        sZoom.setValue(sZoom.getValue() - 10);
    }//GEN-LAST:event_bZoomMinusActionPerformed

    private void bZoomaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bZoomaddActionPerformed
        sZoom.setValue(sZoom.getValue() + 10);
    }//GEN-LAST:event_bZoomaddActionPerformed

    private void bLibraryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLibraryActionPerformed
        Library library = new Library(this, true);
        buff_img = library.getBufferedImage();
        if (buff_img != null) {
            //anh chua duoc luu
            if (padPaint.isSaving() == false) {
                Object[] option = {"Save", "Don't Save", "Cancel"};
                int specify = JOptionPane.showOptionDialog(this, "Do you want to save file ?", "BKPaint", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, rootPane);
                if (specify != JOptionPane.CANCEL_OPTION && specify != JOptionPane.CLOSED_OPTION) {
                    if (specify == JOptionPane.YES_OPTION) {
                        saveImageToFile();
                    }
                    padPaint.loadImage(library.getBufferedImage());
                    backgroundPanel.setPreferredSize(new Dimension(buff_img.getWidth() + 120, buff_img.getHeight() + 50));
                }
            } else if (padPaint.isSaving() == true) {
                padPaint.loadImage(library.getBufferedImage());
                backgroundPanel.setPreferredSize(new Dimension(buff_img.getWidth() + 120, buff_img.getHeight() + 50));
            }

        }
    }//GEN-LAST:event_bLibraryActionPerformed

    private void bReplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bReplayActionPerformed
        padPaint.toolChange();
        System.gc();
        new ReplayDialog(this, true, padPaint.getListState());
    }//GEN-LAST:event_bReplayActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCopy;
    private javax.swing.JButton bCut;
    private javax.swing.JButton bLibrary;
    private javax.swing.JButton bRedo;
    private javax.swing.JButton bReplay;
    private javax.swing.JButton bUndo;
    private javax.swing.JButton bZoomIn;
    private javax.swing.JButton bZoomMinus;
    private javax.swing.JButton bZoomOut;
    private javax.swing.JButton bZoomadd;
    private javax.swing.JButton bZoomorg;
    private javax.swing.JComboBox cbTransform;
    private property.ColorDialog colorDialog1;
    private javax.swing.JMenuItem exitFile;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lbLocation;
    private javax.swing.JLabel lbSize;
    private javax.swing.JLabel lbZoomInfo;
    private javax.swing.JMenu miNew;
    private javax.swing.JMenuItem newFile;
    private javax.swing.JMenuItem openFile;
    private paint.PaintTool paintTool;
    private javax.swing.JSlider sZoom;
    private javax.swing.JMenuItem saveAsFile;
    private javax.swing.JMenuItem saveFile;
    private javax.swing.JScrollPane scrollPane;
    private property.StrokeState strokeState;
    private property.TextPanel textPanel1;
    // End of variables declaration//GEN-END:variables
}
