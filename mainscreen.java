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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Windows
 */
public class mainscreen extends JPanel implements ActionListener,KeyListener{
    private final URL URLtitle2,URLtitle1; 
    private final Image title2,title1;
    private boolean check;
    public mainscreen(){
        check=true;
        ThecastleG.status=0;
        URLtitle1 = this.getClass().getResource("mainscreen1.png");
        title1 = new ImageIcon(URLtitle1).getImage();
        URLtitle2 = this.getClass().getResource("mainscreen2.png");
        title2 = new ImageIcon(URLtitle2).getImage();
        ThecastleG.timer=new Timer(1000,this);
        ThecastleG.timer.start();
        addKeyListener(this);
        this.setFocusable(true);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(check){
            g.drawImage(title1, 0, 0,ThecastleG.width,ThecastleG.height,this);
        }
        else{
            g.drawImage(title2, 0, 0,ThecastleG.width,ThecastleG.height,this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if(!ThecastleG.isminimize){
                check=!check;
                repaint();
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
        int key=e.getKeyCode();
        if(key==KeyEvent.VK_ENTER&&!ThecastleG.isminimize){
            this.requestFocusInWindow(false);
            this.removeKeyListener(this);
            ThecastleG.timer.removeActionListener(this);
            ThecastleG.frame.remove(this);
            ThecastleG.frame.setFocusable(false);
            selectstagescreen sc=new selectstagescreen();
            ThecastleG.frame.add(sc);
            ThecastleG.frame.setVisible(true);
            sc.requestFocus();
        }
    }
}
