package download_manager_;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sajjad on 4/29/2018.
 */
public class FileDownloader {
    /**
     *
     * @param url url of file
     * @return a InputStream in order to download file.
     * @throws IOException if an I/O exception occurs.
     */
    public InputStream download(String url) throws IOException {
        URL fileUrl=new URL(url);
        HttpURLConnection httpURLConnection=(HttpURLConnection)fileUrl.openConnection();
        httpURLConnection.setRequestMethod("GET");
        return httpURLConnection.getInputStream();

    }

    /**
     *
     * @param url url of file
     * @return length of file as int
     * @throws IOException if an I/O exception occurs.
     */
    public int getFileLength(String url) throws IOException {
        URL fileUrl=new URL(url);
        HttpURLConnection httpURLConnection=(HttpURLConnection)fileUrl.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int len=httpURLConnection.getHeaderFieldInt("content-length",0);
        httpURLConnection.disconnect();
        return len;
    }


}
