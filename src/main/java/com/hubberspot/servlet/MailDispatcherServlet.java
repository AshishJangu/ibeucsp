/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hubberspot.servlet;

import com.hubber.ejb.MailSenderBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Prabhunath
 */
@WebServlet(name = "MailDispatcherServlet", urlPatterns = {"/MailDispatcherServlet"})
public class MailDispatcherServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private MailSenderBean mailSender; 
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String toEmail=request.getParameter("email");
        String subject=request.getParameter("subject");
        String message=request.getParameter("message");
        
        
        String fromEmail="punit1971136@gmail.com";
        String username="punit1971136";
        String password="punit12345!";
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            //mailSender.sendEmail(fromEmail, username, password, toEmail, subject, message);
            HttpSession se=request.getSession();
            String toEmail1=(String)se.getAttribute("email");
            String subject1=(String)se.getAttribute("subject");
            String message1=(String)se.getAttribute("message");
            
            
            mailSender.sendEmail(fromEmail, username, password, toEmail1, subject1, message1);
            String filename[]=subject1.split(":");
                    
            se.setAttribute("file_name",filename[1]);
             se.setAttribute("u_email",toEmail1);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Mail Sent Sucessfully!!</title>");   
            out.println("<link type=\"text/css\" href=\"CSS/main.css\" rel=\"stylesheet\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"head\"><h2>Identity Based Encryption using KU-CSP</h2></div>");
            out.println("<a href='decryption_using_key.jsp?check your email for key'>Check your Email and Click Here!!</a>");
            out.println("<br>EMAIL:  "+toEmail1+"<br>SUBJECT:  "+subject1+"<br>MESSAGE:  "+message1+"<br>");
            out.println("</body>");
            out.println("</html>");
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
