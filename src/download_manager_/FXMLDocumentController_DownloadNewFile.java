
package download_manager_;


//import download_manager_.newFileDownload.*;
import download_manager_.*;
import java.io.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;

/**
 *
 * @author Techno Service
 */
public class FXMLDocumentController_DownloadNewFile implements Initializable {
    static ArrayList<Thread> array_of_thread = new ArrayList<>();
    Database_Manager db = new Database_Manager();
    
    int ii=FXMLDocumentController.data.size();
    @FXML
    private Label label;
    @FXML
    private TextField source;
    @FXML
    private TextField dist;
    @FXML
    private Button start;
    @FXML
    private Button cancel;
    @FXML
    private void cut(ActionEvent event) throws IOException{
        System.out.println("the thred number : "+ii);
        File f = new File(dist.getText());
        int answer = 0;
        if(f.exists() && !f.isDirectory()) {

            answer = JOptionPane.showConfirmDialog(null,"There is a file with this name!!!"
                    + "\n do you want to replace is??","Warning",JOptionPane.YES_NO_OPTION);
        }
        if(answer==JOptionPane.YES_OPTION) {
            array_of_thread.add(
                    new Thread(
                            new File_Download_Handler(
                                    new File_Downloader_(source.getText(), dist.getText(), new FileDownloader(), true, ii))));
            array_of_thread.get(ii).start();
            ii++;
            FXMLDocumentController.do_start_refresh = true;
        }else{
            Stage stage = (Stage) start.getScene().getWindow();
            stage.close();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db.connect_to_database();
            ii=FXMLDocumentController.data.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void browes(ActionEvent actionEvent) {
        Stage stage = new Stage();
        DirectoryChooser chooser = new DirectoryChooser();
        File select = chooser.showDialog(stage);
        chooser.setTitle("Open File");
        dist.setText(select.getAbsolutePath());
    }
}
