/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import DatabaseQueries.AdminQueries;
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
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Mundia
 */
@WebServlet(name = "admin", urlPatterns = {"/admin"})
public class admin extends HttpServlet {

    HttpSession session;
    private PrintWriter pWriter;
    private String reader;
    private JSONObject readerObj;
    private JSONObject responseObj = new JSONObject();
    private AdminQueries adminQry = new AdminQueries();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       pWriter = response.getWriter();
        response.setContentType("json");
        response.setCharacterEncoding("UTF-8");
        
        
        session = request.getSession();
        
        
            try {
                if (session.getAttribute("admin") != null){
                    String username = (String) session.getAttribute("student");
                    responseObj.put("response", "success");
                    responseObj.put("username", username);
                    pWriter.print(responseObj);
                }else{
                    responseObj.put("response", "not signed-in");
                    pWriter.print(responseObj);
                }
            } catch (JSONException ex) {
                Logger.getLogger(student.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       pWriter = response.getWriter();
       response.setContentType("json");
       response.setCharacterEncoding("UTF-8");
       
       session = request.getSession();
       
       BufferedReader read = new BufferedReader(new InputStreamReader(request.getInputStream()));
       reader = read.readLine();
       
        
        try {
            readerObj = new JSONObject(reader);
            String action = readerObj.getString("action");
            String username = readerObj.getJSONObject("info").getString("username");
            String password = readerObj.getJSONObject("info").getString("password");
            
            switch (action){
                case "signup":
                    responseObj.put("response", adminQry.signup(username, password));
                    pWriter.print(responseObj);
                    break;
                case "signin":
                    switch(adminQry.signin(username, password)){
                        case "success":
                            responseObj.put("response", "success");
                            pWriter.print(responseObj);
                            session.setAttribute("admin", username); 
                            break;
                        case "not found":
                            responseObj.put("response", "check your password and username and try again.!");
                            pWriter.print(responseObj);
                            break;
                        default:
                            responseObj.put("response", "error occured");
                            pWriter.print(responseObj);
                            break;
                    }
                    break;
                default:
                       responseObj.put("response", "error occured");
                       pWriter.print(responseObj);
                       break;
            }
        } catch (JSONException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
