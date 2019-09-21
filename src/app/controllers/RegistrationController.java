package app.controllers;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.animation.PauseTransition;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import app.database.DbHandler;

public class RegistrationController implements Initializable {
    @FXML
    private Button btn_register;

    @FXML
    private Button btn_login;

    @FXML
    private ImageView icon_loading;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_fullname;

    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_username;

    private Connection connection;
    private DbHandler handler ;
    private PreparedStatement pst, pst1;
    private int RESULT = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        icon_loading.setVisible(false);
        tf_username.setStyle("-fx-text-inner-color:a0a2ab");
        tf_password.setStyle("-fx-text-inner-color:a0a2ab");
        tf_email.setStyle("-fx-text-inner-color:a0a2ab");
        tf_fullname.setStyle("-fx-text-inner-color:a0a2ab");
        handler = new DbHandler();
    }

    @FXML
    public void register(ActionEvent e){
        icon_loading.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(3));

        pt.setOnFinished(ev ->{
            //Saving data
            String insert =  "INSERT INTO userinfo(name, username, email, password) "
                    +"VALUES(?,?,?,?)";

            String check =  "SELECT * FROM userinfo WHERE username = ? OR email = ?";

            try {
                connection = handler.getconnection();
                pst1 = connection.prepareStatement(check);
                pst1.setString(1,tf_username.getText());
                pst1.setString(2, tf_email.getText());
                ResultSet rs =  pst1.executeQuery();
                int size= 0;
                if (rs != null)
                {
                    rs.beforeFirst();
                    rs.last();
                    size = rs.getRow();
                }
                if (size == 0) {
                    pst = connection.prepareStatement(insert);
                    pst.setString(1, tf_fullname.getText());
                    pst.setString(2, tf_username.getText());
                    pst.setString(3, tf_email.getText());
                    pst.setString(4, tf_password.getText());
                    RESULT = pst.executeUpdate();
                    System.out.print(RESULT);
                } else {
                    System.out.println("Username or email already exists.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if (RESULT == 1){
                tf_fullname.setText("");
                tf_username.setText("");
                tf_email.setText("");
                tf_password.setText("");
                RESULT = 0;
            }
            icon_loading.setVisible(false);
        });
        pt.play();
    }

    @FXML
    public void gotoLogin() throws IOException {
        btn_register.getScene().getWindow().hide();
        Stage login =  new Stage();
        Parent root1 = FXMLLoader.load(getClass().getResource("/resources/views/login.fxml"));
        Scene scene = new Scene(root1);
        login.setScene(scene);
        login.show();
        login.setResizable(false);
    }
}
