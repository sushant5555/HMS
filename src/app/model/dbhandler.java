package app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbhandler extends dbconfig{
    Connection dbconnection;
    public Connection getconnection(){
        String connectionString = "jdbc:mysql://"+dbconfig.dbhost+":"+dbconfig.dbport+"/"+dbconfig.dbname;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbconnection = DriverManager.getConnection(connectionString, dbconfig.dbuser, dbconfig.dbpass);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return dbconnection;
    }
}
