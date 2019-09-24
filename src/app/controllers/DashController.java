package app.controllers;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashController implements Initializable {
    @FXML
    private Button btn_dashboard;
    @FXML
    private Button btn_newbooking;
    @FXML
    private Button btn_viewbooking;
    @FXML
    private Button btn_roomstatus;
    @FXML
    private Button btn_roommanager;
    @FXML
    private Button btn_earnings;
    @FXML
    private Button btn_logout;
    @FXML
    private AnchorPane holderpane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Inside dashboard");
            Parent root =  FXMLLoader.load(getClass().getResource("/resources/views/dashstats.fxml"));
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            holderpane.getChildren().clear();
            holderpane.getChildren().setAll(root);
        }catch (IOException e){
            e.printStackTrace();
        }
        btn_dashboard.setOnMouseClicked(handler);
        btn_newbooking.setOnMouseClicked(handler);
        btn_viewbooking.setOnMouseClicked(handler);
        btn_earnings.setOnMouseClicked(handler);
        btn_logout.setOnMouseClicked(handler);
        btn_roommanager.setOnMouseClicked(handler);
        btn_roomstatus.setOnMouseClicked(handler);
    }


    EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getSource() == btn_dashboard) {
                try {
                    Parent root =  FXMLLoader.load(getClass().getResource("/resources/views/dashstats.fxml"));
                    AnchorPane.setTopAnchor(root, 0.0);
                    AnchorPane.setRightAnchor(root, 0.0);
                    AnchorPane.setLeftAnchor(root, 0.0);
                    AnchorPane.setBottomAnchor(root, 0.0);
                    holderpane.getChildren().clear();
                    holderpane.getChildren().setAll(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (event.getSource() == btn_newbooking) {
                try{
                    Parent root =  FXMLLoader.load(getClass().getResource("/resources/views/newbooking.fxml"));
                    AnchorPane.setTopAnchor(root, 0.0);
                    AnchorPane.setRightAnchor(root, 0.0);
                    AnchorPane.setLeftAnchor(root, 0.0);
                    AnchorPane.setBottomAnchor(root, 0.0);
                    holderpane.getChildren().clear();
                    holderpane.getChildren().setAll(root);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (event.getSource() == btn_viewbooking) {
                try {
                    Parent root =  FXMLLoader.load(getClass().getResource("/resources/views/viewbooking.fxml"));
                    AnchorPane.setTopAnchor(root, 0.0);
                    AnchorPane.setRightAnchor(root, 0.0);
                    AnchorPane.setLeftAnchor(root, 0.0);
                    AnchorPane.setBottomAnchor(root, 0.0);
                    holderpane.getChildren().clear();
                    holderpane.getChildren().setAll(root);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (event.getSource() == btn_roomstatus) {
                try {
                    Parent root =  FXMLLoader.load(getClass().getResource("/resources/views/roomstat.fxml"));
                    AnchorPane.setTopAnchor(root, 0.0);
                    AnchorPane.setRightAnchor(root, 0.0);
                    AnchorPane.setLeftAnchor(root, 0.0);
                    AnchorPane.setBottomAnchor(root, 0.0);
                    holderpane.getChildren().clear();
                    holderpane.getChildren().setAll(root);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (event.getSource() == btn_roommanager) {
                try {
                    Parent root =  FXMLLoader.load(getClass().getResource("/resources/views/roommanager.fxml"));
                    AnchorPane.setTopAnchor(root, 0.0);
                    AnchorPane.setRightAnchor(root, 0.0);
                    AnchorPane.setLeftAnchor(root, 0.0);
                    AnchorPane.setBottomAnchor(root, 0.0);
                    holderpane.getChildren().clear();
                    holderpane.getChildren().setAll(root);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (event.getSource() == btn_earnings) {
                try {
                    Parent root =  FXMLLoader.load(getClass().getResource("/resources/views/earnings.fxml"));
                    AnchorPane.setTopAnchor(root, 0.0);
                    AnchorPane.setRightAnchor(root, 0.0);
                    AnchorPane.setLeftAnchor(root, 0.0);
                    AnchorPane.setBottomAnchor(root, 0.0);
                    holderpane.getChildren().clear();
                    holderpane.getChildren().setAll(root);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (event.getSource() == btn_logout) {
                btn_logout.getScene().getWindow().hide();
                Stage login =  new Stage();
                Parent root1 = null;
                try {
                    root1 = FXMLLoader.load(getClass().getResource("/resources/views/login.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root1);
                login.setScene(scene);
                login.show();
                login.setResizable(false);
            }
        }
    };
}
