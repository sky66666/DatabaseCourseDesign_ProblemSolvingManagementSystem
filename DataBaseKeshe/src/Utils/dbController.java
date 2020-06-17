package Utils;

import java.sql.*;

public class dbController {

    private Connection conn;
    private Statement stat;

    public dbController(String dburl,String username,String password) throws SQLException {
        try{
            conn = DriverManager.getConnection(dburl,username,password);
            stat = conn.createStatement();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean addItem(String sql) {
        try {
            stat.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeItem(String sql) {
        try {
            stat.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet query(String sql) {
        try {
            return stat.executeQuery(sql);
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
