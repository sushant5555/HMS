package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public ResultSet getRooms(){
        String sql = "";
        getconnection();
        return null;
    }
}
