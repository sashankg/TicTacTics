/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactics;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Sashank
 */
public class MegaBoard extends Board {
    
    private boolean initialPhase;
    private Point disabled;

    public MegaBoard() {
        super(new SmallBoard[3][3]);
        initialPhase = true;
        disabled = new Point(-1, -1);
    }    
    
    public void setInitialPhase(boolean b)
    {
        initialPhase = b;
    }
    
    public boolean getInitialPhase()
    {
        return initialPhase;
    }
    
    public void disableSpot(int xi, int yi, int xf, int yf)
    {
        SmallBoard s = getSpot(xf, yf);
        if(s.getSpot(xi, yi).getOwner().getChar() == '_' && s.canDisable())
        {
            s.getSpot(xi, yi).setDisabled(true);
            disabled.setLocation(xf*3 + xi, yf*3 + yi);
            return;
        }
        disabled.setLocation(-1, -1);
    }
    
    public void reenableSpot()
    {
        if(disabled.x + disabled.y >= 0)
        {
            getSpot(disabled.x/3, disabled.y/3).getSpot(disabled.x%3, disabled.y%3).setDisabled(false);
        }
    }

    @Override
    public SmallBoard getSpot(int x, int y) {
        return (SmallBoard)super.getSpot(x, y); //To change body of generated methods, choose Tools | Templates.
    }
}
