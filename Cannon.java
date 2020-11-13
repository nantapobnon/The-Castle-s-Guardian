/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thecastleg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Windows
 */
public class Cannon {
    private final URL URLcannonball; 
    private Image cannonball;
    private final int width=30,height=30;
    private int x,speed;
    private int y=255;
    Cannon(int x){
        URLcannonball = this.getClass().getResource("cannonball.png");
        cannonball = new ImageIcon(URLcannonball).getImage();
        this.x=x;
        speed=15;
    }
    public void drawcannon(Graphics g){
        g.drawImage(cannonball, x, y, width, height,null);
    }
    public void move(){
        x -= speed;
    }
    
    public int getX() {
        return x;
    }

    public Image getCannonball() {
        return cannonball;
    }
    
    public Rectangle2D getbound(){
        return (new Rectangle2D.Double(x,y,width,height));
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
    
}
