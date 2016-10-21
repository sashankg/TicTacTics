/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactics;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *
 * @author HEALASST
 */
public class PipedControl extends TTControl{

    private PrintStream out;
    private Player opposite;
    
    public PipedControl(TTModel m, int s, TTView v, PrintStream pos) {
        super(m, s, v);
        out = pos;
        if(v.getPlayer().getChar() == 'X')
        {
            opposite = m.getOPlayer();
        }
        else
        {
            opposite = m.getXPlayer();
        }
        
    }
    
    public void handleMessage(String s)
    {
        if(s.startsWith("Initial"))
        {
            s = s.substring(8);
            for(int i = 0; i<9; i++)
            {
                int x = Character.getNumericValue(s.charAt(i*2));
                int y = Character.getNumericValue(s.charAt((i*2)+1));
                opposite.changeMarks(x, y);
            }
            model.confirm(opposite);
        }
        else if(model.didGameStart())
        {
            int spaceIndex = s.indexOf(" ");
            int x = Integer.parseInt(s.substring(0, spaceIndex));
            int y = Integer.parseInt(s.substring(spaceIndex+1));
            int b = 10;
            int squareSize = (size - (9 * b)) / 9;
            int ix = (x - b) / (squareSize + b);
            int iy = (y - b) / (squareSize + b);
            if (model.canMakeMark(opposite, ix, iy)) {
                model.makeMark(opposite, ix, iy);
            }
        }
    }
    @Override
    public void handleMouseClick(int x, int y) {
        if(this.confirmButton)
        {
            String init = "";
            for(int[] a : this.view.getPlayer().getInitialMarks())
            {
                init += a[0] + "" + a[1] + ""; 
            }
            out.println("Initial " + init);
        }
        else
        {
            out.println(x + " " + y);
        }
        super.handleMouseClick(x, y); //To change body of generated methods, choose Tools | Templates.
    }
    
}
