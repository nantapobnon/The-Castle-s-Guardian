/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thecastleg;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Windows
 */
public class mino extends badguy1{
    protected Image[] upimageR,upimageL;
    protected boolean isUP;
    mino(boolean faceright,int y){
        super(faceright,y);
        isUP=false;
        badguyimage = new Image[6];
        badguyLimage = new Image[6];
        upimageR=new Image[6];
        upimageL=new Image[6];
        for (int i = 0; i < badguyimage.length; i++) {
            URLguy = this.getClass().getResource("minoR" + (i+1) + ".png");
            badguyimage[i] = new ImageIcon(URLguy).getImage();
            URLguy = this.getClass().getResource("minoL" + (i+1) + ".png");
            badguyLimage[i] = new ImageIcon(URLguy).getImage();
            URLguy = this.getClass().getResource("upminoR" + (i+1) + ".png");
            upimageR[i] = new ImageIcon(URLguy).getImage();
            URLguy = this.getClass().getResource("upminoL" + (i+1) + ".png");
            upimageL[i] = new ImageIcon(URLguy).getImage();
        }    
    }
    
    @Override
    public void draw(Graphics g) {
//        if (count >= badguyimage.length) {
//            count = 0;
//        }
        if(isUP){
            if (faceright) {
                g.drawImage(upimageR[count], x, y, width, height, null);
            } else {
                g.drawImage(upimageL[count], x, y, width, height, null);
            }
        }
        else{
            if (faceright) {
                g.drawImage(badguyimage[count], x, y, width, height, null);
            } else {
                g.drawImage(badguyLimage[count], x, y, width, height, null);
            }
        }
    }

    public void setIsUP(boolean isUP) {
        this.isUP = isUP;
    }

    public boolean isIsUP() {
        return isUP;
    }
}
