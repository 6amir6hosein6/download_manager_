/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package download_manager_;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class File_Downloader_ {
    String url;
    int id;
    String dist;
    int answer;
    boolean not_continu;
    //int number;
    FileDownloader fd;
    FXMLDocumentController fdc = new FXMLDocumentController();
    Database_Manager dbm = new Database_Manager();
    public File_Downloader_(String url, String dist, FileDownloader fd , boolean continu , int id){
        this.url = url;
        this.dist= dist;
        this.fd = fd;
        this.not_continu=continu;
        this.id=id;

        
        
    }
    
    public void do_Copy() throws FileNotFoundException, IOException, SQLException{

        dist=dist+"\\"+FilenameUtils.getName(url);
        dist=dist.replaceAll("\\\\","\\\\\\\\");
        System.out.println(dist);
        dbm.connect_to_database();
        InputStream is = null;
        OutputStream os = null;
        FXMLDocumentController.do_start_refresh = true;
        long expectedBytes = fd.getFileLength(url);// This is the number of bytes we expected to copy..
        long totalBytesCopied = 0; // This will track the total number of bytes we've copied
        System.out.println("url : "+url);
        System.out.println("dist : "+dist);

        try {
        is = fd.download(url);
        os = new FileOutputStream(dist);
            System.out.println("url : "+url);
            System.out.println("dist : "+dist);
        if(not_continu==true) {
            dbm.insert(FilenameUtils.getName(url), (fd.getFileLength(url) + " MB"), (totalBytesCopied + "%"), url, dist);
            FXMLDocumentController.data.add(new Files(FilenameUtils.getName(url),(fd.getFileLength(url)+" MB"),(totalBytesCopied+"%"),url,dist,id));
        }

            System.out.println("your id is : "+(id));
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
            totalBytesCopied += length;
            int progress = (int)(((double)totalBytesCopied / (double)expectedBytes)* 100);
            FXMLDocumentController.data.get(id).setProgress(progress+"%");
        }
            JOptionPane.showMessageDialog(null, "Download finished", "info",JOptionPane.INFORMATION_MESSAGE);
            dbm.update("100%",FilenameUtils.getName(url));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error in Downloading it may bee for one of this reson : " +
                    "\n 1-Check your Connection" +
                    "\n 2-Check your URL" +
                    "\n 3-Check your distination","Error",JOptionPane.ERROR_MESSAGE);
        }finally {
            //System.out.println(expectedBytes);
            is.close();
            os.close();


        }

        
    }
}

