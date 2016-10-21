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
public class Spot implements SpotInterface{
    private Player owner;
    private boolean disabled;
    
    public Spot()
    {
        owner = Player.getBlankPlayer();
        disabled = false;
    }
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player p) {
        owner = p;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }  

    @Override
    public void setDisabled(boolean b) {
        disabled = b;
    }
}
