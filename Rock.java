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
class Rock {
    private final URL URLrock; 
    private final Image rock;
    private final int rockwidth=60,rockheight=60;
    private int xrock,yrock,speed;
    public boolean isfall;
    Rock(int x){
        URLrock = this.getClass().getResource("userock.png");
        rock = new ImageIcon(URLrock).getImage();
        xrock=x+5;
        yrock=249;
        isfall=false;
        speed=13;
    }
    public void moverock(int x){
        if(isfall){
            yrock+=speed;
        }
        else{
            xrock=x+5;
        }
    }
    
    public void setIsfall(boolean isfall) {
        this.isfall = isfall;
    }

    public int getRockwidth() {
        return rockwidth;
    }

    public int getRockheight() {
        return rockheight;
    }

    public int getXrock() {
        return xrock;
    }

    public int getYrock() {
        return yrock;
    }
    public void drawrock(Graphics g){
        g.drawImage(rock, xrock, yrock, rockwidth, rockheight,null);
    }
    
    public Rectangle2D getbound(){
        return (new Rectangle2D.Double(xrock,yrock,rockwidth,rockheight));
    }

    public void setYrock(int yrock) {
        this.yrock = yrock;
    }
    
}
