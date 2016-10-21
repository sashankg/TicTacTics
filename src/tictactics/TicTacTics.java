/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactics;

import APCSAnimation.AnimationWindow;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 *
 * @author Sashank
 */
public class TicTacTics {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException, IOException {
        TTModel m1 = new TTModel();
        TTModel m2 = new TTModel();

        
        //TextView t = new TextView(m);
        //t.run();
        
       /* PipedInputStream pis1 = new PipedInputStream();
        PipedOutputStream pos1 = new PipedOutputStream(pis1);
        
        PipedInputStream pis2 = new PipedInputStream();
        PipedOutputStream pos2 = new PipedOutputStream(pis2);
        
        AnimationWindow awX = new AnimationWindow("X Player", 815, 1000, 50);
        TTView vX = new TTView(m1, awX.getWidth(), m1.getXPlayer());
        awX.addAnimatedObject(vX);
        PipedControl pcX = new PipedControl(m1, awX.getWidth(), vX, pos1);
        awX.addAnimatedObject(pcX);
        PISReader prX = new PISReader(pis2, pcX);
        awX.run();

        AnimationWindow awO = new AnimationWindow("O Player", 815, 1000, 50);
        TTView vO = new TTView(m2, awO.getWidth(), m2.getOPlayer());
        awO.addAnimatedObject(vO);
        PipedControl pcO = new PipedControl(m2, awX.getWidth(), vO, pos2);
        awO.addAnimatedObject(pcO);
        PISReader prO = new PISReader(pis1, pcO);
        awO.run();
        
        prX.start();
        prO.start();
       // end of piped controller setup
        
        */
        AnimationWindow awX = new AnimationWindow("X Player", 815, 1000, 50);
        ImprovedView vX = new ImprovedView(m1, awX.getWidth(), m1.getXPlayer());
        awX.addAnimatedObject(vX);
        awX.addAnimatedObject(new TTControl(m1, 800, vX));
        //awX.addAnimatedObject(new RandomControl(m1, vX));
        awX.run();

        AnimationWindow awO = new AnimationWindow("O Player", 815, 1000, 50);
        TTView vO = new ImprovedView(m1, awO.getWidth(), m1.getOPlayer());
        awO.addAnimatedObject(vO);
        awO.addAnimatedObject(new TTControl(m1, 800, vO));
        //awO.addAnimatedObject(new RandomControl(m1, vO));
        awO.run();

    }

}
