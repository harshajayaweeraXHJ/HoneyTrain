/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jplayer;

import ConfigData.FTPConfigData;
import BckgrndServiceThreads.SocketNotificationListnr;
import BckgrndServiceThreads.fileListingTH;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class Jm extends javax.swing.JFrame {

    public Jm(String nativeSearchPathStr) {

        //File transfer is done. set notification when ftp server Updated with a file and run thread to transfer file.
        initComponents();

        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), nativeSearchPathStr);
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

        this.setLocationRelativeTo(null);

        
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {

                   // Automatically load player
        
        
        //try how to design this conditional statement in a such a way that can overrite the methode body
        if (playerContainerFrame != null) {

            if (mListPlayer.isPlaying()) {
                mListPlayer.pause();
            } else {
                mListPlayer.play();
                
                
           
            }
        } else {

            mediaComponentFactory = new MediaPlayerFactory();

            createContainerFreme(800, 600);
            addPlyrCompToFreme();
            getMediaPlayerObjectReference();
            associatePlayerWithListPlayer();
            createMediaList();

            
            
        
            
            
            //socketnotificationlisner thread body removed from here and called thred from thread package
//            SocketNotificationListnr socketNotificationListnrRef = new SocketNotificationListnr(notifyPassToEvtDispatch, EmRef);
            //          socketNotificationListnrRef.start();  // commented only for debuging.. please uncomment. 
        }

        //fileListingTH thread body removed from here and called thred from thread package
        fileListingTH fileListingThRef = new fileListingTH(vidPlayList, mList, mListPlayer, EmRef);
        fileListingThRef.start();
        
        SocketNotificationListnr socketNotificationListnrRef = new SocketNotificationListnr(notifyPassToEvtDispatch, EmRef);
        socketNotificationListnrRef.start();

      //...  
                
                
            }

        
        
        });
        
        
        
        
        
        
     
        
    }
    
    
    int ftpPort;
   public  static JFrame playerContainerFrame;
    EmbeddedMediaListPlayerComponent embededMPlayerComp;
   public static EmbeddedMediaPlayer EmRef;
    MediaPlayerFactory mediaComponentFactory;
    int iC = 0;

    //******** References for FTP Option frame**
    FTPoptions ftpob;

    String refTo_Au_Password = "";
    String refTo_Au_Username = "";
    String refTo_FTPport = "";
    String refTo_serverIPP = "";
    String refTo_localDownloadPath = "";
    MediaListPlayer mListPlayer;
    String vidPlayList[];
    String notifyPassToEvtDispatch;
    MediaList mList;
    //******** References for FTP Option frame** //

    //moved notifyMarquee() method from here to thread class
    //moved getFileNameIndex() method from here to thread class
    //moved loadAndPlayMedia() method from here to thread class
    void createContainerFreme(int width, int height) {

        playerContainerFrame = new JFrame("playvideo");
        playerContainerFrame.setSize(width, height);
        playerContainerFrame.setLocationRelativeTo(null);
        playerContainerFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        playerContainerFrame.setUndecorated(true);

    }

    void addPlyrCompToFreme() {
        embededMPlayerComp = new EmbeddedMediaListPlayerComponent();
        playerContainerFrame.setContentPane(embededMPlayerComp);
        playerContainerFrame.setVisible(true);

    }

    void getMediaPlayerObjectReference() {

        EmRef = embededMPlayerComp.getMediaPlayer();

    }

    void associatePlayerWithListPlayer() {

        
       
        mListPlayer = mediaComponentFactory.newMediaListPlayer();
        mListPlayer.setMediaPlayer(EmRef);
    }

    void createMediaList() {
        //Create a Medialist Object: Important - this should be the interface to Algorithm*
        mList = mediaComponentFactory.newMediaList();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 204));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setForeground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(215, 85));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/play.png"))); // NOI18N
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/marquee.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/open1.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/download.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/ftp.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed



    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

//        FTPDownload fileDownloader = new FTPDownload();
//        fileDownloader.DownoadFile();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        FTPConfigData FTPConfigDatainstant = null;

        try {
            FileInputStream readFTPConfig = new FileInputStream("ftoCon.ser");
            ObjectInputStream obFTPConfigRead = new ObjectInputStream(readFTPConfig);
            FTPConfigDatainstant = (FTPConfigData) obFTPConfigRead.readObject();
        } catch (Exception e) {

            refTo_Au_Username = "";
            refTo_Au_Password = "";
            refTo_FTPport = "";
            refTo_serverIPP = "";
            refTo_localDownloadPath = "";
        }

        if (FTPConfigDatainstant != null) {
            refTo_Au_Username = FTPConfigDatainstant.Au_Username;
            refTo_Au_Password = FTPConfigDatainstant.Au_Password;
            refTo_FTPport = FTPConfigDatainstant.FTPport;
            refTo_serverIPP = FTPConfigDatainstant.serverIPP;
            refTo_localDownloadPath = FTPConfigDatainstant.localDownloadPath;
        }
        ftpob = new FTPoptions(refTo_Au_Password, refTo_Au_Username, refTo_FTPport, refTo_serverIPP, refTo_localDownloadPath);

        ftpob.setVisible(true);


    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Jm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Jm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Jm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Jm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
