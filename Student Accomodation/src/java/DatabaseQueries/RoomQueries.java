/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseQueries;

import Object_Classes.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author 
 */
public class RoomQueries {
    private DBConnect dbConnect = new DBConnect();
    private Connection connection;
    
    private String sql;
    private PreparedStatement stmt;
    private ResultSet result;
    
    public String addBook(Room room) throws ClassNotFoundException, SQLException{
        String response = null;
        
        connection = dbConnect.dbConnect();
        
        sql = "INSERT INTO room (room_id, room_type, room_location, room_charges, room_payment, room_status) VALUES (?,?,?,?,?,?)";
        stmt = connection.prepareStatement(sql);
        stmt.setString(1, room.getRoom_id());
        stmt.setString(2, room.getRoom_type());
        stmt.setString(3, room.getRoom_location());
        stmt.setInt(4, room.getRoom_charges());
        stmt.setString(5, room.getRoom_payment());
        stmt.setString(6, room.getRoom_status());
        
        if (stmt.executeUpdate() > 0){
            response = "success";
        }else {
            response = "error";
        }
        return response;
    }
    
    public List<Room> getRooms() throws ClassNotFoundException, SQLException{
        List<Room> rooms = new ArrayList();
        
        connection = dbConnect.dbConnect();
        
        sql = "SELECT * FROM room ";
        stmt = connection.prepareStatement(sql);
        result = stmt.executeQuery();
        
        while (result.next()){
            Room room = new Room();
            room.setId(result.getInt("id"));
            room.setRoom_id(result.getString("room_id"));
            room.setRoom_type(result.getString("room_type"));
            room.setRoom_location(result.getString("room_location"));
            room.setRoom_charges(result.getInt("room_charges"));
            room.setRoom_payment(result.getString("room_payment"));
            room.setRoom_status(result.getString("room_status"));
            
            rooms.add(room);
        }
        
        return rooms;
    }
    
    public List<Room> getRoom(Room rmId) throws ClassNotFoundException, SQLException{
        List<Room> room = new ArrayList();
        
        connection = dbConnect.dbConnect();
        
        sql = "SELECT * FROM room WHERE id=?";
        stmt  = connection.prepareStatement(sql);
        stmt.setInt(1, rmId.getId());
        
        result = stmt.executeQuery();
        
        while (result.next()){
            Room rm = new Room();
            rm.setId(result.getInt("id"));
            rm.setRoom_id(result.getString("room_id"));
            rm.setRoom_type(result.getString("room_type"));
            rm.setRoom_location(result.getString("room_location"));
            rm.setRoom_charges(result.getInt("room_charges"));
            rm.setRoom_payment(result.getString("room_payment"));
            rm.setRoom_status(result.getString("room_status"));
            
            room.add(rm);
        }
        
        
        return room;
    }
    
    public boolean editRoom(Room room) throws ClassNotFoundException, SQLException{
        boolean success = false;
        
        connection = dbConnect.dbConnect();
        
        sql = "UPDATE room SET room_id=?, room_type=?, room_location=?, room_charges=?, room_payment=?, room_status=? WHERE id=?";
        stmt = connection.prepareStatement(sql);
        stmt.setString(1, room.getRoom_id());
        stmt.setString(2, room.getRoom_type());
        stmt.setString(3, room.getRoom_location());
        stmt.setInt(4, room.getRoom_charges());
        stmt.setString(5, room.getRoom_payment());
        stmt.setString(6, room.getRoom_status());
        stmt.setInt(7, room.getId());
        
        if (stmt.executeUpdate() > 0)success = true;
        
        return success;
    }
    
   
    
    public String removeRoom (int id) throws ClassNotFoundException, SQLException{
        String response = null;
        connection = dbConnect.dbConnect();
        
        sql = "DELETE FROM room WHERE id=?";
        stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        if(stmt.executeUpdate() > 0)response="success";
        
        return response;
    }
    
    public int getstdrooms (String username) throws ClassNotFoundException, SQLException{
        connection = dbConnect.dbConnect();
        sql= "SELECT * FROM std_room WHERE username =?";
        stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        result = stmt.executeQuery();
        
        result.last();
        int rows = result.getRow();
        
        return rows;
    }
    
    public boolean bookRoom(String username, String room_location, String room_payment, String room_type, int room_id, int charges) throws ClassNotFoundException, SQLException{
        boolean success = false;
        connection = dbConnect.dbConnect();
        if(getstdrooms(username) < 1){
             //	username	room_id	room_charges	room_location	room_type	room_payment	
        sql = "INSERT INTO std_room (username,room_id,room_charges,room_location,room_type,room_payment) VALUES (?,?,?,?,?,?)";
        stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setInt(2, room_id);
        stmt.setInt(3, charges);
        stmt.setString(4, room_location);
        stmt.setString(5, room_type);
        stmt.setString(6, "Not Paid");
        
        if(stmt.executeUpdate() > 0){
            sql = "UPDATE room SET room_payment=?, room_status=? WHERE id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, "Not Paid");
            stmt.setString(2, "allocated");
            stmt.setInt(3, room_id);
            if(stmt.executeUpdate()>0)success=true;
        }
        }
       
        
        
        return success;
    }
    
    public List<Room> getMyRoom(String username) throws ClassNotFoundException, SQLException{
        List<Room> rom = new ArrayList();
        connection = dbConnect.dbConnect();
        sql = "SELECT * FROM std_room WHERE username =?";
        stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        
        result = stmt.executeQuery();
        while(result.next()){
            Room rm = new Room();
            rm.setId(result.getInt("room_id"));
            rm.setRoom_type(result.getString("room_type"));
            rm.setRoom_location(result.getString("room_location"));
            rm.setRoom_charges(result.getInt("room_charges"));
            rm.setRoom_payment(result.getString("room_payment"));

            rom.add(rm);
            
        }

        return rom;
    }
    
    public boolean pay(String username, String room_id) throws ClassNotFoundException, SQLException{
        boolean success = false;
        connection = dbConnect.dbConnect();
        sql = "UPDATE room SET room_payment=? WHERE room_id=?";
        stmt = connection.prepareStatement(sql);
        stmt.setString(1, "Paid");
        stmt.setString(2, room_id);
        
        if (stmt.executeUpdate() > 0){
            sql = "UPDATE std_room SET room_payment=? WHERE username=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, "Paid");
            stmt.setString(2, username);
            if (stmt.executeUpdate() > 0)success=true;
        }
        
        
        
        return success;
        
    }
    
    public boolean removeRm(String username, String room_id) throws ClassNotFoundException, SQLException{
        boolean success = false;
        connection = dbConnect.dbConnect();
        sql = "UPDATE room SET room_payment=?, room_status=?  WHERE room_id=?";
        stmt = connection.prepareStatement(sql);
        stmt.setString(1, "Not Paid");
        stmt.setString(2, "available");
        stmt.setString(3, room_id);
        if (stmt.executeUpdate() > 0){
            sql = "DELETE FROM std_room WHERE username=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            if (stmt.executeUpdate() > 0)success=true;
        }
        
        return success;
    }
  
}
