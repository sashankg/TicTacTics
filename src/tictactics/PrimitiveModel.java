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
public class PrimitiveModel implements Cloneable {

    private char turn;
    private int[] moveLocation;
    private char[][] board;
    private char[][] megaBoard;
    private boolean gameOver;
    private char winner;
    private Point[][] posSol;
    protected int[] disabled;

    public PrimitiveModel(PrimitiveModel m){
        turn = 'X';
        moveLocation = new int[]{m.moveLocation[0], m.moveLocation[1]};
        disabled = new int[]{m.disabled[0], m.disabled[1]};
        winner = m.winner;
        
        posSol = m.posSol;
        megaBoard = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                megaBoard[i][j] = m.megaBoard[i][j];
            }
        }

        board = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = m.board[i][j];
            }
        }
    }
    
    public PrimitiveModel(TTModel m)
    {
        turn = m.getTurn().getChar();
        moveLocation = new int[]{m.getMoveLocation().x, m.getMoveLocation().y};
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(m.getSpot(i, j).isDisabled())
                {
                    disabled = new int[]{i, j};
                }
            }
        }
        winner = m.getWinner().getChar();
        posSol = m.posSol;
        megaBoard = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                megaBoard[i][j] = m.getSmallBoard(i, j).getOwner().getChar();
            }
        }
        
        board = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = m.getSpot(i, j).getOwner().getChar();
            }
        }
    }

    public boolean canMakeMark(char c, int x, int y) {
        return ((moveLocation[0] == x / 3 && moveLocation[1] == y / 3) || (moveLocation[0] == -1 && moveLocation[1] == -1))
                && !(x == disabled[0] && y == disabled[1])
                && board[x][y] == '_'
                && turn == c
                && winner == '_';
    }

    public void makeMark(char c, int x, int y) {
        if (canMakeMark(c, x, y)) {
            board[x][y] = c;
            disabled[0] = -1;
            disabled[1] = -1;
            if (numAvailableMovesInSmallBoard(x % 3, y % 3) > 1) {
                disabled[0] = 3 * (x % 3) + (x / 3);
                disabled[1] = 3 * (y % 3) + (y / 3);
            }
            moveLocation[0] = -1;
            moveLocation[1] = -1;
            if (numAvailableMovesInSmallBoard(x % 3, y % 3) > 0) {
                moveLocation[0] = x % 3;
                moveLocation[1] = y % 3;
            }
            if(didWinSmall(c, x/3, y/3) && megaBoard[x/3][y/3] == '_')
            {
                megaBoard[x/3][y/3] = c;
            }
            if(numAvailableMovesInSmallBoard(x/3, y/3) == 0 && megaBoard[x/3][y/3] == '_')
            {
                megaBoard[x/3][y/3] = 'W';
            }
            if(didWinBig(c))
            {
                winner = c;
            }
            if(turn == 'X') turn = 'O';
            else turn = 'X';
        }
    }

    private int numAvailableMovesInSmallBoard(int x, int y) {
        int emptySpots = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[3 * x + i][3 * y + j] == '_') {
                    emptySpots++;
                }
            }
        }
        return emptySpots;
    }

    private boolean didWinSmall(char c, int x, int y) {
        for (Point[] a : posSol) {
            boolean allEqual = true;
            for (int i = 0; i < 3; i++) {
                char temp = board[3*x+((int) a[i].getX())][3*x+((int) a[i].getY())];
                if (temp != c) {
                    allEqual = false;
                }
            }
            if (allEqual) {
                return true;
            }
        }
        return false;
    }
    private boolean didWinBig(char c) {
        for (Point[] a : posSol) {
            boolean allEqual = true;
            for (int i = 0; i < 3; i++) {
                char temp = megaBoard[((int) a[i].getX())][((int) a[i].getY())];
                if (temp != c && temp != 'W') {
                    allEqual = false;
                }
            }
            if (allEqual) {
                return true;
            }
        }
        return false;
    }
    public char getWinner()
    {
        return winner;
    }
    public char getTurn()
    {
        return turn;
    }
    public boolean equals(PrimitiveModel m)
    {
        boolean boardSame = true;
        for(int i = 3; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(m.megaBoard[i][j] != megaBoard[i][j])
                {
                    boardSame = false;
                }
            }
        }
        return m.disabled[0] == disabled[0] && m.disabled[1] == disabled[1]
                && boardSame
                && m.turn == turn
                && m.moveLocation[0] == m.moveLocation[0] && m.moveLocation[1] == moveLocation[1];
    }
}
