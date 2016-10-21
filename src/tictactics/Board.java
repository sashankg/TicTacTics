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
public class Board {
    public SpotInterface[][] spots;
    
    public Board(SpotInterface[][] s)
    {
        spots = new SpotInterface[3][3];
        for(int i = 0; i < s.length; i++)
        {
            for(int j = 0; j < s[i].length; j++)
            {
                if(s.getClass().equals(SmallBoard[][].class))
                {
                    spots[i][j] = new SmallBoard();
                }
                else if(s.getClass().equals(Spot[][].class))
                {
                    spots[i][j] = new Spot();
                }
            }
        }
    }
    public void setSpot(Player p, int x, int y)
    {
        spots[x][y].setOwner(p);
    }
    public SpotInterface getSpot(int x, int y)
    {
        return spots[x][y];
    }
    protected SpotInterface[][] getSpots()
    {
        return spots;
    }
    
    
    public boolean canMakeAnyMove()
    {
        boolean can = false;
        for(SpotInterface[] a : getSpots())
        {
            for(SpotInterface spot : a)
            {
                if(spot.getOwner() == Player.getBlankPlayer())
                {
                    can=true;
                }
            }
        }
        return can;
    }
}
