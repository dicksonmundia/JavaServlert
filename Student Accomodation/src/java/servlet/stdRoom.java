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
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Mundia
 */
@WebServlet(name = "stdRoom", urlPatterns = {"/stdRoom"})
public class stdRoom extends HttpServlet {
    private PrintWriter pWriter;
    private String reader;
    private JSONObject readerObj;
    private JSONObject responseObj = new JSONObject();
    private Room room = new Room();
    private RoomQueries  roomQry = new RoomQueries();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
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
            String username = readerObj.getString("username");
            switch (readerObj.getString("action")){
                case "get":

                    List<Room> rm = roomQry.getMyRoom(username);
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
                case "pay":
                    String room_id = readerObj.getString("room_id");
                    
                    if (roomQry.pay(username, room_id)){
                        responseObj.put("response","success" );
                        pWriter.print(responseObj);
                    }else {
                        responseObj.put("response","error");
                        pWriter.print(responseObj);
                    }
                    
                    break;
                case "remove":
                    String room_id2 = readerObj.getString("room_id");
                    if (roomQry.removeRm(username, room_id2)){
                        responseObj.put("response","success" );
                        pWriter.print(responseObj);
                    }
                    else {
                        responseObj.put("response","error");
                        pWriter.print(responseObj);
                    }
                    break;
                default:
                    responseObj.put("response","error");
                        pWriter.print(responseObj);
                    break;
            }
        } catch (JSONException ex) {
            Logger.getLogger(stdRoom.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(stdRoom.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(stdRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
