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
public class player {

    private URL URLk;
    private final Image[] knightR, knightL, rockimage;
    private int x;
    public int count;
    private int speed;
    private int y = 233, width = 70, height = 85;
    private boolean carry, faceright;
    private Rock rock;

    public player() {
        x = ThecastleG.width / 2;
        speed = 8;
        count = 0;
        carry = false;
        faceright = true;
        knightR = new Image[4];
        knightL = new Image[4];
        for (int i = 0; i < knightR.length; i++) {
            URLk = this.getClass().getResource("knight" + i + ".png");
            knightR[i] = new ImageIcon(URLk).getImage();
            URLk = this.getClass().getResource("knightL" + i + ".png");
            knightL[i] = new ImageIcon(URLk).getImage();
        }

        rockimage = null;
    }

    public void move(boolean direction) {
        if (carry) {
            speed = 6;
            rock.moverock(x);
        }
        if (direction) {
            faceright = true;
            x += speed;
        } else {
            faceright = false;
            x -= speed;
        }

        if (x + width > ThecastleG.width) {
            x = ThecastleG.width - width;
        } else if (x < 0) {
            x = 0;
        }
//        if (carry) {
//            rock.moverock(x);
//        }
        count++;
    }

    public int getX() {
        return x;
    }

    public void setCarry(boolean carry) {
        this.carry = carry;
        if (carry) {
            rock = new Rock(x);
            rock.setYrock(this.y + 15);
        } else {
            rock = null;
            speed = 8;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isCarry() {
        return carry;
    }

    public Rock getRock() {
        return rock;
    }

    public void draw(Graphics g) {
        if (count >= knightR.length) {
            count = 0;
        }
        if (faceright) {
            g.drawImage(knightR[count], x, y, width, height, null);
        } else {
            g.drawImage(knightL[count], x, y, width, height, null);
        }
        if (carry) {
            rock.drawrock(g);
        }
    }

    public Rectangle2D getbound() {
        return (new Rectangle2D.Double(x, y, width, height));
    }

    public void setY(int y) {
        this.y = y;
    }
}
