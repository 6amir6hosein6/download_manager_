
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package download_manager_;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.JOptionPane;

/**
 *
 * @author AmirHosein
 */
public class FXMLDocumentController extends Thread {
    Database_Manager db = new Database_Manager();

    int myIndex;
    static boolean do_start_refresh=false;
    
    @FXML
    private TableView<Files> tb;
    @FXML
    private Button new_btn;

    static ObservableList<Files> data;
    private ArrayList<Files> list_of_file;

    @FXML
    private void new_bt(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLDocument_DownloadNewFile.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    private void play_bt(ActionEvent event) {

        int myIndex = tb.getSelectionModel().getSelectedIndex();

        if (!FXMLDocumentController_DownloadNewFile.array_of_thread.get(myIndex).isAlive())
            FXMLDocumentController_DownloadNewFile.array_of_thread.get(myIndex).start();
        else
            FXMLDocumentController_DownloadNewFile.array_of_thread.get(myIndex).resume();
    }

    @FXML
    private void stop_bt(ActionEvent event) throws InterruptedException
    {
        int myIndex = tb.getSelectionModel().getSelectedIndex();
        FXMLDocumentController_DownloadNewFile.array_of_thread.get(myIndex).suspend();

    }

    @FXML
    private void delete_bt(ActionEvent event) throws SQLException {
        myIndex = tb.getSelectionModel().getSelectedIndex();
        data.remove(myIndex);
        db.delete(data.get(myIndex).getId());


    }

    @FXML
    public void initialize() throws SQLException {

        //------updating table------------
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (do_start_refresh==true){
                    tb.refresh();
                }

            }
        }));
        //--------------------------------

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        tb.setEditable(true);
        data = tb.getItems();
        tb.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        tb.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("size"));
        tb.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("progress"));
        tb.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("url"));
        inserting_db_to_table();
        refreshing_table();
    }
    public void inserting_db_to_table(){
        int a=0;
        try {
            db.connect_to_database();
            list_of_file=db.filess();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error in connecting to data base!!");
        }
        for(Files f:list_of_file){
            data.add(new Files(
                    f.getName(),f.getSize(),f.getProgress(),f.getUrl(),f.getDist(),f.getId()
            ));
            FXMLDocumentController_DownloadNewFile.array_of_thread.add(new Thread(
                    new File_Download_Handler(
                            new File_Downloader_(f.getUrl(),f.getDist(),new FileDownloader(),false , a ))));
            System.out.println("item "+a+" id :"+f.getId());
            a++;
        }
        System.out.println("number of thread : "+(a-1));
        System.out.println("if you want to add new thread it gonna be : "+data.size());
    }
    public void refreshing_table() {
        Thread refresh = new Thread(new Runnable() {
            @Override
            public void run() {
                while (do_start_refresh){
                    tb.refresh();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("working");
                }
            }
        });
        refresh.start();
    }
    public void about(ActionEvent actionEvent) {
       File file = new File("src\\download_manager_\\about us.txt");
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close(ActionEvent actionEvent) {
        /*Stage stage = (Stage) close1.getScene().getWindow();
        stage.close();*/
    }
    public void open_file(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                String path = tb.getSelectionModel().getSelectedItem().getDist();
                System.out.println("your patht : "+path);
                File file = new File(path);
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
