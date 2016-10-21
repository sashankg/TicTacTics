/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactics;

/**
 *
 * @author Sashank
 */
public class SmallBoard extends Board implements SpotInterface{
    private Player winner;

    public SmallBoard() {
        super(new Spot[3][3]);
        winner = Player.getBlankPlayer();

    }
    
    @Override
    public Player getOwner() {
        return winner;
    }

    @Override
    public void setOwner(Player p) {
        winner = p;
    }

    @Override
    public boolean isDisabled() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setDisabled(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean canDisable()
    {
        SpotInterface[][] s = getSpots();
        int emptySpots = 0;
        for(SpotInterface[] a : s)
        {
            for(SpotInterface p : a)
            {
                if(p.getOwner() == Player.getBlankPlayer())
                {
                    emptySpots++;
                }
            }
        }
        return emptySpots > 1;
    }
}
