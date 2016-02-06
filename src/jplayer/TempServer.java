/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jplayer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class TempServer {

    boolean b= true;
   
    
    public static void main(String[] args) {

        try {
            ServerSocket notifiListner = new ServerSocket(8080);

            while (true) {

                Socket Svrport = notifiListner.accept();

               
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TempServer.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                
                DataOutputStream out = new DataOutputStream(Svrport.getOutputStream());
                out.writeBytes("hello from server");
                out.close();

            }

        } catch (IOException ex) {
            Logger.getLogger(TempServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    }
    
}