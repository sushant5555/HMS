package app.controllers;

import app.database.DbHandler;
import javafx.animation.PauseTransition;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button btn_login;
    @FXML
    private ImageView icon_loading;
    @FXML
    private PasswordField tf_password;
    @FXML
    private TextField tf_username;

    private DbHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        icon_loading.setVisible(false);
        handler = new DbHandler();
    }

    @FXML
    public void loginAction(ActionEvent e)throws IOException{
        btn_login.setDisable(true);
        DbHandler dbHandler = new DbHandler();
        if(dbHandler.login(tf_username.getText(), tf_password.getText())){
            btn_login.getScene().getWindow().hide();
            Stage dashboard =  new Stage();
            Parent dashroot = FXMLLoader.load(getClass().getResource("/resources/views/dash.fxml"));
            Scene dashscene = new Scene(dashroot);
            dashboard.setScene(dashscene);
            dashboard.show();
            dashboard.setResizable(false);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Login error");
            alert.setContentText("Invalid username/password");
            alert.show();
        }
    }

    @FXML
    public void registerAction(ActionEvent e) throws IOException {
        btn_login.getScene().getWindow().hide();
        Stage registration =  new Stage();
        Parent root1 = FXMLLoader.load(getClass().getResource("/resources/views/register.fxml"));
        Scene scene = new Scene(root1);
        registration.setScene(scene);
        registration.show();
        registration.setResizable(false);
    }
}

















