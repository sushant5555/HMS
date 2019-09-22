package app.controllers;

import app.database.DbHandler;
import app.model.Rooms;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashStatsController implements Initializable {
    @FXML
    private TableView tableview;
    @FXML
    private Button btn_rooms;
    @FXML
    private Button btn_bookings;
    @FXML
    private Button btn_earnings;
    @FXML
    private Label lbl_bookings;
    @FXML
    private Label lbl_rooms;
    @FXML
    private Label lbl_earnings;

    ObservableList<Rooms> roomsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRooms();
    }

    public void loadRooms(){
        DbHandler dbHandler = new DbHandler();
        ResultSet rs = dbHandler.getRooms();
        if (rs!=null){
            try {
                while (rs.next()){
                    roomsList.add(new Rooms(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
                    System.out.println(rs.getString(1));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        TableColumn roomno = new TableColumn<>("Room No.");
        roomno.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("no"));
        TableColumn roomtype = new TableColumn<>("Room Type.");
        roomtype.setCellValueFactory(new PropertyValueFactory<Rooms, String>("type"));
        TableColumn price = new TableColumn<>("Price");
        price.setCellValueFactory(new PropertyValueFactory<Rooms, Float>("price"));
        TableColumn available = new TableColumn<>("Availability");
        available.setCellValueFactory(new PropertyValueFactory<Rooms, String>("avail"));
        tableview.getColumns().addAll(roomno, roomtype, price,available);
        tableview.setItems(roomsList);
    }
}



















