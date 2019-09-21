package app.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class newbookingcontroller implements Initializable {
    @FXML
    private ImageView img_rtype;
    @FXML
    private Label lbl_rno;
    @FXML
    private Button btn_book;
    @FXML
    private HBox hlist;
    @FXML
    private AnchorPane listpane;
    @FXML
    private JFXListView<HBox> listview;

    ObservableList<HBox> lv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            hlist = FXMLLoader.load(getClass().getResource("/resources/views/roomstat.fxml"));
            lv = FXCollections.observableArrayList(hlist);
        } catch (IOException e) {
            e.printStackTrace();
        }
        listview.setItems(lv);
    }
}
