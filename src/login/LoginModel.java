package login;

import db.DbConnection;

import java.sql.*;

public class LoginModel {
    Connection connection;

    public LoginModel(){
        try{
            this.connection = DbConnection.getConnection();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        if(this.connection == null){
            System.exit(1);
        }
    }

    public boolean isDatabaseConnected(){
        return this.connection != null;
    }

    public boolean isLogin(String user, String pass, String opt)throws Exception{
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql1 = "SELECT * FROM login where user=? and pass=? and div=?";

        try{
            pr = this.connection.prepareStatement(sql1);
            pr.setString(1, user);
            pr.setString(2, pass);
            pr.setString(3, opt);

            rs = pr.executeQuery();


            if(rs.next()){
                return true;
            }
            return false;
        }catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
        finally {
            {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) { /* ignored */}
                }
                if (pr != null) {
                    try {
                        pr.close();
                    } catch (SQLException e) { /* ignored */}
                }
            }
        }
    }
}
