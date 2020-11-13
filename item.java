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
public class item {

    private URL URLimage;
    private Image[][] goodimage, badimage;
    private int x, y, count, speed, type;
    private int width, height, floor;
    public boolean isgood, isexit;

    item() {
    }

    item(int y) {
        floor = 1;
        isexit = false;
        goodimage = new Image[3][12];
        badimage = new Image[3][12];
        for (int i = 1; i <= goodimage[0].length; i++) {
            int k = i;
            if (k > goodimage[0].length / 2) {
                k -= goodimage[0].length / 2;
            }
            URLimage = this.getClass().getResource("heartgoodwing" + (k) + ".png");
            goodimage[0][i - 1] = new ImageIcon(URLimage).getImage();
            URLimage = this.getClass().getResource("heartbadwing" + (k) + ".png");
            badimage[0][i - 1] = new ImageIcon(URLimage).getImage();

            URLimage = this.getClass().getResource("cannongoodwing" + (k) + ".png");
            goodimage[1][i - 1] = new ImageIcon(URLimage).getImage();
            URLimage = this.getClass().getResource("cannonbadwing" + (k) + ".png");
            badimage[1][i - 1] = new ImageIcon(URLimage).getImage();

            URLimage = this.getClass().getResource("clockgoodwing" + (k) + ".png");
            goodimage[2][i - 1] = new ImageIcon(URLimage).getImage();
            URLimage = this.getClass().getResource("clockbadwing" + (k) + ".png");
            badimage[2][i - 1] = new ImageIcon(URLimage).getImage();
        }

        width = 120;
        height = 70;
        speed = 5;
        count = 0;
        this.y = y;
        x = 0;
        isgood = ThecastleG.rand.nextBoolean();
        type = ThecastleG.rand.nextInt(goodimage.length);
    }

    public void move(int[] yfloor) {
        //x+=speed;
        if (floor == 1 && x == ThecastleG.width - width && !isexit) {
            y -= speed;
            if (y <= yfloor[floor + 1]) {
                y = yfloor[floor + 1];
                floor++;
            }
        } else if (floor == 1) {
            x += speed;
        }
        if (floor == 2 && x <= 0&&!isexit) {
            y += speed;
            if (y >= yfloor[1]) {
                y = yfloor[1];
                floor--;
                isexit = true;
            }
        } else if (floor == 2 && x > 0) {
            x -= speed;
        }
    }

    public void draw(Graphics g) {
        if (count >= goodimage[0].length) {
            count = 0;
        }
        if (isgood) {
            g.drawImage(goodimage[type][count], x, y, width, height, null);
        } else {
            g.drawImage(badimage[type][count], x, y, width, height, null);
        }
        count++;
    }

    public int getType() {
        return type;
    }

    public boolean isIsgood() {
        return isgood;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle2D getbound() {
        return (new Rectangle2D.Double(x, y, width, height));
    }
}
