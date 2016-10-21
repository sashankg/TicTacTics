/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactics;

import APCSAnimation.AnimationWindow;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author HEALASST
 */
public class Server 
{
    public static void main( String [] args ) throws IOException
    {
        ServerSocket ss = new ServerSocket( 5327 );
        System.out.println("Server IP Address: ");
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        Socket sock1 = ss.accept();
        System.out.println("X Player connected");
        Socket sock2 = ss.accept();
        System.out.println("O Player connected");

        AnimationWindow awO = new AnimationWindow("O Player", 815, 1000, 50);
        awO.run();

        
        PrintStream print1 = new PrintStream( sock1.getOutputStream() );
        print1.println( "X" );
        System.out.println("X Player joined");
        PrintStream print2 = new PrintStream( sock2.getOutputStream() );
        print2.println( "O" );
        System.out.println("O Player Joined");
        System.out.println("Game Start");
        new ServerListenThread( sock1, sock2 ).start();
        new ServerListenThread( sock2, sock1 ).start();
    }
}
