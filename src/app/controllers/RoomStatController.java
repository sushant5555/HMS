package app.controllers;

import app.database.DbHandler;
import app.model.RoomStatus;
import app.model.Rooms;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RoomStatController implements Initializable {
    @FXML
    private TableView<RoomStatus> tableview;

    @FXML
    private TableColumn<RoomStatus, Integer> tc_rno;

    @FXML
    private TableColumn<RoomStatus, String> tc_type;

    @FXML
    private TableColumn<RoomStatus, String> tc_name;

    @FXML
    private TableColumn<RoomStatus, Date> tc_cin;

    @FXML
    private TableColumn<RoomStatus, Integer> tc_days;

    @FXML
    private TableColumn<RoomStatus, Float> tc_topay;

    @FXML
    private TableColumn<RoomStatus, String> tc_cout;

    ObservableList<RoomStatus> roomStatuses = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRooms();
    }
    public void loadRooms(){
        roomStatuses.clear();
        DbHandler dbHandler = new DbHandler();
        ResultSet rs = dbHandler.getRoomStatus();
        if (rs!=null){
            try {
                while (rs.next()){
                    roomStatuses.add(new RoomStatus(rs.getInt(1),rs.getString(2),
                            rs.getString(3),rs.getString(4),
                            rs.getDate(5), rs.getInt(6),
                            rs.getFloat(7)));
                    System.out.println(rs.getString(1));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        tc_rno.setCellValueFactory(new PropertyValueFactory<RoomStatus, Integer>("rno"));
        tc_type.setCellValueFactory(new PropertyValueFactory<RoomStatus, String>("type"));
        tc_name.setCellValueFactory(new PropertyValueFactory<RoomStatus, String>("name"));
        tc_cin.setCellValueFactory(new PropertyValueFactory<RoomStatus, Date>("date"));
        tc_days.setCellValueFactory(new PropertyValueFactory<RoomStatus, Integer>("days"));
        tc_topay.setCellValueFactory(new PropertyValueFactory<RoomStatus, Float>("price"));
        tc_cout.setCellValueFactory(new PropertyValueFactory<RoomStatus, String>("dummy"));
        Callback<TableColumn<RoomStatus, String>, TableCell<RoomStatus, String>>
                cellFactory = new Callback<TableColumn<RoomStatus, String>, TableCell<RoomStatus, String>>() {
            @Override
            public TableCell call(final TableColumn<RoomStatus, String> param) {
                Button btn_cout = new Button("Check Out");
                TableCell<RoomStatus, String> cell = new TableCell<RoomStatus, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            btn_cout.setOnAction(event -> {
                                RoomStatus model = getTableView().getItems().get(getIndex());
                                DbHandler handler = new DbHandler();
                                if (handler.updateGuestCheckout(model.getGuestid(),model.getPrice(), model.getRno())){
                                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                                    a.setHeaderText(null);
                                    a.setContentText("Success!");
                                    a.show();
                                    refresh();
                                }else {
                                    Alert a = new Alert(Alert.AlertType.ERROR);
                                    a.setHeaderText(null);
                                    a.setContentText("Error adding");
                                    a.show();
                                }
                            });
                            setGraphic(btn_cout);
                        } else {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        tc_cout.setCellFactory(cellFactory);
        tableview.setItems(roomStatuses);
    }

    public void refresh(){
        loadRooms();
    }
}
