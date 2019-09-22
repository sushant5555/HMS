package app.database;

import javafx.scene.control.Alert;

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
    public boolean updateGuestCheckout(String id, float amount){
        try {
            String sql = "UPDATE `hms`.`guestinfo` SET `cout`= CURDATE(), `amount`=? WHERE `id`=?;";
            PreparedStatement preparedStatement = getconnection().prepareStatement(sql);
            preparedStatement.setFloat(1, amount);
            preparedStatement.setString(2, id);
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
