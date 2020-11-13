/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thecastleg;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Windows
 */
public class imp extends mino{
    imp(boolean faceright,int y){
        super(faceright,y);
        badguyimage = new Image[5];
        badguyLimage = new Image[5];
        upimageR=new Image[5];
        upimageL=new Image[5];
        for (int i = 0; i < badguyimage.length; i++) {
            URLguy = this.getClass().getResource("impR" + (i+1) + ".png");
            badguyimage[i] = new ImageIcon(URLguy).getImage();
            URLguy = this.getClass().getResource("impL" + (i+1) + ".png");
            badguyLimage[i] = new ImageIcon(URLguy).getImage();
            URLguy = this.getClass().getResource("upimpR" + (i+1) + ".png");
            upimageR[i] = new ImageIcon(URLguy).getImage();
            URLguy = this.getClass().getResource("upimpL" + (i+1) + ".png");
            upimageL[i] = new ImageIcon(URLguy).getImage();
        }    
    }
}
