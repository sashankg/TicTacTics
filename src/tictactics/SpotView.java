/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactics;

import APCSAnimation.Mouse;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Sashank
 */
public class SpotView {

    Spot spot;
    SmallBoard smallBoard;
    int x, y, size, height;
    int delay;
    TTModel model;
    TTView view;
    int ix, iy;
    boolean isRising, isSinking;
    int speed = 2;

    public SpotView(int xc, int yc, int width, TTModel m, TTView v, int i, int j) {
        spot = m.getSpot(i, j);
        x = xc;
        y = yc;
        size = width;
        model = m;
        view = v;
        height = 12;
        ix = i;
        iy = j;
        smallBoard = m.getSmallBoard(i / 3, j / 3);
        //spot.setOwner(Player.getWildPlayer());
    }

    public void update(int dt) {
        if (model.getMegaBoard().getInitialPhase()) {
            if (view.getPlayer().canMakeMark(ix, iy)) {
                if (Mouse.getX() > x + height
                        && Mouse.getY() > y - height
                        && Mouse.getX() < x + size + height
                        && Mouse.getY() < y + size - height
                        && !isSinking) {
                    riseTo(16);
                } else {
                    sinkTo(12);
                    riseTo(12);
                }
            } else {
                sinkTo(0);
            }
        } else {
            if (spot.getOwner().getChar() == '_' && model.canMakeMark(view.getPlayer(), ix, iy)) {
                if (Mouse.getX() > x + height
                        && Mouse.getY() > y - height
                        && Mouse.getX() < x + size + height
                        && Mouse.getY() < y + size - height
                        && !isSinking) {
                    riseTo(16);
                } else {
                    sinkTo(12);
                    riseTo(12);
                }
            } else {
                sinkTo(0);
            }
        }
    }

    public void paint(Graphics g) {
        int b = 10;
        int s = size;
        g.setColor(Color.DARK_GRAY);
        g.fillPolygon(new int[]{x, x + height, x + s + height, x + s, x}, new int[]{y, y - height, y + s - height, y + s, y + s}, 5);
        setColorToGray(g);
        if (model.getMegaBoard().getInitialPhase()) {
            boolean marked = false;
            for (int[] a : view.getPlayer().getInitialMarks()) {
                if (a[0] == ix && a[1] == iy) {
                    marked = true;
                    break;
                }
            }
            if (marked) {
                if (view.getPlayer().getChar() == 'X') {
                    drawX(g, x + height, y - height, s);
                } else {
                    drawO(g, x + height, y - height, s);
                }
            } else {
                g.fillRect(x + height, y - height, s, s);
            }
        } else {
            if (spot.getOwner().getChar() == 'X') {
                drawX(g, x + height, y - height, s);
            } else if (spot.getOwner().getChar() == 'O') {
                drawO(g, x + height, y - height, s);
            } else {
                setRightColor(g, g.getColor());
                g.fillRect(x + height, y - height, s, s);
            }
        }
    }

    private void setColorToGray(Graphics g) {
        if ((ix / 3 != 1 && iy / 3 != 1) || ix / 3 == iy / 3) {
            g.setColor(Color.LIGHT_GRAY);
        } else {
            g.setColor(Color.GRAY);
        }
    }

    public void drawX(Graphics g, int x, int y, int s) {
        setRightColor(g, Color.RED);
        g.fillRect(x, y, s, s);
        g.setColor(Color.white);
        g.fillPolygon(new int[]{x + s / 6, x + s / 3, x + s / 2, x + s * 2 / 3, x + s * 5 / 6, x + s * 2 / 3, x + s * 5 / 6, x + s * 2 / 3, x + s / 2, x + s / 3, x + s / 6, x + s / 3},
                new int[]{y + s / 3, y + s / 6, y + s / 3, y + s / 6, y + s / 3, y + s / 2, y + s * 2 / 3, y + s * 5 / 6, y + s * 2 / 3, y + s * 5 / 6, y + s * 2 / 3, y + s / 2}, 12);
    }

    public void drawO(Graphics g, int x, int y, int s) {
        setRightColor(g, Color.BLUE);
        g.fillRect(x, y, s, s);
        Color c = g.getColor();
        g.setColor(Color.white);
        g.fillOval(x + s / 12, y + s / 12, s * 5 / 6, s * 5 / 6);
        g.setColor(c);
        g.fillOval(x + s / 3, y + s / 3, s / 3, s / 3);
    }

    public void setRightColor(Graphics g, Color c) {
        if (smallBoard.getOwner() != Player.getBlankPlayer()) {
            if(smallBoard.getOwner() == Player.getWildPlayer()) c = Color.MAGENTA;
            else if(smallBoard.getOwner().getChar() == 'X') c = Color.RED;
            else if(smallBoard.getOwner().getChar() == 'O') c = Color.BLUE;
        }
        if (g.getColor() == Color.LIGHT_GRAY) {
            g.setColor(c);
        } else {
            g.setColor(c.darker());
        }
    }

    public void riseTo(int h) {
        if (height >= h) {
            isRising = false;
        } else {
            isRising = true;
        }
        if (isRising) {
            height += speed;
        }
    }

    public void sinkTo(int h) {
        if (height <= h) {
            isSinking = false;
        } else {
            isSinking = true;
        }
        if (isSinking) {
            height -= speed;
        }
    }
}
