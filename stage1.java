/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thecastleg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Windows
 */
public class stage1 extends JPanel implements ActionListener, KeyListener {

    protected URL url, URLnum;
    protected Image bg, heart, castle, cannon, cannonball, scoreimage, blood, timeimage, esc, pausescreen;
    protected final Image[] numimage;
    protected player knight;
    protected int count, ammo, hp;
    protected int xdoor = 536, xcannon = 1088;
    protected int[][] xrockpile = {{56, 152}, {696, 792}};
    protected int[][] xladder = {{342, 720}, {102, 534, 1014}, {246, 846}};
    protected int[] yfloor = {670, 530, 390, 240};
    protected Timer time;
    protected ArrayList<Rock> listrock = new ArrayList<Rock>();
    protected ArrayList<Cannon> listcannon = new ArrayList<Cannon>();
    protected ArrayList<badguy1> listbadguy = new ArrayList<badguy1>();
    protected ArrayList<item> listitem = new ArrayList<item>();
    protected int score;
    protected boolean isrun;

    public stage1() {
        ThecastleG.status = 1;
        numimage = new Image[10];
        for (int i = 0; i < numimage.length; i++) {
            String s = "num";
            URLnum = this.getClass().getResource(s + i + ".png");
            numimage[i] = new ImageIcon(URLnum).getImage();
        }

        score = 0;
        count = 90;
        hp = 3;
        ammo = 3;
        isrun = true;

        url = this.getClass().getResource("blood.png");
        blood = new ImageIcon(url).getImage();
        url = this.getClass().getResource("score.png");
        scoreimage = new ImageIcon(url).getImage();
        url = this.getClass().getResource("esc.png");
        esc = new ImageIcon(url).getImage();
        url = this.getClass().getResource("pausescreen.png");
        pausescreen = new ImageIcon(url).getImage();
        url = this.getClass().getResource("time.png");
        timeimage = new ImageIcon(url).getImage();
        url = this.getClass().getResource("stage1bg.png");
        bg = new ImageIcon(url).getImage();
        url = this.getClass().getResource("heart.png");
        heart = new ImageIcon(url).getImage();
        url = this.getClass().getResource("castle.png");
        castle = new ImageIcon(url).getImage();
        url = this.getClass().getResource("cannon.png");
        cannon = new ImageIcon(url).getImage();
        url = this.getClass().getResource("cannonball.png");
        cannonball = new ImageIcon(url).getImage();

        knight = new player();

        ThecastleG.timer = new Timer(30, this);
        addKeyListener(this);
        this.setLayout(null);
        ThecastleG.timer.start();
        summon.start();
        summonitem.start();

        time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (!ThecastleG.isminimize && isrun) {
                    if (count > 0) {
                        count--;
                    } else {
                        time.stop();
                    }
                }
            }
        });
        time.start();
    }

    Thread summon = new Thread(new Runnable() {
        @Override
        public void run() {
            while (count > 0) {
                if (listbadguy.size() < 10 && isrun && !ThecastleG.isminimize) {
                    summonenemy(ThecastleG.rand.nextBoolean());
                }
                try {
                    summon.sleep((long) (Math.random() * 1000) + 4500); 
                } catch (InterruptedException ex) {
                }
            }
        }
    });

    Thread summonitem = new Thread(new Runnable() {
        @Override
        public void run() {
            while (count > 0) {
                if(isrun && !ThecastleG.isminimize) listitem.add(new item(yfloor[1]));
                try {
                    summonitem.sleep((long) (Math.random() * 1000) + 10000);
                } catch (InterruptedException ex) {
                }
            }
        }
    });

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!ThecastleG.isminimize&&isrun) {
            
            if (listbadguy.isEmpty() && count > 0 ) {
                int random = ThecastleG.rand.nextInt(100);
                if (random <= 59) {
                    summonenemy(true);
                } else {
                    summonenemy(false);
                }
            } else if ((count == 0 && listbadguy.isEmpty()) || hp <= 0) {
                ThecastleG.timer.stop();
                time.stop();
                boolean iswin;
                iswin = hp > 0;
                this.requestFocusInWindow(false);
                this.removeKeyListener(this);
                ThecastleG.timer.removeActionListener(this);
                ThecastleG.frame.remove(this);
                this.removeAll();
                ThecastleG.frame.setFocusable(false);
                endgamescreen es = new endgamescreen(iswin, score + (5 * hp) + (3 * ammo));
                ThecastleG.frame.add(es);
                ThecastleG.frame.setVisible(true);
                es.requestFocus();
            }
        }
        repaint();
    }

    public void summonenemy(boolean type) {
        if (type) {
            listbadguy.add(new badguy1(ThecastleG.rand.nextBoolean(), yfloor[0]));
        } else {
            listbadguy.add(new wizard(ThecastleG.rand.nextBoolean(), yfloor[0]));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ThecastleG.status == 1) {
            g.drawImage(bg, 0, 0, ThecastleG.width, ThecastleG.height, this);
            knight.draw(g);
            for (int i = 0; i < listcannon.size(); i++) {
                if (listcannon.get(i).getX() < 0) {
                    listcannon.remove(i);
                    i--;
                } else {
                    listcannon.get(i).move();
                    listcannon.get(i).drawcannon(g);
                }
            }
            
            for (int i = 0; i < listbadguy.size(); i++) {
                boolean isalive = true;
                if(listbadguy.get(i) instanceof wizard){
                    for(int j=0;j<listbadguy.size();j++){
                        if((!(listbadguy.get(j) instanceof wizard))&&Intersect(listbadguy.get(i).getbound(),listbadguy.get(j).getbound())){
                            (listbadguy.get(j)).setSpeed(5);
                        }
                    }
                }
                
                for (int j = 0; j < this.listrock.size(); j++) {
                    if (Intersect(listbadguy.get(i).getbound(), listrock.get(j).getbound()) && listbadguy.get(i).currentfloor != xladder.length + 1) {
                        g.drawImage(blood, listbadguy.get(i).getX(), listbadguy.get(i).getY(), 100, 100, this);
                        listrock.remove(j);
                        isalive = false;
                        if (listbadguy.get(i) instanceof wizard) {
                            score += 2;
                        } else if (listbadguy.get(i) instanceof badguy1) {
                            score += 1;
                        }
                        break;
                    }
                }

                for (int j = 0; j < listcannon.size() && isalive; j++) {
                    if (Intersect(listbadguy.get(i).getbound(), listcannon.get(j).getbound())) {
                        g.drawImage(blood, listbadguy.get(i).getX(), listbadguy.get(i).getY(), 100, 100, this);
                        listcannon.remove(j);
                        isalive = false;
                        if (listbadguy.get(i) instanceof wizard) {
                            score += 2;
                        } else if (listbadguy.get(i) instanceof badguy1) {
                            score += 1;
                        }
                        break;
                    }
                }

                if (listbadguy.get(i).getX() >= xdoor && listbadguy.get(i).getX() <= xdoor + 10 && listbadguy.get(i).getCurrentfloor() == yfloor.length && isalive) {
                    isalive = false;
                    hp--;
                }

                boolean check = false;
                for (int j = 0; j < xladder.length && isalive && listbadguy.get(i).getCurrentfloor() != yfloor.length; j++) {
                    for (int k = 0; k < xladder[j].length; k++) {
                        if (listbadguy.get(i).getCurrentfloor() == j + 1 && listbadguy.get(i).getX() <= xladder[j][k] + 5 && listbadguy.get(i).getX() >= xladder[j][k]) {
                            int n = ThecastleG.rand.nextInt(100);
                            int rate = 50;
                            if (n <= rate || (listbadguy.get(i).getY() < yfloor[listbadguy.get(i).getCurrentfloor() - 1])) {
                                listbadguy.get(i).moveup(yfloor[j + 1]);
                                check = true;
                                break;
                            }
                        }
                        if (check) {
                            break;
                        }
                    }
                }
                if (!check) {
                    listbadguy.get(i).move();
                }

                if (!isalive) {
                    listbadguy.remove(i);
                    i--;
                } else {
                    listbadguy.get(i).draw(g);
                }
            }

            for (int i = 0; i < listrock.size(); i++) {
                boolean check = false;
                if (listrock.get(i).getYrock() + listrock.get(i).getRockheight() > ThecastleG.height) {
                    check = true;
                }
                for (int j = 0; j < listitem.size() && !check; j++) {
                    if (this.Intersect(listrock.get(i).getbound(), listitem.get(j).getbound())) {
                        switch (listitem.get(j).getType()) {
                            case 0:
                                if (listitem.get(j).isIsgood()) {
                                    if (hp < 3) {
                                        hp++;
                                    } else {
                                        score += 5;
                                    }
                                } else {
                                    hp--;
                                }
                                break;
                            case 1:
                                if (listitem.get(j).isIsgood()) {
                                    if (ammo < 3) {
                                        ammo++;
                                    } else {
                                        score += 3;
                                    }
                                } else if (ammo != 0) {
                                    ammo--;
                                }
                                break;
                            default:
                                if (listitem.get(j).isIsgood()) {
                                    count += 10;
                                } else {
                                    count -= 10;
                                    if (count < 0) {
                                        count = 0;
                                    }
                                }
                                break;
                        }
                        g.drawImage(blood, listitem.get(j).getX(), listitem.get(j).getY(), 100, 100, this);
                        listitem.remove(j);
                        check = true;
                        break;
                    }
                }

                if (check) {
                    listrock.remove(i);
                    i--;
                } else {
                    listrock.get(i).moverock(0);
                    listrock.get(i).drawrock(g);
                }
            }

            for (int i = 0; i < listitem.size(); i++) {
                listitem.get(i).draw(g);
                listitem.get(i).move(yfloor);
                if (listitem.get(i).getX() >= ThecastleG.width) {
                    listitem.remove(i);
                    i--;
                }
            }

            g.drawImage(castle, 980, 10, 50, 50, this);
            for (int i = 0; i < hp; i++) {
                int thiswidth = 35;
                g.drawImage(heart, 1035 + (i * 10) + (i * thiswidth), 30, thiswidth, 30, this);
            }
            g.drawImage(cannon, 980, 70, 50, 50, this);
            for (int i = 0; i < ammo; i++) {
                int thiswidth = 35;
                g.drawImage(cannonball, 1035 + (i * 10) + (i * thiswidth), 80, thiswidth, 30, this);
            }

            g.drawImage(esc, 10, 10, 100, 40, this);
            g.drawImage(timeimage, 10, 60, 100, 40, this);
            String Stime = String.valueOf(count);
            for (int i = 0; i < Stime.length(); i++) {
                int thiswidth = 20;
                g.drawImage(numimage[Integer.parseInt(String.valueOf(Stime.charAt(i)))], 115 + (i * thiswidth), 60, thiswidth, 40, this);
            }

            g.drawImage(scoreimage, 10, 110, 100, 40, this);
            String Sscore = String.valueOf(score);
            for (int i = 0; i < Sscore.length(); i++) {
                int thiswidth = 20;
                g.drawImage(numimage[Integer.parseInt(String.valueOf(Sscore.charAt(i)))], 115 + (i * thiswidth), 110, thiswidth, 40, this);
            }
            if (!isrun) {
                g.drawImage(pausescreen, 230, 50, 700, 700, this);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!ThecastleG.isminimize){
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && isrun) {
                knight.move(false);
            } else if (key == KeyEvent.VK_RIGHT && isrun) {
                knight.move(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!ThecastleG.isminimize){
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) && isrun) {
                knight.count = 0;
            }
            if (key == KeyEvent.VK_ENTER) {
                if (!isrun) {
                    this.requestFocusInWindow(false);
                    this.removeKeyListener(this);
                    ThecastleG.timer.removeActionListener(this);
                    ThecastleG.frame.remove(this);
                    this.removeAll();
                    ThecastleG.frame.setFocusable(false);
                    mainscreen mc = new mainscreen();
                    ThecastleG.frame.add(mc);
                    ThecastleG.frame.setVisible(true);
                    mc.requestFocus();
                }
            }
            if (key == KeyEvent.VK_R) {
                if (!isrun) {
                    restart();
                }
            }
            if (key == KeyEvent.VK_ESCAPE) {
                if (isrun) {
                    ThecastleG.timer.stop();
                    time.stop();
                    isrun = false;
                    repaint();
                } else if (!isrun) {
                    ThecastleG.timer.start();
                    time.start();
                    isrun = true;
                }
            }
            if (key == KeyEvent.VK_Z && isrun) {
                int kx = knight.getX();
                if (knight.isCarry()) {
                    Rock rock = knight.getRock();
                    rock.setIsfall(true);
                    listrock.add(rock);
                    knight.setCarry(false);
                } else if ((kx >= xrockpile[0][0] && kx <= xrockpile[0][1]) || (kx >= xrockpile[1][0] && kx <= xrockpile[1][1])) {
                    knight.setCarry(true);
                }
            }
            if (key == KeyEvent.VK_X && isrun) {
                if (!knight.isCarry() && knight.getX() >= xcannon && ammo > 0) {
                    listcannon.add(new Cannon(xcannon - 15));
                    ammo--;
                }
            }
        }
        
    }

    public void restart() {
        this.requestFocusInWindow(false);
        this.removeKeyListener(this);
        ThecastleG.timer.removeActionListener(this);
        ThecastleG.frame.remove(this);
        this.removeAll();
        ThecastleG.frame.setFocusable(false);
        stage1 st1 = new stage1();
        ThecastleG.frame.add(st1);
        ThecastleG.frame.setVisible(true);
        st1.requestFocus();
    }

    public boolean Intersect(Rectangle2D a, Rectangle2D b) {
        return (a.intersects(b));
    }
}
