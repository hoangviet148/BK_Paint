/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Khanh
 */
public class ReplayDialog extends javax.swing.JDialog {

    /**
     * Creates new form ReplayDialog
     */
    private PaintState paintState;
    private ImageIcon playIcon;
    private ImageIcon pauseIcon;
    private ImageIcon stopIcon;
    private ReplayPanel replayPanel;
    private JPanel containerPanel;
    private BufferedImage buff_img;
    private boolean saveToFile = false;

    public ReplayDialog(java.awt.Frame parent, boolean modal, PaintState paintState) {
        super(parent, modal);
        playIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/play.png")));
        pauseIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/pause.png")));
        stopIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/stop.png")));
       
        this.paintState = paintState;
        initComponents();
         bPlay.setIcon(pauseIcon);
        bStop.setIcon(stopIcon);
        containerPanel = new JPanel();
        containerPanel.setLayout(null);
        replayPanel = new ReplayPanel();
        replayPanel.setPaintState(paintState);
        buff_img = replayPanel.getBuffer();
        containerPanel.setPreferredSize(new Dimension(replayPanel.getWidth() + 100, replayPanel.getHeight() + 50));
        containerPanel.add(replayPanel);
        scrollPane.setViewportView(containerPanel);
        containerPanel.add(replayPanel);
        containerPanel.validate();
        replayPanel.setButton(bPlay);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (saveToFile == false) {
                    Object[] option = {"Save", "Don't Save", "Cancel"};
                    int specify = JOptionPane.showOptionDialog(null, "Do you want to save file ?", "BKPaint",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, rootPane);
                    if (specify != JOptionPane.CANCEL_OPTION && specify != JOptionPane.CLOSED_OPTION) {
                        if (specify == JOptionPane.YES_OPTION) {
                            saveFile();
                        }
                        //Neu chua luu duoc thi khong thoat ra
                        if (saveToFile == false) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
                replayPanel.flush();
                replayPanel.dispose();
            }

        });
        this.setTitle("Untitled-Replay Dialog");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void saveFile() {
        JFileChooser fileSave = new JFileChooser("Save a replay file");
        int select = 0;
        File init = new File("Untitled.rep");
        fileSave.setSelectedFile(init);
        select = fileSave.showSaveDialog(null);     //Hien thi filechoser cung voi ten mac dinh
        if (select == JFileChooser.APPROVE_OPTION) {
            File file = fileSave.getCurrentDirectory();
            String fileName = file.getPath() + "\\" + fileSave.getSelectedFile().getName();
            file = new File(fileName);
            if (file.exists()) {
                int r = JOptionPane.showConfirmDialog(this, "File" + fileSave.getSelectedFile() + " already exists\nDo you want to replace it", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (r == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
                oos.writeObject(paintState);
                saveToFile = true;
                oos.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Save file error!", "Error", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ReplayDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void openFile() {
        JFileChooser fileOpen = new JFileChooser("Open replay file");
        int select = 0;
        replayPanel.flush();
        select = fileOpen.showOpenDialog(null);
        if (select == JFileChooser.APPROVE_OPTION) {
            if (saveToFile == false) {
                Object[] option = {"Save", "Don't Save", "Cancel"};
                int specify = JOptionPane.showOptionDialog(this, "Do you want to save file ?", "BKPaint",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, rootPane);
                if (specify == JOptionPane.CANCEL_OPTION) {
                    return;
                }
                if (specify == JOptionPane.YES_OPTION) {
                    saveFile();
                }
            }
            try {

                ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileOpen.getSelectedFile()));
                String s = fileOpen.getSelectedFile().getName();
                this.setTitle(s + "- Replay Dialog");
                System.gc();
                paintState = (PaintState) in.readObject();
                replayPanel.setPaintState(paintState);
                replayPanel.refresh();
                System.gc();
                in.close();
            } catch (IOException ex) {
                System.out.println("loi vao ra");
                Logger.getLogger(ReplayDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                System.out.println("khong tim thay");
                Logger.getLogger(ReplayDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        scrollPane = new javax.swing.JScrollPane();
        speed = new javax.swing.JSlider();
        bPlay = new javax.swing.JToggleButton();
        bStop = new javax.swing.JToggleButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnFile = new javax.swing.JMenu();
        miSave = new javax.swing.JMenuItem();
        miOpen = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miExit = new javax.swing.JMenuItem();
        mnHelp = new javax.swing.JMenu();
        miHelp = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        speed.setValueIsAdjusting(true);
        speed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedStateChanged(evt);
            }
        });

        buttonGroup1.add(bPlay);
        bPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPlayActionPerformed(evt);
            }
        });

        buttonGroup1.add(bStop);
        bStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStopActionPerformed(evt);
            }
        });

        mnFile.setText("File");

        miSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        miSave.setText("Save");
        miSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSaveActionPerformed(evt);
            }
        });
        mnFile.add(miSave);

        miOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        miOpen.setText("Open");
        miOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miOpenActionPerformed(evt);
            }
        });
        mnFile.add(miOpen);
        mnFile.add(jSeparator1);

        miExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        miExit.setText("Exit");
        miExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExitActionPerformed(evt);
            }
        });
        mnFile.add(miExit);

        jMenuBar1.add(mnFile);

        mnHelp.setText("Help");

        miHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.CTRL_MASK));
        miHelp.setText("Help");
        mnHelp.add(miHelp);

        jMenuBar1.add(mnHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bStop, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(231, 231, 231)
                        .addComponent(speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(speed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bStop, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPlayActionPerformed
        containerPanel.setSize(new Dimension(replayPanel.getSize()));
        containerPanel.setMinimumSize(new Dimension(replayPanel.getSize()));
        if (replayPanel.isPlaying()) {
            bPlay.setIcon(pauseIcon);
            replayPanel.pauseReplay();

        } else {
            bPlay.setIcon(playIcon);
            replayPanel.startReplay();
        }
    }//GEN-LAST:event_bPlayActionPerformed

    private void speedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedStateChanged
        changeSpeed(speed.getValue());
    }//GEN-LAST:event_speedStateChanged

    private void bStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStopActionPerformed
        bPlay.setIcon(pauseIcon);
        replayPanel.stopReplay();
    }//GEN-LAST:event_bStopActionPerformed

    private void miExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExitActionPerformed
        if (saveToFile == false) {
            Object[] option = {"Save", "Don't Save", "Cancel"};
            int specify = JOptionPane.showOptionDialog(null, "Do you want to save file ?", "BKPaint",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, rootPane);
            if (specify != JOptionPane.CANCEL_OPTION && specify != JOptionPane.CLOSED_OPTION) {
                if (specify == JOptionPane.YES_OPTION) {
                    saveFile();
                }
                //Neu chua luu duoc thi khong thoat ra
                if (saveToFile == false) {
                    return;
                }
            } else {
                return;
            }
        }
        this.setVisible(false);
        replayPanel.flush();
        replayPanel.dispose();
    }//GEN-LAST:event_miExitActionPerformed

    private void miSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSaveActionPerformed
        if (saveToFile == false) {
            saveFile();
        }
    }//GEN-LAST:event_miSaveActionPerformed

    private void miOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miOpenActionPerformed

        openFile();
    }//GEN-LAST:event_miOpenActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton bPlay;
    private javax.swing.JToggleButton bStop;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miHelp;
    private javax.swing.JMenuItem miOpen;
    private javax.swing.JMenuItem miSave;
    private javax.swing.JMenu mnFile;
    private javax.swing.JMenu mnHelp;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSlider speed;
    // End of variables declaration//GEN-END:variables
    public void changeSpeed(int value) {
        replayPanel.setDelay(value);
    }
}
