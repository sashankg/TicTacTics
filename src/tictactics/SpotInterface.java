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
public interface SpotInterface {
    public Player getOwner();
    public void setOwner(Player p);
    public boolean isDisabled();
    public void setDisabled(boolean b);
}
