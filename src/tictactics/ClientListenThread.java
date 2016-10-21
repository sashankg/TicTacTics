/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactics;

import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *
 * @author HEALASST
 */
public class ClientListenThread extends Thread{
        Scanner scan;
        PrintStream pos;

    ClientListenThread( Scanner s, PrintStream out )
    {
        scan = s;
        pos = new PrintStream(out);
    }
    
    public void run()
    {
        while ( true )
        {
            pos.println(scan.nextLine());
        }
    }
}
