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
public class Player {
    private char c;
    private ArrayList<int[]> initialMarks;
    private boolean confirmed;
    TTModel model;
    static private Player blank = new Player('_');
    static private Player wild = new Player('W');
    
    public Player(char ch)
    {
        c = ch;
        initialMarks = new ArrayList<>();
        /*initialMarks.add(new int[]{0, 0});
        initialMarks.add(new int[]{1, 1});
        initialMarks.add(new int[]{0, 4});
        initialMarks.add(new int[]{2, 5});
        initialMarks.add(new int[]{2, 6});
        initialMarks.add(new int[]{1, 8});
        initialMarks.add(new int[]{3, 8});
        initialMarks.add(new int[]{4, 0});
        initialMarks.add(new int[]{5, 1});*/


    }
    
    public ArrayList<int[]> getInitialMarks()
    {
        return initialMarks;
    }
    
    public int getInitialMarksSize()
    {
        return initialMarks.size();
    }
    public boolean changeMarks(int x, int y)
    {
        for(int i = 0; i < initialMarks.size(); i++)
        {
            if(initialMarks.get(i)[0] == x && initialMarks.get(i)[1] == y)
            {
                initialMarks.remove(i);
                return true;
            }
        }
        if(canMakeMark(x,y))
        {
            initialMarks.add(new int[]{x, y});
            return true;
        }
        return false; 
    }
    
    public boolean canMakeMark(int x, int y)
    {
        if(x > 8 || y > 8)
        {
            return false;
        }
        if(x == 4 && y == 4)
        {
            return false;
        }
        int count = 0;
        for (int[] a : initialMarks) {
            if (a[0] % 3 == x % 3 && a[1] % 3 == y % 3) {
                return false;
            }
            if(a[0]/3 == x/3 && a[1]/3 == y/3)
            {
                count++;
            }
        }
        if(count >= 2)
        {
            return false;
        }
        return true;
    }
    
    public void removeInitialMark(int i)
    {
        initialMarks.remove(i);
    }
    public char getChar()
    {
        return c; 
    }
    
    public void setConfirmed(boolean b)
    {
        confirmed = b;
    }
    
    public boolean getConfirmed()
    {
        return confirmed;
    }
    
    public static Player getBlankPlayer()
    {
        return blank;
    }
    
    public static Player getWildPlayer()
    {
        return wild;
    }

}
