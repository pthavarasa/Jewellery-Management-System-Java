package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnection {
    public static Connection getConnection() throws SQLException{
        try{
            Class.forName("org.sqlite.JDBC");
            String SQCONN = "jdbc:sqlite:jewelry.db";
            return DriverManager.getConnection(SQCONN);
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
