/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BckgrndServiceThreads;

import ConfigData.playListDB;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import javax.swing.SwingUtilities;
import jplayer.FTPDownload;
import jplayer.Jm;
import org.apache.commons.net.ftp.FTPFile;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;

/**
 *
 * @author Xsoad
 */
public class fileListingTH extends Thread {

    //Variable Declarations
    String[] vidPlayList;
    MediaList mList;
    MediaListPlayer mListPlayer;
    EmbeddedMediaPlayer EmRef;
    playListDB serInstance_Index = null;
    String l_downloadPath = "";

    //Method Declarations
    public fileListingTH(String[] vidPlayList, MediaList mList, MediaListPlayer mListPlayer, EmbeddedMediaPlayer EmRef) {

        this.vidPlayList = vidPlayList;
        this.mList = mList;
        this.mListPlayer = mListPlayer;
        this.EmRef = EmRef;

    }

    Object getSerializedObject(String objectName) throws Exception {

        FileInputStream inputFile = new FileInputStream(objectName);
        ObjectInputStream inputObject = new ObjectInputStream(inputFile);
        Object getObjectToReturn = inputObject.readObject();
        inputObject.close();
        return getObjectToReturn;
    }

    void saveSerializedObject(String Objectname, Object serialisableObject) throws Exception {
        FileOutputStream outputFile = new FileOutputStream(Objectname);
        ObjectOutputStream outputObject = new ObjectOutputStream(outputFile);
        outputObject.writeObject(serialisableObject);
        outputObject.close();
    }

    void loadAndPlayMedia() {
        try {
// load from saved DB and add into playlist.
            playListDB Mplaylist = (playListDB) getSerializedObject("DB_playListDB.ser");
            vidPlayList = new String[Mplaylist.playListFiles.size()];
            Mplaylist.playListFiles.copyInto(vidPlayList);

            for (int i = 0; i < vidPlayList.length; i++) {
                mList.addMedia(l_downloadPath + "\\" + vidPlayList[i]);
                System.out.println(l_downloadPath + "\\" + vidPlayList[i] + " has loaded to the list");
            }

        } catch (Exception ex) {

            System.out.println("when loading files to the mlist");
            ex.printStackTrace();
        }

        //Set MediaList to MediaListPlayer***********************************
        mListPlayer.setMediaList(mList);

        // Play Media*********************************************************
        if (!mListPlayer.isPlaying()) {
        mListPlayer.play();    
        }
        

        DefaultFullScreenStrategy fullscreen = new DefaultFullScreenStrategy(Jm.playerContainerFrame);
        fullscreen.enterFullScreenMode();

        //in listner's case try to instantiate from main constructor and set to wait untill calls to notify
        MediaPlayerEventListener mE = new MediaPlayerEventAdapter() {

            @Override
            public void finished(MediaPlayer mediaPlayer) {

                mListPlayer.playNext();

            }

        };

        EmRef.addMediaPlayerEventListener(mE);

        //For now intatiate a new thread and do this but later check is this the best way to do this 
        Thread periodicDownloadCheckTh = new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {

                    load();

                    try {
                        sleep(5000);
                    } catch (InterruptedException ex) {
                        System.out.println("Proccess is interrupted");
                    }

                }

            }
        });

        periodicDownloadCheckTh.start();

    }  // Use only to intiate playback

    void load() {

        FTPFile[] getFileListitem = null;
        FTPDownload fileDownloader = new FTPDownload();
        fileDownloader.authenticateFotListing();
        if(fileDownloader.isAuthenticated){
        
        getFileListitem = fileDownloader.getlFileList();
        fileDownloader.disconnectCon();

        // i initialized to 2 instead of 0 because i'ts file listing format
        for (int i = 2; i < getFileListitem.length; i++) {
            if (serInstance_Index.maxIndex < Integer.valueOf(getFileNameIndex(getFileListitem[i].getName()))) {
                fileDownloader.DownoadFile(getFileListitem[i].getName()); // download the updated file
                serInstance_Index.playListFiles.addElement(getFileListitem[i].getName()); //update list
                serInstance_Index.maxIndex = Integer.valueOf(getFileNameIndex(getFileListitem[i].getName())); //update recent index
                mList.addMedia(l_downloadPath + "\\" + getFileListitem[i].getName());
                System.out.println(l_downloadPath + "\\" + getFileListitem[i].getName() + " has loaded to the list");

            }

        }

        try {
            saveSerializedObject("DB_playListDB.ser", serInstance_Index);
        } catch (Exception e) {
            System.out.println("Attempting to save last updated index value to DB");
            e.printStackTrace();

        }
        }
   }

    void downloadUpdatedMedia() {
        FTPFile[] getFileListitem = null;
        FTPDownload fileDownloader = new FTPDownload();
        fileDownloader.authenticateFotListing();
        if (fileDownloader.isAuthenticated) {

            getFileListitem = fileDownloader.getlFileList();
            fileDownloader.disconnectCon();

            for (int i = 2; i < getFileListitem.length; i++) {
                if (serInstance_Index.maxIndex < Integer.valueOf(getFileNameIndex(getFileListitem[i].getName()))) {
                    fileDownloader.DownoadFile(getFileListitem[i].getName()); // download the updated file
                    serInstance_Index.playListFiles.addElement(getFileListitem[i].getName()); //update list
                    serInstance_Index.maxIndex = Integer.valueOf(getFileNameIndex(getFileListitem[i].getName())); //update recent index

                }

            }

            try {
                saveSerializedObject("DB_playListDB.ser", serInstance_Index);
            } catch (Exception e) {
                System.out.println("Attempting to save last updated index value to DB");
                e.printStackTrace();

            }
        }
    }

    String getFileNameIndex(String name) {

        String formattedName = name.replace(".mp4", "");
        return formattedName;
    }

    playListDB getRecentIndex() throws Exception {
        playListDB Recent_Index = (playListDB) getSerializedObject("DB_playListDB.ser");
        return Recent_Index;
    }
 
    void setRecentIndex(playListDB serInstance_Index) throws Exception {

        saveSerializedObject("DB_playListDB.ser", serInstance_Index);

    }

    @Override
    public void run() {


        Properties server_config = new Properties();
        try {

            server_config.loadFromXML(new FileInputStream("Config\\ServerConfig.xml"));

            l_downloadPath = server_config.getProperty("localDownloadPath");

        } catch (Exception ex) {
            System.out.println("Error loading configuration");
        }

        try {

            serInstance_Index = getRecentIndex();

        } catch (Exception e) {

            
            try {

                serInstance_Index = new playListDB();
                //Saving Initial Settings
                setRecentIndex(serInstance_Index);

                serInstance_Index.maxIndex = 0;

                System.out.println("Created a new play list DB");

            } catch (Exception ex) {
                System.out.println("Attempting to create a new play list DB");
                ex.printStackTrace();
            }

            //..
            //System.out.println("Attempting to read from the player DB");
            //  e.printStackTrace();
        }

        if (0 < serInstance_Index.maxIndex) {

            System.out.println("if block executed");
            loadAndPlayMedia();

        } else {

            System.out.println("else block executed");

            // Get file list from server     
            downloadUpdatedMedia();

//call this throught event dispatcher
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    loadAndPlayMedia();
                }
            });

        }

    }

    @Override
    public synchronized void start() {
        super.start();
    }

}
