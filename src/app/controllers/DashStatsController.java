package app.controllers;

import app.database.DbHandler;
import app.model.Rooms;
import app.model.ViewBooking;
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
    ObservableList<ViewBooking> bookings = FXCollections.observableArrayList();
    int roomcount=0;
    int bookingcount = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_rooms.setText("0");
        lbl_bookings.setText("0");
        loadEarnings();
        loadBookings();
        loadRooms();

        btn_rooms.setOnAction(actionEvent -> loadRooms());
        btn_bookings.setOnAction(actionEvent -> loadBookings());
    }

    public void loadEarnings(){
        DbHandler dbHandler = new DbHandler();
        ResultSet rs = dbHandler.getAllTimeRevenue();
        if (rs!=null){
            try {
                while (rs.next()){
                    lbl_earnings.setText(rs.getString(3));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadBookings(){
        tableview.getColumns().clear();
        bookings.clear();
        bookingcount=0;
        DbHandler dbHandler = new DbHandler();
        ResultSet rs = dbHandler.getBookings();
        if (rs!=null){
            try {
                while (rs.next()){
                        if (rs.getString(3) == null){
                            bookings.add(new ViewBooking(rs.getInt(1),rs.getDate(2), rs.getDate(3), rs.getString(4),
                                    rs.getInt(5), rs.getString(6),rs.getString(7),
                                    rs.getString(8), rs.getInt(9),rs.getString(10),
                                    rs.getString(11),rs.getFloat(12)));
                            bookingcount++;
                        }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        lbl_bookings.setText(""+bookingcount);
        TableColumn roomno = new TableColumn<>("Room No.");
        roomno.setCellValueFactory(new PropertyValueFactory<ViewBooking, Integer>("rno"));
        TableColumn name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<ViewBooking, String>("name"));
        TableColumn contact = new TableColumn<>("Contact");
        contact.setCellValueFactory(new PropertyValueFactory<ViewBooking, String>("contact"));
        tableview.getColumns().addAll(roomno, name, contact);
        tableview.setItems(bookings);
    }

    public void loadRooms(){
        tableview.getColumns().clear();
        roomcount = 0;
        roomsList.clear();
        DbHandler dbHandler = new DbHandler();
        ResultSet rs = dbHandler.getRooms();
        if (rs!=null){
            try {
                while (rs.next()){
                    roomsList.add(new Rooms(rs.getInt(1),rs.getString(2),rs.getFloat(3),rs.getString(4)));
                    roomcount++;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        lbl_rooms.setText(""+roomcount);
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



















