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
@WebServlet(name = "bookRoom", urlPatterns = {"/bookRoom"})
public class bookRoom extends HttpServlet {

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
            String room_type = readerObj.getString("type");
            String username = readerObj.getString("username");
            String room_location = readerObj.getString("location");
            int room_charges = readerObj.getInt("charges");
            String room_payment = readerObj.getString("payment");
            
            if (roomQry.bookRoom(username, room_location, room_payment, room_type, id, room_charges)){
                responseObj.put("response", "success");
                pWriter.print(responseObj);
            }else {
                responseObj.put("response", "error");
                pWriter.print(responseObj);
            }
            
      } catch (JSONException ex) {
          Logger.getLogger(bookRoom.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ClassNotFoundException ex) {
          Logger.getLogger(bookRoom.class.getName()).log(Level.SEVERE, null, ex);
      } catch (SQLException ex) {
          Logger.getLogger(bookRoom.class.getName()).log(Level.SEVERE, null, ex);
      }
       
    }

  
}
