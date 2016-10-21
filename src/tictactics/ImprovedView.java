/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Sashank
 */
public class ImprovedView extends TTView{
    ArrayList<SpotView> spots;
    SpotView testSpot;
    TTModel model;
    public ImprovedView(TTModel m, int s, Player p) {
        super(m, s, p);
        model = m;
        spots = new ArrayList<>();
        int b = 10;
        int sz = ((800 - (9 * b)) / 9);
        for(int i = 0; i < 9; i++)
        {
            for(int j = 8; j >= 0; j--)
            {
                int x = i * (sz + b) + b + i / 3 * 5;
                int y = j * (sz + b) + b + j / 3 * 5;
                spots.add(new SpotView(x, y, sz, m, this, i, j));
            }
        }
    }
    
    @Override
    public void update(int dt)
    {
        for(SpotView s : spots)
        {
            s.update(dt);
        }
    }
    
    @Override
    public void paint(Graphics g)
    {
        g.setFont(new Font("Impact", Font.PLAIN, 35));

        for(int i = 80; i >=0; i--)
        {
            spots.get(i).paint(g);
        }
        if (model.getMegaBoard().getInitialPhase()) {
            g.setColor(Color.black);
            g.drawString("Initial Phase", 350, 850);
            g.drawString(9 - super.getPlayer().getInitialMarksSize() + " initial move(s) left", 250, 900);
            if (model.canConfirm(super.getPlayer())) {
                if (!super.getPlayer().getConfirmed()) {
                    g.drawString("Done", 700, 875);
                } else {
                    g.drawString("Waiting", 650, 875);
                }
            }
        }
        else
        {
            g.setColor(Color.black);
            g.drawString(model.getTurn().getChar() + "'s Turn", 350, 850);
        }
        if (model.getWinner() != Player.getBlankPlayer()) {
            g.setColor(getPlayerColor(model.getWinner()));
            g.fillRect(0, 300, 800, 300);
            g.setColor(Color.white);
            g.drawString(model.getWinner().getChar() + " Won!", 350, 450);
        }
    }
    
    
}
