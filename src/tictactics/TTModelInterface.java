/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactics;

import java.awt.Point;

/**
 *
 * @author Sashank
 */
public interface TTModelInterface {
    
    public boolean canMakeMark(Player p, int x, int y);
    public void makeMark(Player p, int x, int y);
    public boolean makeInitialMark(Player p, int x, int y);
    public boolean canConfirm(Player p);
    public void confirm(Player p);
    public boolean canStartGame(Player p);
    public void startGame(Player p);
    public void switchTurn();
    
    public Player getXPlayer();
    public Player getOPlayer();
    public SmallBoard getSmallBoard(int x, int y);
    public Spot getSpot(int x, int y);
    public Point getMoveLocation();
    public Player getTurn();
    public boolean didGameStart();


    
}
