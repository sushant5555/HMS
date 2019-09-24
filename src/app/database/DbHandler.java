package app.database;

import app.model.Guest;
import app.model.ViewBooking;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

import java.sql.*;

public class DbHandler {
    public Connection getconnection(){
        String connectionString = "jdbc:mysql://"+ DbConfig.dbhost+":"+ DbConfig.dbport+"/"+ DbConfig.dbname;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(connectionString, DbConfig.dbuser, DbConfig.dbpass);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //CREATE
    public boolean addRoom(String rno, String price, String type) {
        try {
            String sql = "INSERT INTO `hms`.`roomsinfo` (`rno`, `rtype`, `price`, `availability`) " +
                    "VALUES (?, ?, ?, 'Yes');";
            PreparedStatement preparedStatement = getconnection().prepareStatement(sql);
            preparedStatement.setString(1, rno);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, price);
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                return true;
            }
        }catch (SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Database update error: "+e);
            a.show();
        }
        return false;
    }

    public boolean addGuest(Guest guest){
        try {
            String sql = "INSERT INTO `hms`.`guestinfo` (`ROOM_id`, `cin`," +
                    "`contact`, `email`, `firstname`, `lastname`, `guestplus`, " +
                    "`mealplan`, `nationality`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = getconnection().prepareStatement(sql);
            preparedStatement.setInt(1, guest.getRno());
            preparedStatement.setDate(2, guest.getCin());
            preparedStatement.setString(3, guest.getContact());
            preparedStatement.setString(4, guest.getEmail());
            preparedStatement.setString(5, guest.getFirstname());
            preparedStatement.setString(6, guest.getLastname());
            preparedStatement.setString(7, guest.getGuestplus());
            preparedStatement.setString(8, guest.getMealplan());
            preparedStatement.setString(9, guest.getNationality());
            int result = preparedStatement.executeUpdate();

            //Mark that room no. as not available now
            String sql2 ="UPDATE `roomsinfo` SET availability='No' WHERE `rno`=?";
            PreparedStatement ps2 = getconnection().prepareStatement(sql2);
            ps2.setInt(1,guest.getRno());
            int result2 = ps2.executeUpdate();
            if (result == 1 && result2 == 1){
                return true;
            }
        }catch (SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Database update error: "+e);
            a.show();
        }
        return false;
    }


    //READ
    public ResultSet getRooms(){
        try{
            String sql = "SELECT * FROM roomsinfo;";
            Statement s = getconnection().createStatement();
            return  s.executeQuery(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getAvailableRooms(){
        try{
            String sql = "SELECT * FROM roomsinfo WHERE availability = 'Yes';";
            Statement s = getconnection().createStatement();
            return  s.executeQuery(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getBookings(){
        try{
            //language=MYSQL-SQL
            String sql = "SELECT id, cin, cout, concat(firstname, ' ' ,lastname), guestplus, email, contact, " +
                    "nationality, ROOM_id , roomsinfo.rtype, mealplan, amount " +
                    "FROM guestinfo " +
                    "JOIN roomsinfo ON ROOM_id = roomsinfo.rno;";
            Statement s = getconnection().createStatement();
            return  s.executeQuery(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getSearchedBookings(Date fromdate, Date todate){
        try{
            //language=MYSQL-SQL
            String sql = "SELECT id, cin, cout, concat(firstname, ' ' ,lastname), guestplus, email, contact, " +
                    "nationality, ROOM_id , roomsinfo.rtype, mealplan, amount " +
                    "FROM guestinfo " +
                    "JOIN roomsinfo ON ROOM_id = roomsinfo.rno "+
                    "WHERE cin BETWEEN ? AND ?;";
            PreparedStatement preparedStatement = getconnection().prepareStatement(sql);
            preparedStatement.setDate(1, fromdate);
            preparedStatement.setDate(2, todate);
            return preparedStatement.executeQuery();
        }catch (SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Database read error: "+e);
            e.printStackTrace();
            a.show();
        }
        return null;
    }

    public ResultSet getAllTimeRevenue(){
        try{
            //language=MYSQL-SQL
            String sql = "select count(id), count(cout), sum(amount) from guestinfo " +
                    "JOIN roomsinfo ON ROOM_id = roomsinfo.rno;";
            PreparedStatement preparedStatement = getconnection().prepareStatement(sql);
            return preparedStatement.executeQuery();
        }catch (SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.setContentText("Database read error: "+e);
            a.show();
        }
        return null;
    }

    public ResultSet getDatedRevenue(Date from, Date to){
        try{
            String sql = "select count(id), count(cout), sum(amount) from guestinfo " +
                    "JOIN roomsinfo ON ROOM_id = roomsinfo.rno " +
                    "WHERE cin between ? AND ? ;";
            PreparedStatement preparedStatement = getconnection().prepareStatement(sql);
            preparedStatement.setDate(1,from);
            preparedStatement.setDate(2,to);
            return preparedStatement.executeQuery();
        }catch (SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.setContentText("Database read error: "+e);
            e.printStackTrace();
            a.show();
        }
        return null;
    }

    public ResultSet getDatedRevenueByRoomType(Date from, Date to){
        try{
            String sql = "select rtype, sum(amount) from guestinfo " +
                    "JOIN roomsinfo ON ROOM_id = roomsinfo.rno " +
                    "WHERE cin BETWEEN ? AND ? GROUP BY rtype;";
            PreparedStatement preparedStatement = getconnection().prepareStatement(sql);
            preparedStatement.setDate(1,from);
            preparedStatement.setDate(2,to);
            return preparedStatement.executeQuery();
        }catch (SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            e.printStackTrace();
            a.setContentText("Database read error: "+e);
            a.show();
        }
        return null;
    }


    //FETCH OCCUPIED ROOMS FOR CHECKING OUT
    public ResultSet getRoomStatus(){
        try{
            //language=MYSQL-SQL
            String sql = "SELECT roomsinfo.rno, " +
                    "guestinfo.id, " +
                    "roomsinfo.rtype," +
                    "CONCAT(guestinfo.firstname,' ', guestinfo.lastname)," +
                    "guestinfo.cin, (1+DATEDIFF(CURDATE(),guestinfo.cin))," +
                    "roomsinfo.price*(1+DATEDIFF(CURDATE(),guestinfo.cin)) " +
                    "FROM hms.roomsinfo " +
                    "JOIN guestinfo ON guestinfo.ROOM_id = roomsinfo.rno " +
                    "WHERE guestinfo.cout IS NULL;";
            Statement s = getconnection().createStatement();
            return  s.executeQuery(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //UPDATE
    public boolean updateGuest(ViewBooking model){
        try {
            String sql = "UPDATE guestinfo SET `contact`= ?, `email`= ?, `guestplus`= ?, `mealplan`= ?, `nationality` = ? WHERE `id`=?;";
            PreparedStatement preparedStatement = getconnection().prepareStatement(sql);
            preparedStatement.setString(1, model.getContact());
            preparedStatement.setString(2, model.getEmail());
            preparedStatement.setInt(3, model.getPlus());
            preparedStatement.setString(4, model.getMeal());
            preparedStatement.setString(5, model.getNationality());
            preparedStatement.setInt(6, model.getGuestid());
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                return true;
            }
        }catch (SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Database update error: "+e);
            e.printStackTrace();
            a.show();
        }
        return false;
    }


    public boolean updateGuestCheckout(String id, float amount, int rno){
        try {
            String sql = "UPDATE `hms`.`guestinfo` SET `cout`= CURDATE(), `amount`=? WHERE `id`=?;";
            PreparedStatement preparedStatement = getconnection().prepareStatement(sql);
            preparedStatement.setFloat(1, amount);
            preparedStatement.setString(2, id);
            int result = preparedStatement.executeUpdate();
            String sql2 ="UPDATE `roomsinfo` SET availability='Yes' WHERE `rno`=?";
            PreparedStatement ps2 = getconnection().prepareStatement(sql2);
            ps2.setInt(1,rno);
            int result2 = ps2.executeUpdate();
            if (result == 1 && result2 ==1){
                return true;
            }
        }catch (SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Database update error: "+e);
            e.printStackTrace();
            a.show();
        }
        return false;
    }

    public boolean updateRoom(String rno, String price, String type){
        try {
            String sql = "UPDATE `hms`.`roomsinfo` SET `price`= ?, `rtype`=? WHERE `rno`=?;";
            PreparedStatement preparedStatement = getconnection().prepareStatement(sql);
            preparedStatement.setString(1, price);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, rno);
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                return true;
            }
        }catch (SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Database update error: "+e);
            a.show();
        }
        return false;
    }


    //DELETE
    public boolean deleteRoom(int rno){
        try {
            String sql = "DELETE FROM `hms`.`roomsinfo` WHERE `rno`=?;";
            PreparedStatement preparedStatement = getconnection().prepareStatement(sql);
            preparedStatement.setInt(1, rno);
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                return true;
            }
        }catch (SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Database update error: "+e);
            a.show();
        }
        return false;
    }



}