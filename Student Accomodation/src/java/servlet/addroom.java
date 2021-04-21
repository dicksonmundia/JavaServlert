/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import DatabaseQueries.RoomQueries;
import Object_Classes.Room;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Mundia
 */
@WebServlet(name = "addroom", urlPatterns = {"/addroom"})
public class addroom extends HttpServlet {

    private PrintWriter pWriter;
    private String reader;
    private JSONObject readerObj;
    private JSONObject responseObj = new JSONObject();
    private Room room = new Room();
    private RoomQueries  roomQry = new RoomQueries();
    
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       pWriter = response.getWriter();
       response.setContentType("json");
       response.setCharacterEncoding("UTF-8");
       
       
       BufferedReader read = new BufferedReader(new InputStreamReader(request.getInputStream()));
       reader = read.readLine();
       
        
        try {
            readerObj = new JSONObject(reader);
            int id = readerObj.getInt("id");
            String room_id = readerObj.getString("room_id");
            String room_type = readerObj.getString("room_type");
            String room_location = readerObj.getString("room_location");
            int room_charges = readerObj.getInt("room_charges");
            String room_payment = readerObj.getString("room_payment");
            String room_status = readerObj.getString("room_status");
            
            switch (readerObj.getString("action")){
                case "add":
                    room.setRoom_id(room_id);
                    room.setRoom_type(room_type);
                    room.setRoom_location(room_location);
                    room.setRoom_charges(room_charges);
                    room.setRoom_payment(room_payment);
                    room.setRoom_status(room_status);
                    
                    String resp = roomQry.addBook(room);
                    responseObj.put("response", resp);
                    pWriter.print(responseObj);
                    break;
                case "edit":
                    Room rm = new Room();
                    rm.setId(id);
                    rm.setRoom_id(room_id);
                    rm.setRoom_type(room_type);
                    rm.setRoom_location(room_location);
                    rm.setRoom_charges(room_charges);
                    rm.setRoom_payment(room_payment);
                    rm.setRoom_status(room_status);
                    
                    if (roomQry.editRoom(rm)){
                        responseObj.put("response", "success");
                        pWriter.print(responseObj); 
                    }else {
                         responseObj.put("response", "error");
                         pWriter.print(responseObj);
                    }
                    break;
                default:
                    break;
            }
            
            
            
        } catch (JSONException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(addroom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
