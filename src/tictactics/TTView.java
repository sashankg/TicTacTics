/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactics;

import APCSAnimation.AnimatedObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HEALASST
 */
public class TTView implements AnimatedObject {

    private TTModel model;
    private int size;
    private boolean isDark;
    private Player player;
    public TTView(TTModel m, int s, Player p) {
        model = m;
        size = 800;
        isDark = true;
        player = p;
    }

    @Override
    public void update(int dt) {
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(new Font("Impact", Font.PLAIN, 35));
        int b = 10;
        int s = ((size - (9 * b)) / 9);
        if (model.getMegaBoard().getInitialPhase()) {
            for (int i = 0; i < 9; i++) {
                if (i % 3 == 0) {
                    switchGrays(g);
                }
                for (int j = 0; j < 9; j++) {
                    boolean marked = false;
                    for (int[] a : player.getInitialMarks()) {
                        if (a[0] == i && a[1] == j) {
                            marked = true;
                            break;
                        }
                    }
                    int x = i * (s + b) + b + i / 3 * 5;
                    int y = j * (s + b) + b + j / 3 * 5;
                    if (j % 3 == 0 && j != 0) {
                        switchGrays(g);
                    }
                    if (!marked) {
                        if (player.canMakeMark(i, j)) {
                            g.setColor(Color.DARK_GRAY);
                            g.fillPolygon(new int[]{x, x + b - 2, x + b + s - 2, x + s, x}, new int[]{y, y - b + 2, y + s - b + 2, y + s, y + s}, 5);
                            switchGrays(g);
                            switchGrays(g);
                            g.fillRect(x + b - 2, y - b + 2, s, s);

                        } else {
                            g.fillRect(x, y, s, s);
                        }
                    } else {
                        if (player.getChar() == 'X') {
                            drawX(g, x, y, s, i / 3, j / 3);
                        } else {
                            drawO(g, x, y, s, i / 3, j / 3);
                        }
                    }
                }
            }
            g.setColor(Color.black);
            g.drawString(player.getChar() + "'s Turn", 350, 850);
            g.drawString(9 - player.getInitialMarksSize() + " initial move(s) left", 250, 900);
            if (model.canConfirm(player)) {
                if (!player.getConfirmed()) {
                    g.drawString("Done", 700, 875);
                } else {
                    g.drawString("Waiting", 650, 875);
                }
            }
        } else {
            for (int i = 0; i < 9; i++) {
                if (i % 3 == 0) {
                    switchGrays(g);
                }
                for (int j = 0; j < 9; j++) {
                    int x = i * (s + b) + b + i / 3 * 5;
                    int y = j * (s + b) + b + j / 3 * 5;
                    if (j % 3 == 0 && j != 0) {
                        switchGrays(g);
                    }
                    setColorOfSmallBoard(g, i / 3, j / 3);
                    if (model.canMakeMark(player, i, j)) {
                        g.setColor(Color.DARK_GRAY);
                        g.fillPolygon(new int[]{x, x + b, x + b + s, x + s, x}, new int[]{y, y - b, y + s - b, y + s, y + s}, 5);
                        switchGrays(g);
                        switchGrays(g);
                        setColorOfSmallBoard(g, i / 3, j / 3);
                        g.fillRect(x + b, y - b, s, s);

                    } else if (model.getSpot(i, j).getOwner().getChar() == 'X') {
                        drawX(g, x, y, s, i / 3, j / 3);
                    } else if (model.getSpot(i, j).getOwner().getChar() == 'O') {
                        drawO(g, x, y, s, i / 3, j / 3);
                    } else {
                        g.fillRect(x, y, s, s);

                    }
                    switchGrays(g);
                    switchGrays(g);
                }
            }
            g.setColor(Color.black);
            g.drawString(model.getTurn().getChar() + "'s Turn", 350, 850);
        }
        isDark = true;
        if (model.getWinner() != Player.getBlankPlayer()) {
            g.setColor(getPlayerColor(model.getWinner()));
            g.fillRect(0, 300, 800, 300);
            g.setColor(Color.white);
            g.drawString(model.getWinner().getChar() + " Won!", 350, 450);
        }
        if(model.getTurn() != player)
        {
            g.setColor(Color.YELLOW);
            if(model.getMoveLocation().x == -1)
            {
                g.drawRect(b/2, b/2, size*9, size*9);
            }
        }
    }

    private void switchGrays(Graphics g) {
        if (isDark) {
            g.setColor(Color.LIGHT_GRAY);
            isDark = false;
        } else {
            g.setColor(Color.GRAY);
            isDark = true;

        }
    }

    private void drawX(Graphics g, int x, int y, int s, int smallBoardX, int smallBoardY) {
        g.setColor(getDarkOrLight(Color.red));
        setColorOfSmallBoard(g, smallBoardX, smallBoardY);
        g.fillRect(x, y, s, s);
        g.setColor(Color.white);
        g.fillPolygon(new int[]{x + s / 6, x + s / 3, x + s / 2, x + s * 2 / 3, x + s * 5 / 6, x + s * 2 / 3, x + s * 5 / 6, x + s * 2 / 3, x + s / 2, x + s / 3, x + s / 6, x + s / 3},
                new int[]{y + s / 3, y + s / 6, y + s / 3, y + s / 6, y + s / 3, y + s / 2, y + s * 2 / 3, y + s * 5 / 6, y + s * 2 / 3, y + s * 5 / 6, y + s * 2 / 3, y + s / 2}, 12);
        switchGrays(g);
        switchGrays(g);

    }

    private void drawO(Graphics g, int x, int y, int s, int smallBoardX, int smallBoardY) {
        g.setColor(getDarkOrLight(Color.blue));
        setColorOfSmallBoard(g, smallBoardX, smallBoardY);
        g.fillRect(x, y, s, s);
        g.setColor(Color.white);
        g.fillOval(x + s / 12, y + s / 12, s * 5 / 6, s * 5 / 6);
        g.setColor(getDarkOrLight(Color.blue));
        setColorOfSmallBoard(g, smallBoardX, smallBoardY);
        g.fillOval(x + s / 3, y + s / 3, s / 3, s / 3);
        switchGrays(g);
        switchGrays(g);
    }

    private void setColorOfSmallBoard(Graphics g, int x, int y) {
        char c = model.getSmallBoard(x, y).getOwner().getChar();
        if (c == 'X') {
            g.setColor(getDarkOrLight(Color.red));
        } else if (c == 'O') {
            g.setColor(getDarkOrLight(Color.blue));
        } else if (c == 'W') {
            g.setColor(getDarkOrLight(Color.magenta));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Color getDarkOrLight(Color c) {
        if (isDark) {
            return c.darker();
        }
        return c;
    }

    public Color getPlayerColor(Player p) {
        if (p == model.getXPlayer()) {
            return Color.red;
        } else if (p == model.getOPlayer()) {
            return Color.blue;
        } else {
            return Color.magenta;
        }
    }
}
