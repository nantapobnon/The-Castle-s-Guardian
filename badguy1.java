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
public class badguy1 {

    protected URL URLguy;
    protected Image[] badguyimage, badguyLimage;
    public int x, y, speed,currentfloor;
    protected int count;
    protected int width, height;
    protected boolean faceright;
    
    badguy1() {
    }

    badguy1(boolean faceright,int y) {
        badguyimage = new Image[7];
        badguyLimage = new Image[7];
        for (int i = 0; i < badguyimage.length; i++) {
            String imagename = "badguy1.";
            URLguy = this.getClass().getResource(imagename + (i + 1) + ".png");
            badguyimage[i] = new ImageIcon(URLguy).getImage();
            imagename = "badguy1L.";
            URLguy = this.getClass().getResource(imagename + (i + 1) + ".png");
            badguyLimage[i] = new ImageIcon(URLguy).getImage();
        }
        width = 80;
        height = 80;
        this.faceright = faceright;
        speed = 3;
        if(this.faceright) x=0;
        else x=ThecastleG.width-width;
        this.y=y;
        currentfloor=1;
        count=0;
    }

    public int getX() {
        return x;
    }

    public int getCurrentfloor() {
        return currentfloor;
    }

    public int getY() {
        return y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    
    public void move() {
        count++;
        if (faceright) {
            x += speed;
            if (x + width > ThecastleG.width) {
                x = ThecastleG.width - width;
                faceright = false;
                count = 0;
            } 
        } else {
            x -= speed;
            if (x < 0) {
                x = 0;
                faceright = true;
                count = 0;
            } 
        }
        if(Math.random() < 0.0025&&x>100&&x<ThecastleG.width-100&&currentfloor!=4){
            faceright = !faceright;
            count = 0;
        }
        if (count >= badguyimage.length) {
            count = 0;
        }
    }
    
    public void moveup(int y){
        if(this.y>y){
            this.y-=10;
        }
        if(this.y<=y){
            this.y=y;
            this.currentfloor++;
        }
    }
    
    public void draw(Graphics g) {
        if (faceright) {
            g.drawImage(badguyimage[count], x, y, width, height, null);
        } else {
            g.drawImage(badguyLimage[count], x, y, width, height, null);
        }
    }

    public Rectangle2D getbound() {
        return (new Rectangle2D.Double(x, y, width, height));
    }
}
