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
public class wizard extends badguy1{
    wizard(){
        
    }
    wizard(boolean faceright,int y){
        super(faceright,y);
        badguyimage = new Image[6];
        badguyLimage = new Image[6];
        for (int i = 0; i < badguyimage.length; i++) {
            URLguy = this.getClass().getResource("wizard" + i + ".png");
            badguyimage[i] = new ImageIcon(URLguy).getImage();
            URLguy = this.getClass().getResource("wizardL" + i + ".png");
            badguyLimage[i] = new ImageIcon(URLguy).getImage();
        }
        width=50;
        height=75;
    }
}
