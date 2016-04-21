/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Prabhunath
 */
public class UserRegister extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           Class.forName("com.mysql.jdbc.Driver");
           HttpSession se=request.getSession();
        Connection con=DriverManager.getConnection("jdbc:mysql://127.11.188.130:3306/ibeorcc","adminaHSV5Sy","iSVhA33vaX73");
           String message="";
           String user_name,email,password,contact,location;
           user_name=request.getParameter("user_name");
           email=request.getParameter("email");
           password=request.getParameter("password");
           contact=request.getParameter("contact");
           location=request.getParameter("location");
           String query="insert into users (name, email, password,contact,location,status)values('"+user_name+"','"+email+"','"+password+"','"+contact+"','"+location+"','"+"False"+"')";
           
           if(user_name.equals(""))
           {
               message="Enter your name";
               return;
           }
           if(email.equals(""))
           {
               message+="Enter your Email<br>";
               
           }
           if(password.equals(""))
           {
               message+="Enter your Password<br>";
               
           }
           if(contact.equals(""))
           {
               message+="Enter your Contact Detail<br>";
           }  
           if(location.equals(""))
           {
               message+="Enter your Location<br>";   
           }
           if(message.length()!=0)
           {
               se.setAttribute("msg", message);
               response.sendRedirect("userregister.jsp");
           }
           Statement pst=con.createStatement();
           int x=pst.executeUpdate(query);
           if(x>0)
           {
               String query_to_add_in_revoked_list="insert into revoked_list_table values('"+user_name+"','"+email+"','"+password+"')";
               pst.executeUpdate(query_to_add_in_revoked_list);
               se.setAttribute("msg", "Registered Sucessfully!!");
               response.sendRedirect("userregister.jsp");
           }
           else
           {
               se.setAttribute("msg", "Error Occured");
               response.sendRedirect("userregister.jsp");
           }
        }
        catch(Exception ee)
            {
                ee.printStackTrace();
            }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
