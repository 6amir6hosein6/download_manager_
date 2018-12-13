package download_manager_;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Techno Service
 */
public class File_Download_Handler implements Runnable{
    File_Downloader_ fd;
    public File_Download_Handler(File_Downloader_ fd) {
        this.fd = fd;
    }
    
    @Override
    public void run() {
        try {
            try {
                fd.do_Copy();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(File_Download_Handler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(File_Download_Handler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(File_Download_Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
