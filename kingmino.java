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
public class kingmino extends badguy1{
    kingmino(boolean faceright,int y){
        super(faceright,y);
        badguyimage = new Image[6];
        badguyLimage = new Image[6];
        for (int i = 0; i < badguyimage.length; i++) {
            URLguy = this.getClass().getResource("kingminoR" + (i+1) + ".png");
            badguyimage[i] = new ImageIcon(URLguy).getImage();
            URLguy = this.getClass().getResource("kingminoL" + (i+1) + ".png");
            badguyLimage[i] = new ImageIcon(URLguy).getImage();
        }    
    }
}
