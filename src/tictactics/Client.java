/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactics;

import APCSAnimation.AnimationWindow;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author HEALASST
 */
public class Client 
{
    public static void main( String [] args ) throws IOException
    {
        System.out.println("Enter IP Address");
        Scanner keyboard = new Scanner(System.in);
        Socket s = new Socket( keyboard.nextLine(), 5327 );
        Scanner scan = new Scanner( s.getInputStream() );
        PrintStream print = new PrintStream( s.getOutputStream() );
        
        char c = scan.nextLine().charAt(0);
        System.out.println("You are the " + c + " Player");
        TTModel m1 = new TTModel();
                
        AnimationWindow aw = new AnimationWindow(c + " Player", 815, 1000, 50);
        TTView v;
        if(c == 'X')
        {
            v = new ImprovedView(m1, aw.getWidth(), m1.getXPlayer());
        }
        else
        {
            v = new ImprovedView(m1, aw.getWidth(), m1.getOPlayer());
        }
        aw.addAnimatedObject(v);
        PipedControl pcX = new PipedControl(m1, aw.getWidth(), v, print);
        aw.addAnimatedObject(pcX);
        PISReader pr = new PISReader(scan, pcX);
        aw.run();
        
        pr.start();
    }
}
