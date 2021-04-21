/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
@WebServlet(name = "logout", urlPatterns = {"/logout"})
public class logout extends HttpServlet {
    private PrintWriter mPrintWtr;
    JSONObject responseObj = new JSONObject();


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        mPrintWtr = response.getWriter();
        response.setContentType("json");
        response.setCharacterEncoding("UTF-8");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        
        String readerObjString = reader.readLine();
        JSONObject readObj;
        try {
            readObj = new JSONObject(readerObjString);
            String action = readObj.getString("action");
            switch (action){
                case "admin":
                    session.removeAttribute("admin");
                    responseObj.put("response", "success");
                    mPrintWtr.print(responseObj);
                    break;
                case "student":
                    session.removeAttribute("success");
                    responseObj.put("response", "success");
                    mPrintWtr.print(responseObj);
                    break;
                default:
                    responseObj.put("response", "error");
                    mPrintWtr.print(responseObj);
                    break;
            }
        } catch (JSONException ex) {
            Logger.getLogger(logout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
