/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactics;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HEALASST
 */
public class ServerListenThread extends Thread{
        Socket sock1, sock2;
    
    ServerListenThread( Socket s1, Socket s2 )
    {
        sock1 = s1;
        sock2 = s2;
    }
    
    public void run()
    {
        try {
            Scanner scan = new Scanner( sock1.getInputStream() );
            PrintStream print = new PrintStream( sock2.getOutputStream() );
            while ( true )
            {
                print.println( scan.nextLine() );
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerListenThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
