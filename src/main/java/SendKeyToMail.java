/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;
import java.sql.DriverManager;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Prabhunath
 */
public class SendKeyToMail extends HttpServlet {

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
            String filename,email;
            email=request.getParameter("user_mail");
            filename=request.getParameter("file_name");
            Class.forName("com.mysql.jdbc.Driver");
           HttpSession se=request.getSession();
     Connection con=DriverManager.getConnection("jdbc:mysql://127.11.188.130:3306/ibeorcc","adminaHSV5Sy","iSVhA33vaX73");
            //out.println("EMAIL:"+email+"<hr>Filename: "+filename);
            out.println("<table><tr>"
                    +"<td>To</td><td><input type='text' name='email' value='"+email+"'></td>"
                    +"<td>Subject</td><td><input type='text' name='subject' value='Your Private Key for File:"+filename+"'></td>"
                    +"<td>Private Key as Message: </td><td><input type='text'></td>"
                    + "</tr></table>");
            
            
            Statement st=con.createStatement();
            String query="select public_key,private_key from csp_files where filename='"+filename+"' and email='"+email+"'";
            out.println(query);
            ResultSet rs=st.executeQuery(query);
             String subject="Your Private Key for File: "+filename;
            se.setAttribute("email", email);
            se.setAttribute("subject", subject);
           String msg="";
            if(rs.next())
            {
           //   //->>> must set it...
          
          //-------------------------
            Blob b=rs.getBlob(1);
          Blob b1=rs.getBlob(2);    
          PublicKey publicKey1 =KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(b.getBytes(1,(int)b.length())));
          PrivateKey privateKey1 = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(b1.getBytes(1, (int)b1.length())));

        out.println("<HR>PRIVATE KEY::: "+privateKey1+"<hr>PUBLIC KEY::: "+publicKey1+"<HR>"+b1.getBytes(1, (int)b1.length()));
        String generated_key="";
        Random randomGenerator = new Random();
        for(int i=0;i<6;i++)
        {
        int randomInt = randomGenerator.nextInt(10);
        generated_key+=randomInt;
        }
        byte pkey1[]=publicKey1.getEncoded();
          byte prkey1[]=privateKey1.getEncoded();
        String q="insert into Mailed values(?,?,?,?)";  
        
       PreparedStatement pst=con.prepareStatement(q);
       pst.setString(1,email);
       pst.setString(2,filename);
       pst.setBytes(3,prkey1);
       pst.setString(4,generated_key);
       
        int x=pst.executeUpdate();
        
        se.setAttribute("message", generated_key);
        
        out.println("<br>okokokokokok...");
        if(x>0)
        {
            //PrintWriter out=response.getWriter();
           out.println("Key Generated and Sending Mail...<br>");
        }
            
            
            
            
            
            }
            out.println("<br>"+email+"<br>"+subject+" <br> "+msg);
            response.sendRedirect("MailDispatcherServlet");
        }
        catch(Exception ee)
        {
            
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
