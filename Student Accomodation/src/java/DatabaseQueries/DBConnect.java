/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 
 */
public class DBConnect {
    private final String dbUrl = "jdbc:mysql://localhost:3306/student_accomodation?useLegacyDatetimeCode=false&serverTimezone=UTC# [root on Default schema]";
    private final String dbPassword = "";
    private final String dbRoot = "root";
    
    public Connection dbConnect() throws ClassNotFoundException, SQLException{
        Connection connection;
        connection = null;
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(dbUrl, dbRoot, dbPassword);
        return connection;
    }
}
