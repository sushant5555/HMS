package app.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class dashcontroller implements Initializable {

    @FXML
    AnchorPane holderPane;

    AnchorPane homePane;
    AnchorPane newBooking;

    @FXML
    Button btn_dashboard;

    @FXML
    Button btn_newbooking;

    @FXML
    Button btn_viewbooking;

    @FXML
    Button btn_earnings;

    @FXML
    Button btn_logout;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createPage();
        btn_dashboard.setDisable(true);
        btn_dashboard.setOnMouseClicked(mouseEvent -> {
            try{
                btn_dashboard.setDisable(true);
                btn_newbooking.setDisable(false);
                btn_viewbooking.setDisable(false);
                btn_earnings.setDisable(false);
                homePane = FXMLLoader.load(getClass().getResource("/app/views/dashstats.fxml"));
                setNode(homePane);
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_newbooking.setOnMouseClicked(mouseEvent -> {
            try{
                btn_dashboard.setDisable(false);
                btn_newbooking.setDisable(true);
                btn_viewbooking.setDisable(false);
                btn_earnings.setDisable(false);

                newBooking = FXMLLoader.load(getClass().getResource("/app/views/newbooking.fxml"));
                setNode(newBooking);
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_viewbooking.setOnMouseClicked(mouseEvent -> {
            btn_dashboard.setDisable(false);
            btn_newbooking.setDisable(false);
            btn_viewbooking.setDisable(true);
            btn_earnings.setDisable(false);

        });

        btn_earnings.setOnMouseClicked(mouseEvent -> {
            btn_dashboard.setDisable(false);
            btn_newbooking.setDisable(false);
            btn_viewbooking.setDisable(false);
            btn_earnings.setDisable(true);

        });

        btn_logout.setOnMouseClicked(mouseEvent -> {
            btn_logout.getScene().getWindow().hide();
            Stage login =  new Stage();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getResource("/app/views/login.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root1);
            login.setScene(scene);
            login.show();
            login.setResizable(false);
        });
    }

    private void setNode(Node node){
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public void createPage(){
        try{
            homePane = FXMLLoader.load(getClass().getResource("/app/views/dashstats.fxml"));
            setNode(homePane);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
