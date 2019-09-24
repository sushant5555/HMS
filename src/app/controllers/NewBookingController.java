package app.controllers;

import app.database.DbHandler;
import app.model.Guest;
import app.model.RoomManager;
import app.model.Rooms;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewBookingController implements Initializable {
    @FXML
    private TextField tf_fname;
    @FXML
    private TextField tf_lname;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_contact;
    @FXML
    private TextField tf_guestplus;
    @FXML
    private ChoiceBox<String> cb_nationality;
    @FXML
    private DatePicker dp_cin;
    @FXML
    private ChoiceBox<String> cb_mealplan;
    @FXML
    private TableView<Rooms> tableview;
    @FXML
    private TableColumn<Rooms, Integer> tc_rno;
    @FXML
    private TableColumn<Rooms, String> tc_type;
    @FXML
    private TableColumn<Rooms, String> tc_book;
    @FXML
    private Button btn_double;
    @FXML
    private Button btn_single;
    @FXML
    private Button btn_queen;
    @FXML
    private Button btn_king;

    ObservableList mealplanList = FXCollections.observableArrayList(
            "Breakfast",
            "2 Meals",
            "3 Meals");
    ObservableList nationalityList = FXCollections.observableArrayList(
            "Indian",
            "American",
            "British",
            "Australian");
    ObservableList<Rooms> roomsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_nationality.setItems(nationalityList);
        cb_mealplan.setItems(mealplanList);
        loadData();
        loadTable("All");
        btn_single.setOnAction(actionEvent -> loadTable("Single"));
        btn_double.setOnAction(actionEvent -> loadTable("Double"));
        btn_queen.setOnAction(actionEvent -> loadTable("Queen"));
        btn_king.setOnAction(actionEvent -> loadTable("King"));
    }

    private void loadData() {
        roomsList.clear();
        DbHandler dbHandler = new DbHandler();
        ResultSet rs = dbHandler.getAvailableRooms();
        if (rs !=null){
            try{
                while(rs.next()){
                    roomsList.add(new Rooms(rs.getInt(1),rs.getString(2),
                            rs.getFloat(3),rs.getString(4)));
                }
            }catch (SQLException e){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(null);
                a.setContentText("Error reading database: "+e);
            }
        }
    }

    public void loadTable(String type){
        ObservableList<Rooms> filteredList = FXCollections.observableArrayList();
        loadData();
        if (type != "All"){
            filteredList.clear();
            for (Rooms rooms: roomsList) {
                if (rooms.getType().equals(type)){
                    filteredList.add(new Rooms(rooms.getNo(), rooms.getType(),
                            rooms.getPrice(), rooms.getAvail()));
                }
            }
        }
        tc_rno.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("no"));
        tc_type.setCellValueFactory(new PropertyValueFactory<Rooms, String>("type"));
        tc_book.setCellValueFactory(new PropertyValueFactory<Rooms, String>("dummy"));
        Callback<TableColumn<Rooms, String>, TableCell<Rooms, String>>
                cellFactory = new Callback<TableColumn<Rooms, String>, TableCell<Rooms, String>>() {
            //Overriding its default method call
            @Override
            public TableCell call(final TableColumn<Rooms, String> param) {
                //Each row will now contain graphics or text which we define here in table cell
                Button btn_book = new Button("Book");
                TableCell<Rooms, String> cell = new TableCell<Rooms, String>() {
                    //Overriding default method updateItem
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        //If item exist and is not empty we define tasks to do else we define nulls
                        if (item != null && !empty) {
                            //The defined update button should have click listener. So setOnAction.
                            btn_book.setOnAction(event -> {
                                //Now that we are inside the method where we want to perform task,
                                //we will fetch that whole row with below code
                                Rooms model = getTableView().getItems().get(getIndex());
                                //Above code fetches that specific model from tableview where that update
                                // button was clicked
                                DbHandler dbHandler1 = new DbHandler();
                                if (validate()){
                                    //Guest(int id, int rno, float amount, Date cin, String contact, Date cout,
                                    // String email, String firstname, String lastname,
                                    // String guestplus, String mealplan, String nationality, String paymentstat)
                                    if (dbHandler1.addGuest(new Guest(0,
                                            model.getNo(),0, Date.valueOf(dp_cin.getValue()) ,tf_contact.getText().trim(),null,
                                            tf_email.getText().trim(), tf_fname.getText().trim(),tf_lname.getText().trim(),
                                            tf_guestplus.getText().trim(),cb_mealplan.getValue().toString(),
                                            cb_nationality.getValue().toString(),null))){
                                            Alert success = new Alert(Alert.AlertType.INFORMATION);
                                            success.setHeaderText(null);
                                            success.setContentText("Booking done successfully!");
                                            success.show();
                                    }else{
                                        Alert a = new Alert(Alert.AlertType.ERROR);
                                        a.setHeaderText(null);
                                        a.setContentText("Error adding guest");
                                        a.show();
                                    }
                                }else {
                                    Alert a = new Alert(Alert.AlertType.ERROR);
                                    a.setHeaderText(null);
                                    a.setContentText("Incomplete fields");
                                    a.show();
                                }

                            });
                            //Setting graphic on that button
                            setGraphic(btn_book);
                        } else {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        tc_book.setCellFactory(cellFactory);
        if (type.equals("All")){
            tableview.setItems(roomsList);
        }else {
            tableview.setItems(filteredList);
        }
    }

    private boolean validate() {
        if (!tf_fname.getText().trim().isEmpty() && !tf_lname.getText().trim().isEmpty()
        && !tf_contact.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty()
        && cb_nationality.getValue() !=null && cb_mealplan.getValue() !=null
        && dp_cin.getValue() !=null){
            return true;
        }
        return false;
    }
}
