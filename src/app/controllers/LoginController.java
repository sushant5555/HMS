package app.controllers;

import app.model.dbhandler;
import javafx.animation.PauseTransition;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button btn_login;

    @FXML
    private Button btn_register;

    @FXML
    private ImageView icon_loading;

    @FXML
    private CheckBox remembercb;

    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_username;

    @FXML
    private Label txt_logo;

    private dbhandler handler;
    private Connection connection;
    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        icon_loading.setVisible(false);
        tf_username.setStyle("-fx-text-inner-color:a0a2ab");
        tf_password.setStyle("-fx-text-inner-color:a0a2ab");
        handler = new dbhandler();
    }

    @FXML
    public void loginAction(ActionEvent e){
        icon_loading.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(3));
        pt.setOnFinished(ev ->{
            String insert =  "SELECT * FROM userinfo WHERE username = ? AND password = ?";
            try {
                connection = handler.getconnection();
                pst = connection.prepareStatement(insert);
                pst.setString(1,tf_username.getText());
                pst.setString(2,tf_password.getText());
                ResultSet rs =  pst.executeQuery();
                int size= 0;
                if (rs != null)
                {
                    rs.beforeFirst();
                    rs.last();
                    size = rs.getRow();
                }
                if (size == 1){
                    System.out.println("Login Successful");
                    btn_login.getScene().getWindow().hide();
                    Stage dashboard =  new Stage();
                    Parent root2 = FXMLLoader.load(getClass().getResource("/app/views/dash.fxml"));
                    Scene scene = new Scene(root2);
                    dashboard.setScene(scene);
                    dashboard.show();
                    dashboard.setResizable(false);

                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Login Error");
                    alert.setContentText("Incorrect username or password");
                    alert.show();
                    icon_loading.setVisible(false);
                }
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
            icon_loading.setVisible(false);
        });
        pt.play();
    }

    @FXML
    public void registerAction(ActionEvent e) throws IOException {
        btn_login.getScene().getWindow().hide();
        Stage registration =  new Stage();
        Parent root1 = FXMLLoader.load(getClass().getResource("/app/views/register.fxml"));
        Scene scene = new Scene(root1);
        registration.setScene(scene);
        registration.show();
        registration.setResizable(false);
    }
}

















