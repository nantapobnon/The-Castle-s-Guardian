/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thecastleg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Windows
 */
public class selectstagescreen extends JPanel implements ActionListener, KeyListener {

    private int selectstage;
    private URL url;
    private final Image[][] bgimage;
    private boolean check;

    public selectstagescreen() {
        selectstage = 1;
        check = false;
        ThecastleG.status = 0;
        bgimage = new Image[3][2];
        for (int i = 0; i < bgimage.length; i++) {
            String s = "selectstagescreen";
            url = this.getClass().getResource(s + (i + 1) + ".png");
            bgimage[i][0] = new ImageIcon(url).getImage();
            url = this.getClass().getResource(s + (i + 1) + ".1.png");
            bgimage[i][1] = new ImageIcon(url).getImage();
        }
        ThecastleG.timer = new Timer(1000, this);
        ThecastleG.timer.start();
        addKeyListener(this);
        this.setFocusable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        check = !check;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (selectstage > bgimage.length) {
            selectstage = 1;
        }
        if (selectstage < 1) {
            selectstage = 3;
        }
        if (check) {
            g.drawImage(bgimage[selectstage - 1][0], 0, 0, ThecastleG.width, ThecastleG.height - 30, this);
        } else {
            g.drawImage(bgimage[selectstage - 1][1], 0, 0, ThecastleG.width, ThecastleG.height - 30, this);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!ThecastleG.isminimize) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                switch (selectstage) {
                    case 1:
                        this.requestFocusInWindow(false);
                        this.removeKeyListener(this);
                        ThecastleG.timer.removeActionListener(this);
                        ThecastleG.frame.remove(this);
                        ThecastleG.frame.setFocusable(false);
                        stage1 st1 = new stage1();
                        ThecastleG.frame.add(st1);
                        ThecastleG.frame.setVisible(true);
                        st1.requestFocus();
                        break;
                    case 2:
                        this.requestFocusInWindow(false);
                        this.removeKeyListener(this);
                        ThecastleG.timer.removeActionListener(this);
                        ThecastleG.frame.remove(this);
                        ThecastleG.frame.setFocusable(false);
                        stage2 st2 = new stage2();
                        ThecastleG.frame.add(st2);
                        ThecastleG.frame.setVisible(true);
                        st2.requestFocus();
                        break;
                    case 3:
                        this.requestFocusInWindow(false);
                        this.removeKeyListener(this);
                        ThecastleG.timer.removeActionListener(this);
                        ThecastleG.frame.remove(this);
                        ThecastleG.frame.setFocusable(false);
                        stage3 st3 = new stage3();
                        ThecastleG.frame.add(st3);
                        ThecastleG.frame.setVisible(true);
                        st3.requestFocus();
                        break;
                    default:
                        break;
                }
            }
            if (key == KeyEvent.VK_RIGHT) {
                selectstage++;
                repaint();
            }
            if (key == KeyEvent.VK_LEFT) {
                selectstage--;
                repaint();
            }
            if (key == KeyEvent.VK_ESCAPE) {
                this.requestFocusInWindow(false);
                this.removeKeyListener(this);
                ThecastleG.timer.removeActionListener(this);
                ThecastleG.frame.remove(this);
                ThecastleG.frame.setFocusable(false);
                mainscreen mn = new mainscreen();
                ThecastleG.frame.add(mn);
                ThecastleG.frame.setVisible(true);
                mn.requestFocus();
            }
        }
    }

}
