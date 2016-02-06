/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BckgrndServiceThreads;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import jplayer.Jm;
import uk.co.caprica.vlcj.binding.internal.libvlc_marquee_position_e;
import uk.co.caprica.vlcj.player.Marquee;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author Xsoad
 */
public class SocketNotificationListnr extends Thread {

    String notifyPassToEvtDispatch;
    EmbeddedMediaPlayer EmRef;
    Socket listnerSocket;
    Socket client;
    LinkedList MsgQueue;
    boolean isNotificationReceived = false;

    public SocketNotificationListnr(String notifyPassToEvtDispatch, EmbeddedMediaPlayer EmRef) {
        this.notifyPassToEvtDispatch = notifyPassToEvtDispatch;
        this.EmRef = EmRef;

    }

    void notifyMarquee(String marqueeText, int timeout) {

        System.out.println("6: " + marqueeText);
        Marquee.marquee()
                .colour(Color.white)
                .text(marqueeText)
                .opacity(255)
                .size(20)
                .position(libvlc_marquee_position_e.centre)
                .timeout(timeout)
                .enable(true)
                .apply(Jm.EmRef);

        //   EmRef.setOverlay(new JWindow(ftpob));
    }

    String getReceivedNotification() throws IOException {

        InputStreamReader MSGin = new InputStreamReader(client.getInputStream());
        BufferedReader MSGresBuffReader = new BufferedReader(MSGin);

        String returnStr = MSGresBuffReader.readLine();
        MSGresBuffReader.close();

        return returnStr;  //ch

    }

    public void run() {
        try {
            client = new Socket("localhost", 8081);
           
            
            
            
                                  //  DataOutputStream send = new DataOutputStream(client.getOutputStream());

            // send.writeBytes("Ladies and Gentlement Train has arrived to Station X");
        } catch (IOException ex) {
            System.out.println("Unable to commiunicate with Server side");
        }
        try{
         DataOutputStream authenticateString = new DataOutputStream(client.getOutputStream());
            authenticateString.writeBytes("http://127.0.0.1/update/id=1");
            
             } catch (IOException ex) {
            System.out.println("AU :Unable to commiunicate with Server side");
        }
        
        //  ServerSocket notifiListner = new ServerSocket(4444);
        MsgQueue = new LinkedList();
        //lisner socket was declared here.
        while (true) {

            String Msg = null;
            try {
                Msg = getReceivedNotification();
                System.out.println("1: " + Msg);
            } catch (IOException ex) {
                continue;
                //Logger.getLogger(SocketNotificationListnr.class.getName()).log(Level.SEVERE, null, ex);
            }

//           if (Msg == null) {
//                
//                   continue;
//                
//            }

            //   listnerSocket = notifiListner.accept();
//****
            if (Msg != null) {

                //*****************************************
                if (Jm.EmRef == null) {

                    //temp please delete
                    String tttt = Msg;
                    System.out.println("2: " + tttt);

                    MsgQueue.add(tttt);
                    System.out.println(MsgQueue.getFirst());

                    Thread notificationQueue = new Thread(new Runnable() {

                        @Override
                        public void run() {

                            while (true) {

                                if (Jm.EmRef != null) {
                                    System.out.println("loop 2");
                                    while (!MsgQueue.isEmpty()) {

                                        boolean playerStatus = true;
                                        while (playerStatus) {

                                            if (Jm.EmRef.isPlaying()) {
                                                SwingUtilities.invokeLater(new Runnable() {

                                                    @Override
                                                    public void run() {

                                                        notifyMarquee("Delayed Message: " + MsgQueue.getFirst(), 4000);
                                                    }
                                                });

                                                playerStatus = false;

                                            }

                                        }

                                        try {
                                            sleep(4500);
                                        } catch (InterruptedException ex) {
                                            System.out.println("Process is interrupted");
                                        }
                                        MsgQueue.removeFirst();
                                    }

                                }

                                try {
                                    sleep(2000);
                                } catch (InterruptedException ex) {
                                    System.out.println("Process is interrupted");
                                }

                            }

                        }
                    });

                    //end of q thread
                    if (!notificationQueue.isAlive()) {

                        notificationQueue.start();

                    }

                } else {

                    notifyPassToEvtDispatch = Msg;
                    System.out.println("4: " + notifyPassToEvtDispatch);
//ch

                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {

                            System.out.println("5: " + notifyPassToEvtDispatch);
                            notifyMarquee(notifyPassToEvtDispatch, 5000);

                        }
                    });

//                        try {
//                            client.close();
//
//                            /* try {
//                             Thread.sleep(2000);
//                             } catch (InterruptedException ex) {
//                             Logger.getLogger(Jm.class.getName()).log(Level.SEVERE, null, ex);
//                             } */
//                        } catch (IOException ex) {
//                            Logger.getLogger(SocketNotificationListnr.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                }

            }
        }
    }

    @Override
    public synchronized void start() {

        super.start();
    }

}
