/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactics;

import APCSAnimation.AnimatedObject;
import APCSAnimation.ClickableObject;
import java.awt.Graphics;

/**
 *
 * @author Sashank
 */
public class TTControl implements ClickableObject, AnimatedObject {

    protected TTModel model;
    protected int size;
    protected TTView view;
    protected boolean confirmButton;

    public TTControl(TTModel m, int s, TTView v) {
        model = m;
        size = s;
        view = v;
    }

    @Override
    public boolean contains(int x, int y) {
        if (x > 680 && x < 800 && model.canConfirm(view.getPlayer()) && y > 855 && y < 900) {
            confirmButton = true;
            return true;
        }
        return x < size && y < size;
    }

    @Override
    public void handleMouseClick(int x, int y) {
        if (confirmButton) {
            model.confirm(view.getPlayer());
            confirmButton = false;
        } else {
            int b = 10;
            int s = (size - (9 * b)) / 9;
            int ix = (x - b) / (s + b);
            int iy = (y - b) / (s + b);
            if (model.getMegaBoard().getInitialPhase() && !view.getPlayer().getConfirmed()) {
                model.makeInitialMark(view.getPlayer(), ix, iy);

            } else {
                if (model.canMakeMark(view.getPlayer(), ix, iy)) {
                    model.makeMark(view.getPlayer(), ix, iy);
                }
            }
        }
    }

    @Override
    public void update(int dt) {
    }

    @Override
    public void paint(Graphics g) {
    }
}
