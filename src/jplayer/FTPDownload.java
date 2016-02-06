/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jplayer;

import ConfigData.FTPConfigData;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import ConfigData.FTPConfigData;
import java.util.Properties;

/**
 *
 * @author XsoaD
 */
public class FTPDownload {

    FTPClient fCli = new FTPClient();
    public boolean isAuthenticated = false;

    public void DownoadFile(String fileName) {

        String serverIP = "";
        int FTPport = 0;
        String Au_Username = "";
        String Au_Password = "";
        String localDwnloadPath = "";

        

        Properties server_config = new Properties();
        try {

            server_config.loadFromXML(new FileInputStream("Config\\ServerConfig.xml"));
            serverIP = server_config.getProperty("serverIPP");
            FTPport = Integer.valueOf(server_config.getProperty("FTPport"));
            Au_Username = server_config.getProperty("Au_Username");
            Au_Password = server_config.getProperty("Au_Password");
            localDwnloadPath = server_config.getProperty("localDownloadPath");

        } catch (Exception ex) {
            System.out.println("Error loading configuration");
        }

        System.out.println(serverIP + " " + FTPport + " " + Au_Username + " " + Au_Password + " " + localDwnloadPath);

        try {
            fCli.connect(serverIP, FTPport);
            fCli.login(Au_Username, Au_Password);
            System.out.println("ftp Request sent, Waiting for a Reply");
            System.out.println(fCli.getReplyString());
        } catch (IOException ex) {
            System.out.println("Error authenticating for downloading");
        }

        try {
            OutputStream FtpDownload = new FileOutputStream(localDwnloadPath + "\\" + fileName);
            fCli.retrieveFile("vidsVirtual/1/" + fileName, FtpDownload);
            System.out.println(fCli.getReplyString());

            System.out.println("Transfer Done");
        } catch (IOException ex) {
            System.out.println("Error downloading file");
        }

  
    }

    public void authenticateFotListing() {

        String serverIP = "";
        int FTPport = 0;
        String Au_Username = "";
        String Au_Password = "";
        String localDwnloadPath = "";

      

        Properties server_config = new Properties();
        try {

            server_config.loadFromXML(new FileInputStream("Config\\ServerConfig.xml"));
            serverIP = server_config.getProperty("serverIPP");
            FTPport = Integer.valueOf(server_config.getProperty("FTPport"));
            Au_Username = server_config.getProperty("Au_Username");
            Au_Password = server_config.getProperty("Au_Password");
            localDwnloadPath = server_config.getProperty("localDownloadPath");

        } catch (Exception ex) {
            System.out.println("Error loading configuration");
        }

        System.out.println(serverIP + " " + FTPport + " " + Au_Username + " " + Au_Password + " " + localDwnloadPath);

        try {
            fCli.connect(serverIP, FTPport);
            fCli.login(Au_Username, Au_Password);

            System.out.println("ftp Request sent, Waiting for a Reply");
            System.out.println(fCli.getReplyString());

            isAuthenticated = true;

        } catch (IOException ex) {
            System.out.println("Error loging to the server");
        }

    }

    public void disconnectCon() {

        try {
            fCli.disconnect();

            System.out.println("Connection disconnected successfully");

            isAuthenticated = false;
        } catch (IOException ex) {
            System.out.println("Unable to terminate connection succesfuly");
        }
    }

    public FTPFile[] getlFileList() {
        FTPFile[] getFileList = null;
        try {
            getFileList = fCli.listFiles("vidsVirtual/1/");

        } catch (IOException ex) {

            System.out.println("Error listing files from server.");

        }

        return getFileList;
    }

}
