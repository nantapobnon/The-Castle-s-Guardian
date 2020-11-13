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
public class endgamescreen extends JPanel implements ActionListener, KeyListener {

    private URL url;
    private final Image[] numimage;
    private final Image[][] bgimage;
    private boolean check, iswin;
    private int score;

    public endgamescreen(boolean iswin, int score) {
        this.iswin = iswin;
        this.score = score;
        bgimage = new Image[2][2];
        check = false;
        for (int i = 0; i < bgimage.length; i++) {
            String s = "wingamebg";
            url = this.getClass().getResource(s + (i + 1) + ".png");
            bgimage[0][i] = new ImageIcon(url).getImage();
            s = "losegamebg";
            url = this.getClass().getResource(s + (i + 1) + ".png");
            bgimage[1][i] = new ImageIcon(url).getImage();
        }
        numimage = new Image[10];
        for (int i = 0; i < numimage.length; i++) {
            String s = "num";
            url = this.getClass().getResource(s + i + ".png");
            numimage[i] = new ImageIcon(url).getImage();
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
        if (check) {
            if (iswin) {
                g.drawImage(bgimage[0][0], 0, 0, ThecastleG.width - 15, ThecastleG.height - 30, this);
            } else {
                g.drawImage(bgimage[1][0], 0, 0, ThecastleG.width - 15, ThecastleG.height - 30, this);
            }
        } else {
            if (iswin) {
                g.drawImage(bgimage[0][1], 0, 0, ThecastleG.width - 15, ThecastleG.height - 30, this);
            } else {
                g.drawImage(bgimage[1][1], 0, 0, ThecastleG.width - 15, ThecastleG.height - 30, this);
            }
        }

        String Sscore = String.valueOf(score);
        for (int i = 0; i < Sscore.length(); i++) {
            int thiswidth = 50;
            g.drawImage(numimage[Integer.parseInt(String.valueOf(Sscore.charAt(i)))], 1010 + (i * thiswidth), 215, thiswidth, 80, this);
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
            if (key == KeyEvent.VK_R) {
                this.requestFocusInWindow(false);
                this.removeKeyListener(this);
                ThecastleG.timer.removeActionListener(this);
                ThecastleG.frame.remove(this);
                ThecastleG.frame.setFocusable(false);
                switch (ThecastleG.status) {
                    case 1:
                        stage1 st1 = new stage1();
                        ThecastleG.frame.add(st1);
                        ThecastleG.frame.setVisible(true);
                        st1.requestFocus();
                        break;
                    case 2:
                        stage2 st2 = new stage2();
                        ThecastleG.frame.add(st2);
                        ThecastleG.frame.setVisible(true);
                        st2.requestFocus();
                        break;
                    case 3:
                        stage3 st3 = new stage3();
                        ThecastleG.frame.add(st3);
                        ThecastleG.frame.setVisible(true);
                        st3.requestFocus();
                        break;
                }
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
            if (key == KeyEvent.VK_SPACE) {
                this.requestFocusInWindow(false);
                this.removeKeyListener(this);
                ThecastleG.timer.removeActionListener(this);
                ThecastleG.frame.remove(this);
                ThecastleG.frame.setFocusable(false);
                selectstagescreen sc = new selectstagescreen();
                ThecastleG.frame.add(sc);
                ThecastleG.frame.setVisible(true);
                sc.requestFocus();
            }
        }
    }

}
