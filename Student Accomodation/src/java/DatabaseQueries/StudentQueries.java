/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author 
 */
public class StudentQueries {
    private DBConnect dbConnect = new DBConnect();
    private Connection connection;
    
    private String sql;
    private PreparedStatement stmt;
    private ResultSet result;
    
    public int getStudent(String username, String password) throws ClassNotFoundException, SQLException{
        int rows = 1;
        connection = dbConnect.dbConnect();
        
        sql = "SELECT * FROM student WHERE username=? AND password=?";
        stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);
        result = stmt.executeQuery();
        
        result.last();
        rows = result.getRow();
        
        return rows;
    }
    
    public String signup(String username, String password) throws ClassNotFoundException, SQLException{
        String response = null;
        
        
        if (getStudent(username,password) < 1){
            sql = "INSERT INTO student (username, password) VALUES (?,?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            if(stmt.executeUpdate() > 0){
                response = "updtated";
            }else {
                response = "error";
            }
        }else {
            response = "existence";
        }
        
        
        
        return response;
    }
    
    public String signin(String username, String password) throws ClassNotFoundException, SQLException{
        String response = null;
        
        if(getStudent(username,password) < 1){
            response = "not found";
        }else {
            response = "success";
        }
        
        return response;
    }
 
}
