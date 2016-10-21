/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactics;

import APCSAnimation.AnimatedObject;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HEALASST
 */
public class RandomControl implements AnimatedObject{
    
    TTModel model;
    TTView view;
    int delay;
    
    public RandomControl(TTModel m, TTView v)
    {
        model = m;
        view = v;
        delay = 50;
    }

    @Override
    public void update(int dt) {
        if(model.getMegaBoard().getInitialPhase())
        {
            for(int i = 0; i < 9; i++)
            {
                ArrayList<Point> moves = getPossibleInitialMoves();
                Point p = moves.get((int)(moves.size()*Math.random()));
                model.makeInitialMark(view.getPlayer(), p.x, p.y);
            }
            model.confirm(view.getPlayer());
        }
        else
        {
            if(model.getTurn() == view.getPlayer())
            {
                if ( delay < 0 )
                {
                    ArrayList<Point> moves = getPossibleMoves();
                    Point p = moves.get((int)(moves.size()*Math.random()));
                    model.makeMark(view.getPlayer(), p.x, p.y);
                    delay = 500;
                }
                else 
                {
                    delay -= dt;
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
    }
    
    private ArrayList<Point> getPossibleInitialMoves()
    {
        ArrayList<Point> o = new ArrayList<>();
        for(int i =0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(view.getPlayer().canMakeMark(i, j))
                {
                    o.add(new Point(i, j));
                }
            }
        }
        return o;
    }
    
    private ArrayList<Point> getPossibleMoves()
    {
        ArrayList<Point> o = new ArrayList<>();
        for(int i =0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(model.canMakeMark(view.getPlayer(), i, j))
                {
                    o.add(new Point(i, j));
                }
            }
        }
        return o;
    }
    
}
