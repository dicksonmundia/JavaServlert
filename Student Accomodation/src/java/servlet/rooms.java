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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Mundia
 */
@WebServlet(name = "rooms", urlPatterns = {"/rooms"})
public class rooms extends HttpServlet {
    private PrintWriter pWriter;
    private String reader;
    private JSONObject readerObj;
    private JSONObject responseObj = new JSONObject();
    private RoomQueries  roomQry = new RoomQueries();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        pWriter = response.getWriter();
        response.setContentType("json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            List<Room> rooms = roomQry.getRooms();
            Iterator roomsItr = rooms.iterator();
            
            JSONArray array = new JSONArray();
            while (roomsItr.hasNext()){
                Room room = new Room();
                room = (Room) roomsItr.next();
                
                JSONObject rmObj = new JSONObject();
                rmObj.put("id", room.getId());
                rmObj.put("room_id", room.getRoom_id());
                rmObj.put("room_type", room.getRoom_type());
                rmObj.put("room_location", room.getRoom_location());
                rmObj.put("room_charges", room.getRoom_charges());
                rmObj.put("room_payment", room.getRoom_payment());
                rmObj.put("room_status", room.getRoom_status());
                
                array.put(rmObj);
            }
            
            pWriter.print(array);
        } catch (ClassNotFoundException | SQLException | JSONException ex) {
            Logger.getLogger(rooms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
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
            
            int room_id = readerObj.getInt("id");
            Room room = new Room();
            room.setId(room_id);
            
            switch (readerObj.getString("action")){
                case "get":
                    Room r = new Room();
                    r.setId(1);
                    RoomQueries rq = new RoomQueries();
                    List<Room> rm = rq.getRoom(r);
                    Iterator i = rm.iterator();
                    JSONObject ob = new JSONObject();
                    while (i.hasNext()){
                        Room o = new Room();
                        o = (Room) i.next();

                        ob.put("id", o.getId());
                        ob.put("room_id", o.getRoom_id());
                        ob.put("room_type", o.getRoom_type());
                        ob.put("room_location", o.getRoom_location());
                        ob.put("room_charges", o.getRoom_charges());
                        ob.put("room_payment", o.getRoom_payment());
                        ob.put("room_status", o.getRoom_status());

                    }
                    pWriter.print(ob);
                    break;
                case "remove":
                    responseObj.put("response", roomQry.removeRoom(room_id));
                    pWriter.print(responseObj);
                    break;
                default:
                    break;
            }
            
        } catch (JSONException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(rooms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
