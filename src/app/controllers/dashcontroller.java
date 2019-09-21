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

public class dashcontroller implements Initializable {
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
            Node n = (Node) FXMLLoader.load(getClass().getResource("/resources/views/dashstats.fxml"));
            AnchorPane.setTopAnchor(n, 0.0);
            AnchorPane.setRightAnchor(n, 0.0);
            AnchorPane.setLeftAnchor(n, 0.0);
            AnchorPane.setBottomAnchor(n, 0.0);
            setNode(n);
        }catch (IOException e){
            e.printStackTrace();
        }
        btn_dashboard.setDisable(true);
        btn_dashboard.setOnMouseClicked(handler);
        btn_newbooking.setOnMouseClicked(handler);
        btn_viewbooking.setOnMouseClicked(handler);
        btn_earnings.setOnMouseClicked(handler);
        btn_logout.setOnMouseClicked(handler);
    }


    EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getSource() == btn_dashboard) {
                try {
                    btn_dashboard.setDisable(true);
                    btn_newbooking.setDisable(false);
                    btn_viewbooking.setDisable(false);
                    btn_earnings.setDisable(false);
                    Node n = (Node) FXMLLoader.load(getClass().getResource("/resources/views/dashstats.fxml"));
                    AnchorPane.setTopAnchor(n, 0.0);
                    AnchorPane.setRightAnchor(n, 0.0);
                    AnchorPane.setLeftAnchor(n, 0.0);
                    AnchorPane.setBottomAnchor(n, 0.0);
                    setNode(n);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (event.getSource() == btn_newbooking) {
                try{
                    btn_dashboard.setDisable(false);
                    btn_newbooking.setDisable(true);
                    btn_viewbooking.setDisable(false);
                    btn_earnings.setDisable(false);
                    Node n = (Node) FXMLLoader.load(getClass().getResource("/resources/views/newbooking.fxml"));
                    AnchorPane.setTopAnchor(n, 0.0);
                    AnchorPane.setRightAnchor(n, 0.0);
                    AnchorPane.setLeftAnchor(n, 0.0);
                    AnchorPane.setBottomAnchor(n, 0.0);
                    setNode(n);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (event.getSource() == btn_viewbooking) {
                btn_dashboard.setDisable(false);
                btn_newbooking.setDisable(false);
                btn_viewbooking.setDisable(true);
                btn_earnings.setDisable(false);
            }
            if (event.getSource() == btn_earnings) {
                btn_dashboard.setDisable(false);
                btn_newbooking.setDisable(false);
                btn_viewbooking.setDisable(false);
                btn_earnings.setDisable(true);
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

    private void setNode(Node node){
        holderpane.getChildren().clear();
        holderpane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
}
