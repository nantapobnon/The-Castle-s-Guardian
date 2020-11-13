/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thecastleg;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Windows
 */
public class stage2 extends stage1 {

    protected int[][] xrockpile = {{264, 368}, {904, 1000}};
    protected int[][] xladder = {{328, 800}, {96, 560, 1080}, {216, 864}};
    protected int[] yfloor = {658, 515, 375, 240};

    public stage2() {
        super();
        ThecastleG.status = 2;
        url = this.getClass().getResource("stage2bg.png");
        bg = new ImageIcon(url).getImage();

        xdoor = 584;
        xcannon = 1090;
    }

    @Override
    public void summonenemy(boolean type) {
        if (type) {
            listbadguy.add(new mino(ThecastleG.rand.nextBoolean(), this.yfloor[0]));
        } else {
            listbadguy.add(new kingmino(ThecastleG.rand.nextBoolean(), this.yfloor[0]));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, ThecastleG.width, ThecastleG.height - 40, this);
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

            if (listbadguy.get(i) instanceof kingmino) {
                for (int j = 0; j < listbadguy.size(); j++) {
                    if ((listbadguy.get(j) instanceof mino) && Intersect(listbadguy.get(i).getbound(), listbadguy.get(j).getbound())) {
                        ((mino) listbadguy.get(j)).setIsUP(true);
                    }
                }
            }

            for (int j = 0; j < this.listrock.size(); j++) {
                if (Intersect(listbadguy.get(i).getbound(), listrock.get(j).getbound()) && listbadguy.get(i).currentfloor != xladder.length + 1) {
                    g.drawImage(blood, listbadguy.get(i).getX(), listbadguy.get(i).getY(), 100, 100, this);
                    listrock.remove(j);
                    if (listbadguy.get(i) instanceof kingmino) {
                        score += 2;
                        isalive = false;
                        break;
                    } else if (listbadguy.get(i) instanceof mino) {
                        if (((mino) listbadguy.get(i)).isIsUP()) {
                            ((mino) listbadguy.get(i)).setIsUP(false);
                        } else {
                            score += 1;
                            isalive = false;
                            break;
                        }
                    }
                }
            }

            for (int j = 0; j < listcannon.size() && isalive; j++) {
                if (Intersect(listbadguy.get(i).getbound(), listcannon.get(j).getbound())) {
                    g.drawImage(blood, listbadguy.get(i).getX(), listbadguy.get(i).getY(), 100, 100, this);
                    listcannon.remove(j);
                    isalive = false;
                    if (listbadguy.get(i) instanceof kingmino) {
                        score += 2;
                        break;
                    } else {
                        score += 1;
                        break;
                    }
                }
            }

            if (listbadguy.get(i).getX() >= xdoor && listbadguy.get(i).getX() <= xdoor + 10 && listbadguy.get(i).getCurrentfloor() == this.yfloor.length && isalive) {
                isalive = false;
                hp--;
            }

            boolean check = false;
            for (int j = 0; j < xladder.length && isalive && listbadguy.get(i).getCurrentfloor() != this.yfloor.length; j++) {
                for (int k = 0; k < xladder[j].length; k++) {
                    if (listbadguy.get(i).getCurrentfloor() == j + 1 && listbadguy.get(i).getX() <= xladder[j][k] + 5 && listbadguy.get(i).getX() >= xladder[j][k]) {
                        int n = ThecastleG.rand.nextInt(100);
                        int rate = 50;

                        if (n <= rate || (listbadguy.get(i).getY() < yfloor[listbadguy.get(i).getCurrentfloor() - 1])) {

                            listbadguy.get(i).moveup(this.yfloor[j + 1]);
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

    @Override
    public void keyReleased(KeyEvent e) {
        if (!ThecastleG.isminimize) {
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

    @Override
    public void restart() {
        this.requestFocusInWindow(false);
        this.removeKeyListener(this);
        ThecastleG.timer.removeActionListener(this);
        ThecastleG.frame.remove(this);
        this.removeAll();
        ThecastleG.frame.setFocusable(false);
        stage2 st2 = new stage2();
        ThecastleG.frame.add(st2);
        ThecastleG.frame.setVisible(true);
        st2.requestFocus();
    }
}
