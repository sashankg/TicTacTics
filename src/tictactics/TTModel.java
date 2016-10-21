/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactics;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Sashank
 */
public class TTModel implements TTModelInterface, Cloneable {

    private Player oPlayer;
    private Player xPlayer;
    private Player turn;
    private Point moveLocation;
    private MegaBoard mega;
    Point[][] posSol;
    private boolean gameOver;
    private Player winner = Player.getBlankPlayer();

    public TTModel() throws FileNotFoundException {
        mega = new MegaBoard();
        xPlayer = new Player('X');
        oPlayer = new Player('O');
        turn = xPlayer;
        moveLocation = new Point(-1, -1);
        Scanner solFile = new Scanner(new File("SolutionCombinations.txt"));
        posSol = new Point[8][3];
        for (int i = 0; i < posSol.length; i++) {
            for (int j = 0; j < posSol[i].length; j++) {
                posSol[i][j] = new Point((int) solFile.nextInt(), (int) solFile.nextInt());
            }
        }
    }

    @Override
    public boolean canMakeMark(Player p, int x, int y) {
        return (moveLocation.equals(new Point(-1, -1)) 
                || (moveLocation.x == x / 3 && moveLocation.y == y / 3))
                && !mega.getInitialPhase()
                && !mega.getSpot(x / 3, y / 3).getSpot(x % 3, y % 3).isDisabled()
                && mega.getSpot(x / 3, y / 3).getSpot(x % 3, y % 3).getOwner() == Player.getBlankPlayer()
                && p == turn
                && winner == Player.getBlankPlayer();
    }

    @Override
    public void makeMark(Player p, int x, int y) {
        SmallBoard s = (SmallBoard) mega.getSpot(x / 3, y / 3);
        s.getSpot(x % 3, y % 3).setOwner(p);
        mega.reenableSpot();
        mega.disableSpot(x / 3, y / 3, x % 3, y % 3);
        if (mega.getSpot(x % 3, y % 3).canMakeAnyMove()) {
            moveLocation.setLocation(x % 3, y % 3);
        } else {
            moveLocation.setLocation(-1, -1);
        }
        if (didWin(turn, mega.getSpot(x / 3, y / 3)) && mega.getSpot(x/3, y/3).getOwner() == Player.getBlankPlayer()) {
            mega.getSpot(x / 3, y / 3).setOwner(turn);
        }
        if (!mega.getSpot(x / 3, y / 3).canMakeAnyMove() && mega.getSpot(x/3, y/3).getOwner()==Player.getBlankPlayer()) {
            mega.getSpot(x / 3, y / 3).setOwner(Player.getWildPlayer());
        }
        if(didWin(turn, mega))
        {
            winner = turn;
        }
        else if(!mega.canMakeAnyMove())
        {
            winner = Player.getWildPlayer();
        }
        switchTurn();
    }

    @Override
    public boolean makeInitialMark(Player p, int x, int y) {
        return p.changeMarks(x, y);
    }

    @Override
    public boolean canConfirm(Player p) {
        return p.getInitialMarksSize() == 9;
    }

    @Override
    public void confirm(Player p) {
        if (canConfirm(p)) {
            p.setConfirmed(true);
            Player opp;
            if(p == oPlayer) opp = xPlayer;
            else opp = oPlayer;
            if (opp.getConfirmed()) {
                for (int i = 8; i >= 0; i--) {
                    for (int j = oPlayer.getInitialMarksSize() - 1; j >= 0; j--) {
                        if (xPlayer.getInitialMarks().get(i)[0] == oPlayer.getInitialMarks().get(j)[0]
                                && xPlayer.getInitialMarks().get(i)[1] == oPlayer.getInitialMarks().get(j)[1]) {
                            xPlayer.removeInitialMark(i);
                            oPlayer.removeInitialMark(j);
                            break;
                        }
                    }
                }
                for (int i = 0; i < xPlayer.getInitialMarksSize(); i++) {
                    int[] c = xPlayer.getInitialMarks().get(i);
                    SmallBoard s1 = (SmallBoard) mega.getSpot(c[0] / 3, c[1] / 3);
                    s1.getSpot(c[0] % 3, c[1] % 3).setOwner(xPlayer);

                    int[] d = oPlayer.getInitialMarks().get(i);
                    SmallBoard s2 = (SmallBoard) mega.getSpot(d[0] / 3, d[1] / 3);
                    s2.getSpot(d[0] % 3, d[1] % 3).setOwner(oPlayer);
                }
                startGame(p);
            }
        }
    }

    @Override
    public boolean canStartGame(Player p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startGame(Player p) {
        mega.setInitialPhase(false);
    }

    @Override
    public void switchTurn() {
        if (turn.equals(xPlayer)) {
            turn = oPlayer;
        } else {
            turn = xPlayer;
        }

    }

    public Player getWinner()
    {
        return winner;
    }
    @Override
    public Player getXPlayer() {
        return xPlayer;
    }

    @Override
    public Player getOPlayer() {
        return oPlayer;
    }

    @Override
    public SmallBoard getSmallBoard(int x, int y) {
        return (SmallBoard) mega.getSpot(x, y);
    }

    @Override
    public Point getMoveLocation() {
        return moveLocation;
    }

    @Override
    public Player getTurn() {
        return turn;
    }

    @Override
    public Spot getSpot(int x, int y) {
        SmallBoard b = (SmallBoard) mega.getSpot(x / 3, y / 3);
        return (Spot) b.getSpot(x % 3, y % 3);
    }

    @Override
    public boolean didGameStart() {
        return !mega.getInitialPhase();
    }

    public boolean didWin(Player p, Board b) {
        for (Point[] a : posSol) {
            boolean allEqual = true;
            for (int i = 0; i < 3; i++) {
                Player temp = b.getSpot((int) a[i].getX(), (int) a[i].getY()).getOwner();
                if (!temp.equals(p) && !temp.equals(Player.getWildPlayer())) {
                    allEqual = false;
                }
            }
            if (allEqual) {
                return true;
            }
        }
        return false;
    }

    public MegaBoard getMegaBoard() {
        return mega;
    }
    public void setOPlayer(Player p)
    {
        oPlayer = p;
    }
    
    public void setXPlayer(Player p)
    {
        xPlayer = p;
    }
}