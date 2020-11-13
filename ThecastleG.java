
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thecastleg;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.Random;
import javax.swing.*;
/**
 *
 * @author Windows
 */

public class ThecastleG{
    public static JFrame frame;
    public static final int height=800,width=1200; 
    public static int status;
    public static Timer timer;
    public static Random rand=new Random();
    public static boolean isminimize;
    public static void main(String[] args) {
        frame=new JFrame("The Castle's Guardian");
        WindowStateListener listener = new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent evt) {
                int oldState = evt.getOldState();
                int newState = evt.getNewState();

                if ((oldState & Frame.ICONIFIED) == 0 && (newState & Frame.ICONIFIED) != 0) {
                    isminimize=true;
                } else if ((oldState & Frame.ICONIFIED) != 0 && (newState & Frame.ICONIFIED) == 0) {
                    isminimize=false;
                }

            }
        };
        frame.addWindowStateListener(listener);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.add(new mainscreen());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
    
}
