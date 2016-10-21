/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactics;

import java.io.PipedInputStream;
import java.util.Scanner;

/**
 *
 * @author HEALASST
 */
public class PISReader extends Thread{
    private Scanner s;
    private PipedControl control;
    public PISReader(Scanner pis, PipedControl pc)
    {
        s = pis;
        control = pc;
    }
   
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        while(s.hasNext())
        {
            control.handleMessage(s.nextLine());
        }
    }
    
}
